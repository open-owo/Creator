package com.xiyue.creator.BuilderSystem;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.xiyue.creator.Creator;
import com.xiyue.creator.ModBlockEntities.ModBlockEntities;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.Optional;

@EventBusSubscriber(modid = Creator.MODID, value = Dist.CLIENT)
public class BuilderSystem {

    //预览渲染
    @OnlyIn(Dist.CLIENT)
    private static BlockPos previewPos = null;
    @OnlyIn(Dist.CLIENT)
    private static boolean shouldShowPreview = false;

    public static void reset() {
        previewPos = null;
        shouldShowPreview = false;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        Level level = mc.level;

        if (player == null || level == null) return;

        BlockHitResult hitResult = null;
        if (mc.hitResult instanceof BlockHitResult hitRes) hitResult = hitRes;

        if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) return;

        if (level.getBlockState(hitResult.getBlockPos()).canBeReplaced()) {
            previewPos = hitResult.getBlockPos();
        } else {
            previewPos = hitResult.getBlockPos().relative(hitResult.getDirection());
        }

        if (!shouldShowPreview || event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS || !level.getBlockState(previewPos).canBeReplaced()) return;

        renderPreviewBox(event.getPoseStack(), previewPos);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        shouldShowPreview = player.getPersistentData().getBoolean("BuildingStrainer");
    }

    @OnlyIn(Dist.CLIENT)
    private static void renderPreviewBox(PoseStack poseStack, BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        if (level == null) return;

        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
        poseStack.pushPose();
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        VoxelShape shape = Shapes.block();

        float red = 1.0f;
        float green = 0.0f;
        float blue = 0.0f;
        float alpha = 0.8f;

        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        VertexConsumer lineBuffer = bufferSource.getBuffer(RenderType.lines());

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();

        shape.forAllEdges((x1, y1, z1, x2, y2, z2) -> {
            float dx = (float)(x2 - x1);
            float dy = (float)(y2 - y1);
            float dz = (float)(z2 - z1);
            float length = Mth.sqrt(dx * dx + dy * dy + dz * dz);

            lineBuffer.addVertex(matrix4f, (float)(x1 + pos.getX()), (float)(y1 + pos.getY()), (float)(z1 + pos.getZ()))
                    .setColor(red, green, blue, alpha)
                    .setNormal(pose, dx/length, dy/length, dz/length);

            lineBuffer.addVertex(matrix4f, (float)(x2 + pos.getX()), (float)(y2 + pos.getY()), (float)(z2 + pos.getZ()))
                    .setColor(red, green, blue, alpha)
                    .setNormal(pose, dx/length, dy/length, dz/length);
        });

        poseStack.popPose();
    }


    //方块实体
    public static class BuilderBlockEntity extends BlockEntity {
        private static final int TotalExistingTime = 600;
        private int ExistingTime = 0;

        private BuilderRecipe Recipe = null;
        private ItemStackHandler consumedItemList = new ItemStackHandler(1);
        private int step = 0;
        private int single_progress = 0;
        private int progress = 0;
        private int total_progress = 0;
        private boolean hasRecipe = false;
        private boolean Interacted = false;
        private boolean finishing = false;
        private String namespace = "";
        private String path = "";

        public float getProgressPercentage(){
            if (total_progress == 0) return 0;
            return (float) progress / total_progress;
        }

        public int getTotalExistingTime() {
            return TotalExistingTime;
        }

        public int getExistingTime() {
            return ExistingTime;
        }

        public BuilderRecipe getRecipe(){
            if (Recipe == null){
                setRecipe(namespace, path);
            }
            return Recipe;
        }

        public int getStep() {
            return step;
        }

        public int getSingle_progress() {
            if (single_progress == 0) {
                if (Recipe == null) {
                    setRecipe(namespace, path);
                }
                if (Recipe != null) {
                    single_progress = Recipe.getBuilderSteps().get(getStep()).stack().count();
                }
            }
            return single_progress;
        }

        public String getNamespace() {
            return namespace;
        }

        public String getPath() {
            return path;
        }

        public void setRecipe(String namespace, String path) {
            if (level != null) {
                ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, path);
                Optional<RecipeHolder<?>> option = level.getRecipeManager().byKey(resourceLocation);
                if (option.isPresent() && option.get().value() instanceof BuilderRecipe recipe) {
                    Recipe = recipe;
                    setTotal_Progress(Recipe);
                    this.consumedItemList = new ItemStackHandler(this.Recipe.getTotalSteps());
                    this.hasRecipe = true;
                    this.namespace = namespace;
                    this.path = path;
                    this.setChanged();
                    if (!level.isClientSide) {
                        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
                    }
                }else {
                    if (!level.isClientSide) {
                        level.removeBlock(this.getBlockPos(), false);
                        Creator.LOGGER.warn("Failed to load recipe {}:{} for builder at {}. The block has been removed.", namespace, path, worldPosition.toShortString());
                    } else {
                        Creator.LOGGER.warn("Client failed to load recipe {}:{} for builder at {}. Will retry later.", namespace, path, worldPosition.toShortString());
                    }
                }
            }
        }

        private void setTotal_Progress(BuilderRecipe recipe){
            int totalSteps = recipe.getTotalSteps();
            for (int i = 0; i < totalSteps; i++) {
                total_progress += recipe.getBuilderSteps().get(i).stack().count();
            }
        }

        public BuilderBlockEntity(BlockPos pos, BlockState state) {
            super(ModBlockEntities.BUILDER_BLOCK.get(), pos, state);
        }

        protected static void serverTick(Level level, BlockPos pos, BlockState state, BuilderBlockEntity builder) {
            builder.ExistingTime++;
            if (builder.ExistingTime == TotalExistingTime && state.getBlock() instanceof BuilderBlock && !builder.Interacted) {
//                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                level.removeBlock(pos, false);
            } else if (builder.Interacted) {
                builder.Interacted = false;
                builder.ExistingTime = 0;
            }
            builder.setChanged();
        }

        //数据同步
        @Override
        public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
            CompoundTag tag = new CompoundTag();
            saveAdditional(tag, registries);
            return tag;
        }

        @Override
        public Packet<ClientGamePacketListener> getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        @Override
        public void onDataPacket(@NotNull Connection connection, @NotNull ClientboundBlockEntityDataPacket packet, HolderLookup.@NotNull Provider registries) {
            super.onDataPacket(connection, packet, registries);
            loadAdditional(packet.getTag(), registries);

//            if (level != null && level.isClientSide) {
//                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
//            }
        }

        protected void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
            super.loadAdditional(tag, registries);
            this.ExistingTime = tag.getInt("ExistingTime");
            this.step = tag.getInt("step");
            this.single_progress = tag.getInt("single_progress");
            this.progress = tag.getInt("progress");
            this.total_progress = tag.getInt("total_progress");
            this.namespace = tag.getString("namespace");
            this.path = tag.getString("path");
            this.hasRecipe = tag.getBoolean("hasRecipe");
            this.Interacted = tag.getBoolean("Interacted");
            this.finishing = tag.getBoolean("finishing");
            CompoundTag consumedItemList = tag.getCompound("consumedItemList");
            this.consumedItemList.deserializeNBT(registries ,consumedItemList);

        }

        protected void saveAdditional(@NotNull CompoundTag tag,@NotNull HolderLookup.Provider registries) {
            super.saveAdditional(tag, registries);
            tag.putInt("ExistingTime", ExistingTime);
            tag.putInt("step", step);
            tag.putInt("single_progress", single_progress);
            tag.putInt("progress", progress);
            tag.putInt("total_progress", total_progress);
            tag.putString("namespace", namespace);
            tag.putString("path", path);
            tag.putBoolean("hasRecipe", hasRecipe);
            tag.putBoolean("Interacted", Interacted);
            tag.putBoolean("finishing", finishing);
            tag.put("consumedItemList", this.consumedItemList.serializeNBT(registries));

        }
    }

    //方块
    public static class BuilderBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
        public static BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
        public static final DirectionProperty DIRECTION = BlockStateProperties.FACING;

        public BuilderBlock() {
            super(Properties.of().noOcclusion().noCollission().strength(0.3f,1f).sound(SoundType.EMPTY));
            registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(DIRECTION, Direction.NORTH));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> Builder) {
            Builder.add(WATERLOGGED, DIRECTION);
        }

        @Override
        public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
            return level.isClientSide ? null : (BlockEntityTicker<T>) (BlockEntityTicker<BuilderBlockEntity>) BuilderBlockEntity::serverTick;
        }

        protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull BlockHitResult hitResult) {
            if (player.isShiftKeyDown()) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
            return InteractionResult.SUCCESS;
        }

        @Override
        protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
            if (!level.isClientSide && level.getBlockEntity(pos) instanceof BuilderBlockEntity builder) {
                if (!builder.hasRecipe) {
                    return ItemInteractionResult.FAIL;
                }
                if (builder.Recipe == null) {
                    builder.setRecipe(builder.namespace, builder.path);
                }

                if (builder.single_progress == 0) {
                    builder.single_progress = builder.getSingle_progress();
                }

                Ingredient useStack = builder.Recipe.getBuilderSteps().get(builder.step).stack().ingredient();

                if (useStack.test(stack)){
                    if (building(builder, stack, builder.consumedItemList, level, pos, player) && builder.step < builder.Recipe.getTotalSteps()) {
                        builder.step++;
                    }
                }
                if (builder.step >= builder.Recipe.getTotalSteps()){
                    builder.finishing = true;
                    BlockState res = builder.Recipe.getResult();
                    if (res.hasProperty(BlockStateProperties.FACING)){
                        res = res.setValue(BlockStateProperties.FACING, player.getDirection().getOpposite());
                    }
                    level.setBlockAndUpdate(pos, res);
                }

                builder.setChanged();
                level.sendBlockUpdated(pos, state, state, 2);
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            return ItemInteractionResult.SUCCESS;
        }

        private boolean building(BuilderBlockEntity builder, ItemStack stack, ItemStackHandler consumedItemList, Level level, BlockPos pos, Player player){
            if (builder.single_progress > 0) {
                stack.consume(1, player);
                builder.single_progress--;
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);

                recordConsumedItem(consumedItemList, stack);

                builder.Interacted = true;
                builder.progress++;
                builder.setChanged();
                return builder.single_progress == 0;
            }
            builder.setChanged();
            return true;
        }

        private void recordConsumedItem(ItemStackHandler handler, ItemStack consumed) {
            ItemStack toAdd = new ItemStack(consumed.getItem(), 1);

            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack slotStack = handler.getStackInSlot(i);

                if (slotStack.isEmpty()) {
                    handler.setStackInSlot(i, toAdd.copy());
                    return;
                }

                if (ItemStack.isSameItemSameComponents(slotStack, toAdd) && slotStack.getCount() < slotStack.getMaxStackSize()) {
                    slotStack.grow(1);
                    return;
                }
            }

            expandHandlerAndAdd(handler, toAdd);
        }

        private void expandHandlerAndAdd(ItemStackHandler handler, ItemStack toAdd) {
            int newSize = handler.getSlots() + 1;
            ItemStackHandler newHandler = new ItemStackHandler(newSize);

            for (int i = 0; i < handler.getSlots(); i++) {
                newHandler.setStackInSlot(i, handler.getStackInSlot(i).copy());
            }

            newHandler.setStackInSlot(newSize - 1, toAdd.copy());

            handler.setSize(newSize);
            for (int i = 0; i < newSize; i++) {
                handler.setStackInSlot(i, newHandler.getStackInSlot(i));
            }
        }

        @Override
        public @NotNull FluidState getFluidState(BlockState state) {
            if (state.getValue(WATERLOGGED)) {
                return Fluids.WATER.getSource(false);
            }
            return Fluids.EMPTY.defaultFluidState();
        }

        @Nullable
        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            Level level = context.getLevel();
            FluidState fluidstate = level.getFluidState(context.getClickedPos());
            if (fluidstate.getType() == Fluids.WATER){
                return defaultBlockState().setValue(WATERLOGGED, true).setValue(DIRECTION, context.getHorizontalDirection().getOpposite());
            }
            return this.defaultBlockState();
        }

        //方块破坏时
        @Override
        public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
            if (state.hasBlockEntity() && !state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof BuilderBlockEntity entity) {
                if (!entity.finishing) {
                    ItemStackHandler itemHandler = entity.consumedItemList;
                    if (itemHandler != null) {
                        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
                            ItemStack stack = itemHandler.getStackInSlot(slot).copy();
                            if (!stack.isEmpty()) {
                                Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                            }
                        }
                        entity.setChanged();
                    }
                    level.playSound(null, pos, SoundEvents.ITEM_FRAME_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.updateNeighbourForOutputSignal(pos, this);
                }

            }
            super.onRemove(state, level, pos, newState, isMoving);
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            return new BuilderBlockEntity(blockPos, blockState);
        }
    }
}
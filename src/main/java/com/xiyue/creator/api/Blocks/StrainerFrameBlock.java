package com.xiyue.creator.api.Blocks;

import com.xiyue.creator.ModItems.FunctionItems.Meshes;
import com.xiyue.creator.api.BlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public abstract class StrainerFrameBlock extends Block implements EntityBlock, SimpleWaterloggedBlock{
    //方块状态
    public static BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static BooleanProperty LAVALOGGED = BooleanProperty.create("lavalogged");
    public static BooleanProperty HAS_MESH = BooleanProperty.create("has_mesh");

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HAS_MESH)){
            return SHAPE1;
        }
        return SHAPE;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 5;
    }

    //构造器
    public StrainerFrameBlock(Properties properties) {
        super(properties.noOcclusion().lightLevel(state -> state.getValue(LAVALOGGED) ? 15 : 0));
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(LAVALOGGED, false).setValue(HAS_MESH, false));
    }

    //方块状态
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> Builder) {
        Builder.add(WATERLOGGED, LAVALOGGED, HAS_MESH);
    }

    //交互
    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && InteractionHand.MAIN_HAND == player.getUsedItemHand()) {
            player.openMenu(Objects.requireNonNull(state.getMenuProvider(level, pos)), pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide && stack.getItem() instanceof Meshes) {
            StrainerFrameEntity StrainerFrame = (StrainerFrameEntity) level.getBlockEntity(pos);
            ItemStackHandler itemHandler = null;
            if (StrainerFrame != null) {
                itemHandler = StrainerFrame.getItemHandler();
            }

            ItemStack toAdd = stack.copyWithCount(1);
            if (itemHandler != null && itemHandler.getStackInSlot(0).isEmpty()) {
                itemHandler.setStackInSlot(0, toAdd);
                stack.consume(1, player);
                StrainerFrame.setChanged();
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    //方块破坏时
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && !state.is(newState.getBlock())) {
            StrainerFrameEntity strainerFrame = (StrainerFrameEntity)level.getBlockEntity(pos);
            ItemStackHandler itemHandler = null;
            if (strainerFrame != null) {
                itemHandler = strainerFrame.getItemHandler();
            }

            if (itemHandler != null) {
                for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
                    ItemStack stack = itemHandler.getStackInSlot(slot).copy();
                    if (!stack.isEmpty()) {
                        Containers.dropItemStack(level, pos.getX() + 1, pos.getY(), pos.getZ(), stack);
                    }
                }
            }
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    //菜单
    @Override
    public abstract MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos);

    @Override
    public abstract  BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState);

    //tick
    @Override
    public abstract <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type);

    //含水
    @Override
    public @NotNull ItemStack pickupBlock(Player player, @NotNull LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.setBlock(blockPos, blockState.setValue(WATERLOGGED, false), 3);
            if (!blockState.canSurvive(levelAccessor, blockPos)) {
                levelAccessor.destroyBlock(blockPos, true);
            }
            return new ItemStack(Items.WATER_BUCKET);
        } else if (blockState.getValue(LAVALOGGED)) {
            levelAccessor.setBlock(blockPos, blockState.setValue(LAVALOGGED, false), 3);
            if (!blockState.canSurvive(levelAccessor, blockPos)) {
                levelAccessor.destroyBlock(blockPos, true);
            }
            return new ItemStack(Items.LAVA_BUCKET);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    @Deprecated
    public @NotNull Optional<SoundEvent> getPickupSound() {
        return Fluids.EMPTY.getPickupSound();
    }

    @Override
    public @NotNull Optional<SoundEvent> getPickupSound(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getPickupSound();
        } else if (state.getValue(LAVALOGGED)) {
            return Fluids.LAVA.getPickupSound();
        }
        return Fluids.EMPTY.getPickupSound();
    }

    @Override
    public boolean canPlaceLiquid(Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return !blockState.getValue(WATERLOGGED) && !blockState.getValue(LAVALOGGED) && (fluid == Fluids.WATER || fluid == Fluids.LAVA);
    }

    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        if (!blockState.getValue(WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            if (!levelAccessor.isClientSide()) {
                levelAccessor.setBlock(blockPos, blockState.setValue(WATERLOGGED, true), 3);
                levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
            }
            return true;
        } else if (!blockState.getValue(LAVALOGGED) && fluidState.getType() == Fluids.LAVA) {
            if (!levelAccessor.isClientSide()) {
                levelAccessor.setBlock(blockPos, blockState.setValue(LAVALOGGED, true), 3);
                levelAccessor.scheduleTick(blockPos, Fluids.LAVA, Fluids.LAVA.getTickDelay(levelAccessor));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        } else if (state.getValue(LAVALOGGED)) {
            return Fluids.LAVA.getSource(false);
        }
        return Fluids.EMPTY.defaultFluidState();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        FluidState fluidstate = level.getFluidState(context.getClickedPos());
        if (fluidstate.getType() == Fluids.WATER){
            return defaultBlockState().setValue(WATERLOGGED, true);
        } else if (fluidstate.getType() == Fluids.LAVA) {
            return defaultBlockState().setValue(LAVALOGGED, true);
        }
        return defaultBlockState();
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return 20;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return 5;
    }

    //shape
    private static final VoxelShape SHAPE;

    private static final VoxelShape SHAPE1;

    static {
        // 创建所有部件的碰撞箱
        VoxelShape part1 = Block.box(0, 0, 2, 2, 2, 14);
        VoxelShape part2 = Block.box(14, 0, 2, 16, 2, 14);
        VoxelShape part3 = Block.box(14, 14, 2, 16, 16, 14);
        VoxelShape part4 = Block.box(0, 14, 2, 2, 16, 14);
        VoxelShape part5 = Block.box(0, 0, 14, 2, 2, 16);
        VoxelShape part6 = Block.box(14, 2, 0, 16, 14, 2);
        VoxelShape part7 = Block.box(14, 2, 14, 16, 14, 16);
        VoxelShape part8 = Block.box(0, 2, 14, 2, 14, 16);
        VoxelShape part9 = Block.box(14, 0, 0, 16, 2, 2);
        VoxelShape part10 = Block.box(14, 0, 14, 16, 2, 16);
        VoxelShape part11 = Block.box(0, 2, 0, 2, 14, 2);
        VoxelShape part12 = Block.box(0, 0, 0, 2, 2, 2);
        VoxelShape part13 = Block.box(0, 14, 0, 2, 16, 2);
        VoxelShape part14 = Block.box(0, 14, 14, 2, 16, 16);
        VoxelShape part15 = Block.box(14, 14, 14, 16, 16, 16);
        VoxelShape part16 = Block.box(2, 0, 0, 14, 2, 2);
        VoxelShape part17 = Block.box(2, 14, 0, 14, 16, 2);
        VoxelShape part18 = Block.box(2, 14, 14, 14, 16, 16);
        VoxelShape part19 = Block.box(2, 0, 14, 14, 2, 16);
        VoxelShape part20 = Block.box(14, 14, 0, 16, 16, 2);
        VoxelShape part21 = Block.box(2, 14, 2, 14, 16, 14);
        VoxelShape part22 = Block.box(2, 0, 2, 14, 2, 14);
        VoxelShape part23 = Block.box(0, 2, 2, 2, 14, 14);
        VoxelShape part24 = Block.box(14, 2, 2, 16, 14, 14);
        VoxelShape part25 = Block.box(2, 2, 0, 14, 14, 2);
        VoxelShape part26 = Block.box(2, 2, 14, 14, 14, 16);

        VoxelShape group = Shapes.or(
                part1, part2, part3, part4, part5,
                part6, part7, part8, part9, part10,
                part11, part12, part13, part14, part15,
                part16, part17, part18, part19, part20,
                part21, part22, part23, part24, part25,
                part26
        );
        SHAPE1 = Shapes.or(group);

        double[][] elements = new double[][]{{0, 0, 14, 2, 2, 16}, {14, 0, 14, 16, 2, 16}, {14, 14, 14, 16, 16, 16}, {0, 14, 14, 2, 16, 16}, {14, 14, 0, 16, 16, 2}, {0, 0, 0, 2, 2, 2}, {14, 0, 0, 16, 2, 2}, {2, 0, 0, 14, 2, 2}, {2, 0, 14, 14, 2, 16}, {2, 14, 0, 14, 16, 2}, {2, 14, 14, 14, 16, 16}, {0, 2, 0, 2, 14, 2}, {0, 2, 14, 2, 14, 16}, {14, 2, 0, 16, 14, 2}, {14, 2, 14, 16, 14, 16}, {0, 14, 0, 2, 16, 2}, {0, 0, 2, 2, 2, 14}, {14, 0, 2, 16, 2, 14}, {14, 14, 2, 16, 16, 14}, {0, 14, 2, 2, 16, 14}};
        VoxelShape shape = Shapes.empty();
        for (double[] element : elements) {
            double minX = element[0] / 16.0;
            double minY = element[1] / 16.0;
            double minZ = element[2] / 16.0;
            double maxX = element[3] / 16.0;
            double maxY = element[4] / 16.0;
            double maxZ = element[5] / 16.0;
            VoxelShape part = Shapes.box(minX, minY, minZ, maxX, maxY, maxZ);shape = Shapes.or(shape, part);
        }
        SHAPE = shape;
    }
}

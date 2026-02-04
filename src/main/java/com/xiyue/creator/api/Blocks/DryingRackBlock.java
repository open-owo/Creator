package com.xiyue.creator.api.Blocks;

import com.xiyue.creator.api.BlockEntities.DryingRackEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DryingRackBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {
    public static DirectionProperty DIRECTION = BlockStateProperties.FACING;
    public static BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public DryingRackBlock(Properties properties) {
        super(properties.noOcclusion());
        registerDefaultState(stateDefinition.any().setValue(DIRECTION, Direction.NORTH).setValue(WATERLOGGED, false));
    }



    //方块状态
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> Builder) {
        Builder.add(DIRECTION, WATERLOGGED);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof DryingRackEntity dryingRack) {
            Direction facing = state.getValue(DIRECTION);

            Vec3 hitVec = hitResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
            double x = hitVec.x;
            double y = hitVec.y;
            double z = hitVec.z;

            int slot = getSlot(facing, x, y, z);

            if (slot != -1){
                ItemStackHandler itemHandler = dryingRack.getItemHandler();
                if (itemHandler == null) return ItemInteractionResult.FAIL;
                if (!itemHandler.getStackInSlot(slot).isEmpty() || stack.isEmpty()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                ItemStack toAdd = stack.copyWithCount(1);
                itemHandler.setStackInSlot(slot, toAdd);
                stack.consume(1, player);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                dryingRack.setChanged();
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.FAIL;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof DryingRackEntity dryingRack) {
            Direction facing = state.getValue(DIRECTION);

            Vec3 hitVec = hitResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
            double x = hitVec.x;
            double y = hitVec.y;
            double z = hitVec.z;

            int slot = getSlot(facing, x, y, z);

            if (slot == -1) return InteractionResult.FAIL;

            ItemStackHandler itemHandler = dryingRack.getItemHandler();
            ItemStack stack = itemHandler.getStackInSlot(slot).copy();

            if (stack.isEmpty()) return InteractionResult.PASS;

            if (!level.isClientSide) {
                itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
                Containers.dropItemStack(level, hitResult.getLocation().x, hitResult.getLocation().y + 0.8, hitResult.getLocation().z, stack);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            dryingRack.setChanged();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && !state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof DryingRackEntity entity) {
            ItemStackHandler itemHandler = entity.getItemHandler();;

            if (itemHandler != null) {
                for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
                    ItemStack stack = itemHandler.getStackInSlot(slot).copy();
                    if (!stack.isEmpty()) {
                        Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                    }
                }
            }
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        FluidState fluidstate = level.getFluidState(context.getClickedPos());
        if (fluidstate.getType() == Fluids.WATER){
            return defaultBlockState().setValue(WATERLOGGED, true).setValue(DIRECTION, context.getHorizontalDirection().getOpposite());
        }
        return this.defaultBlockState().setValue(DIRECTION, context.getHorizontalDirection().getOpposite());
    }

    //含水
    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public abstract @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState);

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return 20;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return 5;
    }

    private int getSlot(Direction facing, double x, double y, double z){
        //前面(north)
        //右下(0.25,0.25,0.25)x,y,z
        boolean a = x>=0 && x<=0.5 && z>=0 && z<=0.5 && y>=0 && y<=0.5;
        //左下(0.75,0.25,0.25)
        boolean b = x>=0.5 && x<=1 && z>=0 && z<=0.5 && y>=0 && y<=0.5;
        //右上(0.25,0.75,0.25)
        boolean c = x>=0 && x<=0.5 && z>=0 && z<=0.5 && y>=0.5 && y<=1;
        //左上(0.75,0.75,0.25)
        boolean d = x>=0.5 && x<=1 && z>=0 && z<=0.5 && y>=0.5 && y<=1;

        //后面(south)
        //右下(0.25,0.25,0.75)x,y,z
        boolean e = x>=0 && x<=0.5 && z>=0.5 && z<=1 && y>=0 && y<=0.5;
        //左下(0.75,0.25,0.75)
        boolean f = x>=0.5 && x<=1 && z>=0.5 && z<=1 && y>=0 && y<=0.5;
        //右上(0.25,0.75,0.75)
        boolean g = x>=0 && x<=0.5 && z>=0.5 && z<=1 && y>=0.5 && y<=1;
        //左上(0.75,0.75,0.75)
        boolean h = x>=0.5 && x<=1 && z>=0.5 && z<=1 && y>=0.5 && y<=1;

        switch (facing) {
            case NORTH: {
                //右下
                if (a) return 0;
                //左下
                if (b) return 1;
                //右上
                if (g) return 2;
                //左上
                if (h) return 3;
            }

            case EAST:{
                //右下
                if (b) return 0;
                //左下
                if (f) return 1;
                //右上
                if (c) return 2;
                //左上
                if (g) return 3;
            }

            case SOUTH:{
                //右下
                if (f) return 0;
                //左下
                if (e) return 1;
                //右上
                if (d) return 2;
                //左上
                if (c)return 3;
            }

            case WEST: {
                //右下
                if (e) return 0;
                //左下
                if (a) return 1;
                //右上
                if (h) return 2;
                //左上
                if (d) return 3;
            }
        }

        return -1;
    }

    //碰撞体积
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;

    private static final VoxelShape NORTH_SHAPE_c;
    private static final VoxelShape EAST_SHAPE_c;
    private static final VoxelShape WEST_SHAPE_c;
    private static final VoxelShape SOUTH_SHAPE_c;


    static {
        VoxelShape north1 = Block.box(0, 4, 4, 16, 8, 8);
        VoxelShape north2 = Block.box(0, 8, 8, 16, 12, 12);
        VoxelShape north3 = Block.box(0, 12, 12, 16, 16, 16);
        VoxelShape north4 = Block.box(0, 0, 0, 16, 4, 4);
        VoxelShape north5 = Block.box(0, 0, 15, 1, 12, 16);
        VoxelShape north6 = Block.box(15, 0, 15, 16, 12, 16);
        NORTH_SHAPE_c = Shapes.or(north1, north2, north3, north4, north5, north6);

        VoxelShape north1c = Block.box(0, 0, 0, 16, 4, 16);
        VoxelShape north2c = Block.box(0, 4, 4, 16, 8, 16);
        VoxelShape north3c = Block.box(0, 8, 8, 16, 12, 16);
        VoxelShape north4c = Block.box(0, 12, 12, 16, 16, 16);
        NORTH_SHAPE = Shapes.or(north1c, north2c, north3c, north4c);

        VoxelShape east1 = Block.box(8, 4, 0, 12, 8, 16);
        VoxelShape east2 = Block.box(4, 8, 0, 8, 12, 16);
        VoxelShape east3 = Block.box(0, 12, 0, 4, 16, 16);
        VoxelShape east4 = Block.box(12, 0, 0, 16, 4, 16);
        VoxelShape east5 = Block.box(0, 0, 0, 1, 12, 1);
        VoxelShape east6 = Block.box(0, 0, 15, 1, 12, 16);
        EAST_SHAPE_c = Shapes.or(east1, east2, east3, east4, east5, east6);

        VoxelShape east1c = Block.box(0, 0, 0, 16, 4, 16);
        VoxelShape east2c = Block.box(0, 4, 0, 12, 8, 16);
        VoxelShape east3c = Block.box(0, 8, 0, 8, 12, 16);
        VoxelShape east4c = Block.box(0, 12, 0, 4, 16, 16);
        EAST_SHAPE = Shapes.or(east1c, east2c, east3c, east4c);

        VoxelShape west1 = Block.box(4, 4, 0, 8, 8, 16);
        VoxelShape west2 = Block.box(8, 8, 0, 12, 12, 16);
        VoxelShape west3 = Block.box(12, 12, 0, 16, 16, 16);
        VoxelShape west4 = Block.box(0, 0, 0, 4, 4, 16);
        VoxelShape west5 = Block.box(15, 0, 15, 16, 12, 16);
        VoxelShape west6 = Block.box(15, 0, 0, 16, 12, 1);
        WEST_SHAPE_c = Shapes.or(west1, west2, west3, west4, west5, west6);

        VoxelShape west1c = Block.box(0, 0, 0, 16, 4, 16);
        VoxelShape west2c = Block.box(4, 4, 0, 16, 8, 16);
        VoxelShape west3c = Block.box(8, 8, 0, 16, 12, 16);
        VoxelShape west4c = Block.box(12, 12, 0, 16, 16, 16);
        WEST_SHAPE = Shapes.or(west1c, west2c, west3c, west4c);

        VoxelShape south1 = Block.box(0, 0, 12, 16, 4, 16);
        VoxelShape south2 = Block.box(0, 4, 8, 16, 8, 12);
        VoxelShape south3 = Block.box(0, 8, 4, 16, 12, 8);
        VoxelShape south4 = Block.box(0, 12, 0, 16, 16, 4);
        VoxelShape south5 = Block.box(0, 0, 0, 1, 12, 1);
        VoxelShape south6 = Block.box(15, 0, 0, 16, 12, 1);
        SOUTH_SHAPE_c = Shapes.or(south1, south2, south3, south4, south5, south6);

        VoxelShape south1c = Block.box(0, 0, 0, 16, 4, 16);
        VoxelShape south2c = Block.box(0, 4, 0, 16, 8, 12);
        VoxelShape south3c = Block.box(0, 8, 0, 16, 12, 8);
        VoxelShape south4c = Block.box(0, 12, 0, 16, 16, 4);
        SOUTH_SHAPE = Shapes.or(south1c, south2c, south3c, south4c);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(DIRECTION)) {
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };

    }

    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(DIRECTION)) {
            case SOUTH -> SOUTH_SHAPE_c;
            case EAST -> EAST_SHAPE_c;
            case WEST -> WEST_SHAPE_c;
            default -> NORTH_SHAPE_c;
        };
    }
}

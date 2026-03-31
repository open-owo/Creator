package com.xiyue.creator.ModBlocks.FunctionBlocks;

import com.xiyue.creator.ModItems.FunctionItems.Meshes;
import com.xiyue.creator.ModBlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import com.xiyue.creator.api.Blocks.Macines.MachineBlock;
import com.xiyue.creator.api.util.ShapeHelper;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

import static com.xiyue.creator.api.Blocks.Property.BlockStateProperties.*;

public class StrainerFrameBlock extends MachineBlock implements SimpleWaterloggedBlock{

    public StrainerFrameBlock(Properties properties, ShapeHelper shapeHelper, Supplier<BlockEntityType<?>> blockEntityTypeSupplier) {
        super(properties, shapeHelper, blockEntityTypeSupplier);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(LAVALOGGED, false).setValue(HAS_MESH, false));
    }

    public StrainerFrameBlock(Properties properties, ShapeHelper shapeHelper,Supplier<BlockEntityType<?>> blockEntityTypeSupplier,int Flammability, int FireSpreadSpeed) {
        super(properties, shapeHelper,blockEntityTypeSupplier, Flammability, FireSpreadSpeed);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(LAVALOGGED, false).setValue(HAS_MESH, false));
    }

    //方块状态
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> Builder) {
        Builder.add(WATERLOGGED, LAVALOGGED, HAS_MESH);
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
}

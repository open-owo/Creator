package com.xiyue.creator.api.Blocks.Macines;

import com.xiyue.creator.api.BlockEntities.Machines.MachineBlockEntity;
import com.xiyue.creator.api.Blocks.BaseBlock;
import com.xiyue.creator.api.util.ShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public abstract class MachineBlock extends BaseBlock implements EntityBlock {

    public MachineBlock(Properties properties, ShapeHelper shapeHelper) {
        super(properties, shapeHelper);
    }

    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState state, Level level, @NotNull BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider ? (MenuProvider) blockEntity : null;
    }

    private void openMenu(BlockState state, Level level, BlockPos pos, Player player){
        MenuProvider menuProvider = state.getMenuProvider(level, pos);
        if (!level.isClientSide && InteractionHand.MAIN_HAND == player.getUsedItemHand() && menuProvider != null) {
            player.openMenu(menuProvider, pos);
        }
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        openMenu(state, level, pos, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && !state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof MachineBlockEntity entity) {
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
}

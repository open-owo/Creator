package com.xiyue.creator.Handler.StrainerFrame;

import com.xiyue.creator.ModItems.FunctionItems.Meshes;
import com.xiyue.creator.api.BlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class StrainerFrameItemHandler implements IItemHandler {
    StrainerFrameEntity StrainerFrame ;

    public StrainerFrameItemHandler(StrainerFrameEntity be) {
        StrainerFrame = be;
    }

    @Override
    public int getSlots() {
        return StrainerFrame.getItemHandler().getSlots();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int i) {
        return StrainerFrame.getItemHandler().getStackInSlot(i);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (isItemValid(slot, stack)) {
            return StrainerFrame.getItemHandler().insertItem(slot, stack, simulate);
        }
        return stack;
    }
    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot == 0) {
            return ItemStack.EMPTY;
        }
        return StrainerFrame.getItemHandler().extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int i) {
        return StrainerFrame.getItemHandler().getSlotLimit(i);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return slot == 0 && isMesh(stack);
    }

    private boolean isMesh(ItemStack stack) {
        return stack.getItem() instanceof Meshes;
    }
}

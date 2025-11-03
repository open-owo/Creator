package com.xiyue.creator.Handler.DryingRack;

import com.xiyue.creator.api.BlockEntities.DryingRackEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class DryingRackItemHandler implements IItemHandler {
    DryingRackEntity DryingRack;
    ItemStackHandler stacks;

    public DryingRackItemHandler(DryingRackEntity be) {
        DryingRack = be;
        stacks = be.getItemHandler();
    }
    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return stacks.insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot >= 0 && slot < stacks.getSlots() && DryingRack.getFinish(slot) == 1){
            return stacks.extractItem(slot, amount, simulate);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return stacks.getStackInSlot(slot).isEmpty() && slot >=0 && slot < stacks.getSlots();
    }

    @Override
    public int getSlotLimit(int i) {
        return stacks.getSlotLimit(i);
    }

    @Override
    public int getSlots() {
        return stacks.getSlots();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int i) {
        return stacks.getStackInSlot(i);
    }
}

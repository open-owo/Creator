package com.xiyue.creator.api.capability.item;

import com.xiyue.creator.api.capability.SlotType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class ConfigurableItemHandler implements IItemHandler {
    private final ItemStackHandler internal;
    private final ItemConfig.ContextRules rules;

    public ConfigurableItemHandler(ItemStackHandler internal, ItemConfig.ContextRules rules) {
        this.internal = internal;
        this.rules = rules;
    }

    @Override
    public int getSlots() {
        return internal.getSlots();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return internal.getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        SlotType type = rules.getSlotType(slot);
        if (type == SlotType.OUTPUT || type == SlotType.NONE) return stack;
        if (!rules.canInsert(slot, stack)) return stack;
        return internal.insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        SlotType type = rules.getSlotType(slot);
        if (type == SlotType.INPUT || type == SlotType.NONE) return ItemStack.EMPTY;
        ItemStack current = internal.getStackInSlot(slot);
        if (!rules.canExtract(slot, current)) return ItemStack.EMPTY;
        return internal.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return internal.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return insertItem(slot, stack, true).getCount() != stack.getCount();
    }
}

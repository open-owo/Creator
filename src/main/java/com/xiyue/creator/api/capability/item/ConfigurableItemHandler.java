package com.xiyue.creator.api.capability.item;

import com.xiyue.creator.api.capability.SlotType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;

public class ConfigurableItemHandler implements IItemHandler, IItemHandlerModifiable {
    private final ItemStackHandler internal;
    private final ItemConfig.ContextRules rules;
    private final BiPredicate<Integer, ItemStack> entityInsertCheck;
    private final BiPredicate<Integer, ItemStack> entityExtractCheck;

    public ItemConfig.ContextRules getRules() {
        return rules;
    }

    public ConfigurableItemHandler(ItemStackHandler internal, ItemConfig.ContextRules rules) {
        this.internal = internal;
        this.rules = rules;
        this.entityInsertCheck = ((integer, stack) -> true);
        this.entityExtractCheck = ((integer, stack) -> true);
    }

    public ConfigurableItemHandler(ItemStackHandler internal, ItemConfig.ContextRules rules, BiPredicate<Integer, ItemStack> entityInsertCheck, BiPredicate<Integer, ItemStack> entityExtractCheck) {
        this.internal = internal;
        this.rules = rules;
        this.entityInsertCheck = entityInsertCheck;
        this.entityExtractCheck = entityExtractCheck;
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
        if (!(rules.canInsert(type, slot, stack) && entityInsertCheck.test(slot, stack))) return stack;
        return internal.insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        SlotType type = rules.getSlotType(slot);
        ItemStack current = internal.getStackInSlot(slot);
        if (!(rules.canExtract(type, slot, current) && entityInsertCheck.test(slot, current))) return ItemStack.EMPTY;
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

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack itemStack) {
        internal.setStackInSlot(slot, itemStack);
    }
}

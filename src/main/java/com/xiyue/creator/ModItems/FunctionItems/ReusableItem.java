package com.xiyue.creator.ModItems.FunctionItems;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ReusableItem extends Item {
    public ReusableItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack getCraftingRemainingItem(@NotNull ItemStack stack) {
        return new ItemStack(this);
    }
}

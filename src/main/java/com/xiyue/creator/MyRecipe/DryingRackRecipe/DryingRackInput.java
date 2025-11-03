package com.xiyue.creator.MyRecipe.DryingRackRecipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public record DryingRackInput(ItemStack inputItem) implements RecipeInput {

    @Override
    public @NotNull ItemStack getItem(int index) {
        if (index != 0) throw new IllegalArgumentException("No item for index " + index);
        return inputItem;
    }

    @Override
    public int size() {
        return 1;
    }
}

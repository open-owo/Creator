package com.xiyue.creator.MyRecipe.SoakingRecipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public record SoakingInput(ItemStack inputItem, BlockState inputBlockState) implements RecipeInput {

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

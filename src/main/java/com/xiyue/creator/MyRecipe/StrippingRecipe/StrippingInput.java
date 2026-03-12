package com.xiyue.creator.MyRecipe.StrippingRecipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public record StrippingInput(ItemStack inputItem, BlockState inputBlock) implements RecipeInput {

    @Override
    public @NotNull ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> this.inputItem;
            case 1 -> this.inputBlock.getBlock().asItem().getDefaultInstance();
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + index);
        };
    }

    @Override
    public int size() {
        return 2;
    }
}

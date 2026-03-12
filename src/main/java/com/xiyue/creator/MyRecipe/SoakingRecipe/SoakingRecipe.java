package com.xiyue.creator.MyRecipe.SoakingRecipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static com.xiyue.creator.MyRecipe.RegisterRecipe.SOAKING_SERIALIZERS;
import static com.xiyue.creator.MyRecipe.RegisterRecipe.SOAKING_TYPE;

public record SoakingRecipe(Ingredient inputItem, BlockState inputBlockState, ItemStack resultItem) implements Recipe<SoakingInput> {

    @Override
    public ItemStack resultItem() {
        return resultItem.copy();
    }


    @Override
    public boolean matches(@NotNull SoakingInput soakingInput, @NotNull Level level) {
        return inputItem.test(soakingInput.getItem(0)) && inputBlockState == soakingInput.inputBlockState();
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SoakingInput soakingInput, HolderLookup.@NotNull Provider provider) {
        return this.resultItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return this.resultItem;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SOAKING_SERIALIZERS.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return SOAKING_TYPE.get();
    }
}

package com.xiyue.creator.Datagen.Provider.MyRecipe.Soaking;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SoakingBuilder implements RecipeBuilder {
    protected final ItemStack results;
    protected final BlockState inputBlockState;
    protected final Ingredient inputItem;

    public SoakingBuilder(ItemStack results,BlockState inputBlockState ,Ingredient inputItem) {
        this.results = results;
        this.inputItem = inputItem;
        this.inputBlockState = inputBlockState;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(String s, @NotNull Criterion<?> criterion) {
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return results.getItem();
    }
}

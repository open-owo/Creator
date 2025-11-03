package com.xiyue.creator.Datagen.Provider.MyRecipe.Builder;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BuilderRecipeBuilder implements RecipeBuilder {
    protected final BlockState result;

    public BuilderRecipeBuilder(BlockState result) {
        this.result = result;
    }

    @Override
    public @NotNull BuilderRecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        return this;
    }

    @Override
    public @NotNull BuilderRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return new ItemStack(this.result.getBlock()).getItem();
    }
}

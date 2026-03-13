package com.xiyue.creator.Datagen.Provider.MyRecipe.DryingRack;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class DryingRackBuilder implements RecipeBuilder {
    protected final ItemStack results;

    public DryingRackBuilder(ItemStack results) {
        this.results = results;
    }

    @Override
    public @NotNull DryingRackBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        return this;
    }

    @Override
    public @NotNull DryingRackBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return results.getItem();
    }
}

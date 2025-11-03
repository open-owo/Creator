package com.xiyue.creator.Datagen.Provider.MyRecipe.Stripping;

import com.xiyue.creator.MyRecipe.ChanceResult;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public abstract class StrippingBuilder implements RecipeBuilder {
    protected final List<ChanceResult> results;

    public StrippingBuilder(List<ChanceResult> results) {
        this.results = results;
    }

    @Override
    public @NotNull StrippingBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        return this;
    }

    @Override
    public @NotNull StrippingBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return ItemStack.EMPTY.getItem();
    }
}

package com.xiyue.creator.Datagen.Provider.MyRecipe.NoConsume;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class NoConsumeBuilder extends ShapelessRecipeBuilder {
    public NoConsumeBuilder(RecipeCategory category, ItemLike result, int count) {
        super(category, result, count);
    }

    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        super.save(recipeOutput, id);
    }
}

package com.xiyue.creator.Datagen.Provider.MyRecipe.NoConsume;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;

import java.util.Map;
import java.util.Objects;

public class NoConsumeBuilder extends ShapelessRecipeBuilder {
    public NoConsumeBuilder(RecipeCategory category, ItemLike result, int count) {
        super(category, result, count);
    }

    public void save(RecipeOutput recipeOutput, ResourceLocation id) {

    }
}

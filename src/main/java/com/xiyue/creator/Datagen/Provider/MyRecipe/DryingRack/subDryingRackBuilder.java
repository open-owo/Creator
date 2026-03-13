package com.xiyue.creator.Datagen.Provider.MyRecipe.DryingRack;

import com.xiyue.creator.MyRecipe.DryingRackRecipe.DryingRackRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class subDryingRackBuilder extends DryingRackBuilder{
    private final Ingredient inputItem;
    private final int processing_time;

    public subDryingRackBuilder(Ingredient inputItem, int processing_time,ItemStack results) {
        super(results);
        this.inputItem = inputItem;
        this.processing_time = processing_time;
    }

    @Override
    public void save(RecipeOutput output, @NotNull ResourceLocation id) {
        DryingRackRecipe recipe = new DryingRackRecipe(this.inputItem, this.results, this.processing_time);
        output.accept(id, recipe, null);
    }
}

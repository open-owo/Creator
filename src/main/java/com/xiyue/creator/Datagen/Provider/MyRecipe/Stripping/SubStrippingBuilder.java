package com.xiyue.creator.Datagen.Provider.MyRecipe.Stripping;

import com.xiyue.creator.MyRecipe.ChanceResult;
import com.xiyue.creator.MyRecipe.StrippingRecipe.StrippingRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.BlockTagIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SubStrippingBuilder extends StrippingBuilder{
    private final Ingredient inputItem;
    private final BlockTagIngredient inputBlock;

    public SubStrippingBuilder(Ingredient inputItem, BlockTagIngredient inputBlock, List<ChanceResult> results) {
        super(results);
        this.inputItem = inputItem;
        this.inputBlock = inputBlock;
    }

    @Override
    public void save(RecipeOutput output, @NotNull ResourceLocation id) {
        StrippingRecipe recipe = new StrippingRecipe(this.inputItem, this.inputBlock, this.results);
        output.accept(id, recipe, null);
    }
}

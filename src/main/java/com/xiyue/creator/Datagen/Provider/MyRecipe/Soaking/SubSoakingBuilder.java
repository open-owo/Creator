package com.xiyue.creator.Datagen.Provider.MyRecipe.Soaking;

import com.xiyue.creator.MyRecipe.SoakingRecipe.SoakingRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SubSoakingBuilder extends SoakingBuilder{
    public SubSoakingBuilder(ItemStack results, BlockState inputBlockState,Ingredient inputItem) {
        super(results,inputBlockState, inputItem);
    }

    @Override
    public void save(@NotNull RecipeOutput output, @NotNull ResourceLocation id) {
        SoakingRecipe recipe = new SoakingRecipe(this.inputItem,this.inputBlockState ,this.results);
        output.accept(id, recipe, null);
    }
}

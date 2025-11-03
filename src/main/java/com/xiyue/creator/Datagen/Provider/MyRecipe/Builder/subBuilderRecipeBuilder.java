package com.xiyue.creator.Datagen.Provider.MyRecipe.Builder;

import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderInput;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class subBuilderRecipeBuilder extends BuilderRecipeBuilder{
    private final List<BuilderInput.BuilderStep> builderSteps;

    public subBuilderRecipeBuilder(List<BuilderInput.BuilderStep> builderSteps, BlockState result) {
        super(result);
        this.builderSteps = builderSteps;
    }

    @Override
    public void save(RecipeOutput output, @NotNull ResourceLocation id) {
        BuilderRecipe recipe = new BuilderRecipe(this.builderSteps, this.result);
        output.accept(id, recipe, null);
    }
}

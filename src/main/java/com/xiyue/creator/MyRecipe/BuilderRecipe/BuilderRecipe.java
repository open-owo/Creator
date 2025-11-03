package com.xiyue.creator.MyRecipe.BuilderRecipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.xiyue.creator.MyRecipe.RegisterRecipe.BUILDER_TYPE;
import static com.xiyue.creator.MyRecipe.RegisterRecipe.BUILDER_SERIALIZERS;

public class BuilderRecipe implements Recipe<BuilderInput> {
    private final BlockState result;
    private final List<BuilderInput.BuilderStep> builderSteps;
    private final int totalSteps;

    public BlockState getResult() {
        return result;
    }

    public List<BuilderInput.BuilderStep> getBuilderSteps() {
        return builderSteps;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public BuilderRecipe(List<BuilderInput.BuilderStep> builderSteps, BlockState result) {
        this.result = result;
        this.builderSteps = builderSteps;
        this.totalSteps = builderSteps.size();
    }

    @Override
    public boolean matches(@NotNull BuilderInput builderInput, @NotNull Level level) {
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull BuilderInput builderInput, HolderLookup.@NotNull Provider provider) {
        return new ItemStack(this.result.getBlock()).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return new ItemStack(this.result.getBlock()).copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return BUILDER_SERIALIZERS.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return BUILDER_TYPE.get();
    }
}

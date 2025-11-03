package com.xiyue.creator.MyRecipe.StrippingRecipe;

import com.xiyue.creator.MyRecipe.ChanceResult;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.BlockTagIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record StrippingRecipe(Ingredient inputItem, BlockTagIngredient inputBlock, List<ChanceResult> results) implements Recipe<StrippingInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public boolean matches(@NotNull StrippingInput knifeStrippingInput, @NotNull Level level) {
        return this.inputBlock.test(knifeStrippingInput.inputBlock().getBlock().asItem().getDefaultInstance()) && this.inputItem.test(knifeStrippingInput.inputItem());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull StrippingInput knifeStrippingInput, HolderLookup.@NotNull Provider provider) {
        return ItemStack.EMPTY.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RegisterRecipe.STRIPPING_SERIALIZERS.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RegisterRecipe.STRIPPING_TYPE.get();
    }
}

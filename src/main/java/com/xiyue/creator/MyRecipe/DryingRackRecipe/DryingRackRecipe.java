package com.xiyue.creator.MyRecipe.DryingRackRecipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static com.xiyue.creator.MyRecipe.RegisterRecipe.*;

public record DryingRackRecipe(Ingredient inputItem, ItemStack resultItem,
                               int processing_time) implements Recipe<DryingRackInput> {

    @Override
    public ItemStack resultItem() {
        return resultItem.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public boolean matches(DryingRackInput dryingRackInput, @NotNull Level level) {
        return inputItem.test(dryingRackInput.inputItem());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull DryingRackInput dryingRackInput, HolderLookup.@NotNull Provider provider) {
        return this.resultItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return this.resultItem;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return DRYING_RACK_SERIALIZERS.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return DRYING_RACK_TYPE.get();
    }
}

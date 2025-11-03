package com.xiyue.creator.MyRecipe.DryingRackRecipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import static com.xiyue.creator.MyRecipe.RegisterRecipe.*;

public class DryingRackRecipe implements Recipe<DryingRackInput> {
    private final Ingredient inputItem;
    private final ItemStack resultItem;

    public Ingredient getInputItem() {
        return inputItem;
    }

    public ItemStack getResultItem() {
        return resultItem.copy();
    }

    public int getProcessing_time() {
        return processing_time;
    }

    private final int processing_time;

    public DryingRackRecipe(Ingredient inputItem, ItemStack resultItem,  int processing_time){
        this.inputItem = inputItem;
        this.resultItem = resultItem;
        this.processing_time = processing_time;

    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public boolean matches(DryingRackInput dryingRackInput, Level level) {
        return inputItem.test(dryingRackInput.inputItem());
    }

    @Override
    public ItemStack assemble(DryingRackInput dryingRackInput, HolderLookup.Provider provider) {
        return this.resultItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.resultItem;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DRYING_RACK_SERIALIZERS.get();
    }

    @Override
    public RecipeType<?> getType() {
        return DRYING_RACK_TYPE.get();
    }
}

package com.xiyue.creator.MyRecipe.CraftingTable;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static com.xiyue.creator.MyRecipe.RegisterRecipe.NO_SERIALIZERS;


public class NoConsumeRecipe extends ShapelessRecipe {
    protected ItemStack result;

    private static final Set<Item> NO_CONSUME_ITEMS = Set.of(Items.FLINT);

    public NoConsumeRecipe(String group, CraftingBookCategory category,
                           ItemStack result, NonNullList<Ingredient> ingredients) {
        super(group, category, result, ingredients);
        this.result = result;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return NO_SERIALIZERS.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for(int i = 0; i < input.size(); ++i) {
            ItemStack stack = input.getItem(i);

            if (stack.hasCraftingRemainingItem()) {
                remaining.set(i, stack.getCraftingRemainingItem());
            } else if (!stack.isEmpty() && NO_CONSUME_ITEMS.contains(stack.getItem())) {
                ItemStack copy = stack.copy();
                copy.setCount(1);
                remaining.set(i, copy);
            }
        }
        return remaining;
    }
}

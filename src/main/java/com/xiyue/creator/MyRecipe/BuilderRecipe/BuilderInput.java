package com.xiyue.creator.MyRecipe.BuilderRecipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.xiyue.creator.MyIngredient.IngredientWithCount.IngredientWithCount;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public record BuilderInput() implements RecipeInput {

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return ItemStack.EMPTY.copy();

    }

    @Override
    public int size() {
        return 0;
    }

    public record BuilderStep(IngredientWithCount stack) {
        public static final Codec<BuilderStep> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                IngredientWithCount.CODEC.fieldOf("ingredient").forGetter(BuilderStep::stack)
        ).apply(inst, BuilderStep::new));
    }
}

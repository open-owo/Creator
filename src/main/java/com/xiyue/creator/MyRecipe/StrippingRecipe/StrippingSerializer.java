package com.xiyue.creator.MyRecipe.StrippingRecipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.xiyue.creator.MyRecipe.ChanceResult;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.BlockTagIngredient;
import org.jetbrains.annotations.NotNull;

public class StrippingSerializer implements RecipeSerializer<StrippingRecipe> {
    public static final MapCodec<StrippingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                    Ingredient.CODEC.fieldOf("item").forGetter(StrippingRecipe::inputItem),
                    BlockTagIngredient.CODEC.fieldOf("block").forGetter(StrippingRecipe::inputBlock),
                    ChanceResult.CODEC.listOf().fieldOf("results").forGetter(StrippingRecipe::results)).apply(inst, StrippingRecipe::new));


    @Override
    public @NotNull MapCodec<StrippingRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, StrippingRecipe> streamCodec() {
        return ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());
    }
}

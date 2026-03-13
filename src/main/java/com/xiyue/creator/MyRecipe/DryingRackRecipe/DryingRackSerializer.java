package com.xiyue.creator.MyRecipe.DryingRackRecipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class DryingRackSerializer implements RecipeSerializer<DryingRackRecipe> {
    public static final MapCodec<DryingRackRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("input_item").forGetter(DryingRackRecipe::inputItem),
            ItemStack.CODEC.fieldOf("result_item").forGetter(DryingRackRecipe::resultItem),
            Codec.INT.fieldOf("processing_time").forGetter(DryingRackRecipe::processing_time)
    ).apply(inst, DryingRackRecipe::new));



    @Override
    public MapCodec<DryingRackRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, DryingRackRecipe> streamCodec() {
        return ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());
    }
}

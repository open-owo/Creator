package com.xiyue.creator.MyRecipe.SoakingRecipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.xiyue.creator.MyRecipe.ChanceResult;
import com.xiyue.creator.MyRecipe.StrippingRecipe.StrippingRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.crafting.BlockTagIngredient;
import org.jetbrains.annotations.NotNull;

public class SoakingSerializer implements RecipeSerializer<SoakingRecipe> {

    public static final MapCodec<SoakingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("item").forGetter(SoakingRecipe::inputItem),
            BlockState.CODEC.fieldOf("block_state").forGetter(SoakingRecipe::inputBlockState),
            ItemStack.CODEC.fieldOf("results").forGetter(SoakingRecipe::resultItem)
            ).apply(inst, SoakingRecipe::new)
    );


    @Override
    public @NotNull MapCodec<SoakingRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, SoakingRecipe> streamCodec() {
        return ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());
    }
}

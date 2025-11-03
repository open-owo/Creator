package com.xiyue.creator.MyRecipe.BuilderRecipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BuilderSerializer implements RecipeSerializer<BuilderRecipe> {

    public static final MapCodec<BuilderRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BuilderInput.BuilderStep.CODEC.listOf().fieldOf("builder_steps").forGetter(BuilderRecipe::getBuilderSteps),
            BlockState.CODEC.fieldOf("result").forGetter(BuilderRecipe::getResult))
            .apply(inst, BuilderRecipe::new));

    @Override
    public @NotNull MapCodec<BuilderRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, BuilderRecipe> streamCodec() {
        return ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());
    }

}

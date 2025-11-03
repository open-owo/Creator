package com.xiyue.creator.MyRecipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

public record ChanceResult(ItemStack stack, float chance, int minimum_guarantee_count) {
    public static final Codec<ChanceResult> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ItemStack.CODEC.fieldOf("stack").forGetter(ChanceResult::stack),
            Codec.FLOAT.fieldOf("chance").forGetter(ChanceResult::chance),
            Codec.INT.fieldOf("minimum_guarantee_count").forGetter(ChanceResult::minimum_guarantee_count)
    ).apply(inst, ChanceResult::new));
}

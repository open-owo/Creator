package com.xiyue.creator.Datagen;

import com.mojang.serialization.MapCodec;
import com.xiyue.creator.Creator;
import com.xiyue.creator.Datagen.Provider.LootTableModifier.ShearsToDropModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModGLM {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Creator.MODID);

    public static final Supplier<MapCodec<ShearsToDropModifier>> GRASS_DROP =
            SERIALIZERS.register("grass_drop", () -> ShearsToDropModifier.CODEC);
}

package com.xiyue.creator.tag;

import com.xiyue.creator.Creator;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeTag {
    //我的群系tags
    public static final TagKey<Biome> IS_PLAIN = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_plains"));
    public static final TagKey<Biome> IS_CHERRY_GROVE = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_cherry_grove"));
    public static final TagKey<Biome> IS_DESERT = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_desert"));
    public static final TagKey<Biome> IS_DARK_FOREST = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_dark_forest"));
    public static final TagKey<Biome> IS_BADLANDS = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_badlands"));
    public static final TagKey<Biome> IS_GIANT_TREE_TAIGA = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_giant_tree_taiga"));
    public static final TagKey<Biome> IS_JUNGLE = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_jungle"));
    public static final TagKey<Biome> IS_SNOWY_TAIGA = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_snowy_taiga"));
    public static final TagKey<Biome> IS_SAVANNA = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_savanna"));
    public static final TagKey<Biome> IS_MOUNTAIN = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_mountain"));
    public static final TagKey<Biome> IS_BEACH = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_beach"));
    public static final TagKey<Biome> IS_RIVER = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_river"));
    public static final TagKey<Biome> IS_BIRCH_FOREST = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_birch_forest"));
    public static final TagKey<Biome> IS_FOREST = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_forest"));
    public static final TagKey<Biome> IS_TAIGA = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_taiga"));
    public static final TagKey<Biome> IS_SWAMP = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_swamp"));
    public static final TagKey<Biome> IS_MUSHROOM_FIELD = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_mushroom_field"));
    public static final TagKey<Biome> IS_SNOWY_PLAIN = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_snowy_plain"));
    public static final TagKey<Biome> IS_OCEAN = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "is_ocean"));
}

package com.xiyue.creator.tag;

import com.xiyue.creator.Creator;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class ItemTag {
    public static final TagKey<Item> WOODEN_STRAINER_FRAME = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "wooden_strainer_frames"));
    public static final TagKey<Item> STONE_STRAINER_FRAME = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "stone_strainer_frames"));
    public static final TagKey<Item> METAL_STRAINER_FRAME = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "metal_strainer_frames"));
    public static final TagKey<Item> STRAINER_FRAME = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "strainer_frames"));

    public static final TagKey<Item> MESH = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "meshes"));
    public static final TagKey<Item> LAVA_MESH = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "lava_meshes"));

    public static final TagKey<Item> KNIFE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/knife"));
    public static final TagKey<Item> STRING = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "strings"));
    public static final TagKey<Item> BAMBOO = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "bamboos"));
    public static final TagKey<Item> STRAW = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "straw"));
    public static final TagKey<Item> ROOD_WOODEN = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "rods/wooden"));
    public static final TagKey<Item> FLINT = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "gems/flint"));

    public static final TagKey<Item> BIRCH_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "birch_lumbers"));
    public static final TagKey<Item> ACACIA_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "acacia_lumbers"));
    public static final TagKey<Item> CHERRY_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "cherry_lumbers"));
    public static final TagKey<Item> CRIMSON_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "crimson_lumbers"));
    public static final TagKey<Item> DARK_OAK_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dark_oak_lumbers"));
    public static final TagKey<Item> JUNGLE_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "jungle_lumbers"));
    public static final TagKey<Item> MANGROVE_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "mangrove_lumbers"));
    public static final TagKey<Item> OAK_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "oak_lumbers"));
    public static final TagKey<Item> SPRUCE_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "spruce_lumbers"));
    public static final TagKey<Item> WARPED_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "warped_lumbers"));
    public static final TagKey<Item> RUBBER_LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "rubber_lumbers"));
    public static final TagKey<Item> LUMBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "lumbers"));

    public static final TagKey<Item> RUBBER_LOG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "rubber_logs"));
    public static final TagKey<Item> BIRCH_LOG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "birch_logs"));
    public static final TagKey<Item> ACACIA_LOG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "acacia_logs"));
    public static final TagKey<Item> CHERRY_LOG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "cherry_logs"));
    public static final TagKey<Item> CRIMSON_STEM = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "crimos_stems"));
    public static final TagKey<Item> DARK_OAK_LOG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dark_oak_logs"));
    public static final TagKey<Item> JUNGLE_LOG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "jungle_logs"));
    public static final TagKey<Item> MANGROVE_LOG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "mangrove_logs"));
    public static final TagKey<Item> OAK_LOG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "oak_logs"));
    public static final TagKey<Item> SPRUCE_LOG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "spruce_logs"));
    public static final TagKey<Item> WARPED_STEM = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "warped_stems"));
    public static final TagKey<Item> LOG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "logs"));

    public static final TagKey<Item> BIRCH_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "birch_barks"));
    public static final TagKey<Item> ACACIA_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "acacia_barks"));
    public static final TagKey<Item> CHERRY_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "cherry_barks"));
    public static final TagKey<Item> CRIMSON_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "crimson_barks"));
    public static final TagKey<Item> DARK_OAK_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dark_oak_barks"));
    public static final TagKey<Item> JUNGLE_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "jungle_barks"));
    public static final TagKey<Item> MANGROVE_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "mangrove_barks"));
    public static final TagKey<Item> OAK_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "oak_barks"));
    public static final TagKey<Item> SPRUCE_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "spruce_barks"));
    public static final TagKey<Item> WARPED_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "warped_barks"));
    public static final TagKey<Item> RUBBER_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "rubber_barks"));
    public static final TagKey<Item> BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "barks"));

    public static final TagKey<Item> DRY_BARK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dry_barks"));
    public static final TagKey<Item> DRY_BAMBOO = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dry_bamboo.json"));
    public static final TagKey<Item> PLANT_FIBER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "plant_fiber"));

}

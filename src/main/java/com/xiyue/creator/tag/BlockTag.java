package com.xiyue.creator.tag;

import com.xiyue.creator.Creator;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockTag {
    //我的标签
    public static final TagKey<Block> WOODEN_STRAINER_FRAME = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "wooden_strainer_frames"));
    public static final TagKey<Block> STONE_STRAINER_FRAME = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "stone_strainer_frames"));
    public static final TagKey<Block> METAL_STRAINER_FRAME = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "metal_strainer_frames"));
    public static final TagKey<Block> STRAINER_FRAME = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "strainer_frames"));
    public static final TagKey<Block> PLANTS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "plants"));

    public static final TagKey<Block> RUBBER_LOG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "rubber_logs"));
    public static final TagKey<Block> BIRCH_LOG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "birch_logs"));
    public static final TagKey<Block> ACACIA_LOG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "acacia_logs"));
    public static final TagKey<Block> CHERRY_LOG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "cherry_logs"));
    public static final TagKey<Block> CRIMSON_STEM = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "crimos_stems"));
    public static final TagKey<Block> DARK_OAK_LOG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "dark_oak_logs"));
    public static final TagKey<Block> JUNGLE_LOG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "jungle_logs"));
    public static final TagKey<Block> MANGROVE_LOG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "mangrove_logs"));
    public static final TagKey<Block> OAK_LOG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "oak_logs"));
    public static final TagKey<Block> SPRUCE_LOG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "spruce_logs"));
    public static final TagKey<Block> WARPED_STEM = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "warped_stems"));
    public static final TagKey<Block> LOG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "logs"));
}

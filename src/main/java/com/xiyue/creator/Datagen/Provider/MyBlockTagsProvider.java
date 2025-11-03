package com.xiyue.creator.Datagen.Provider;

import com.xiyue.creator.Integration.GT.GTceuIntegration.GTRegistryHelper;
import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.tag.BlockTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MyBlockTagsProvider extends BlockTagsProvider {
    public MyBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTag.WOODEN_STRAINER_FRAME).add(ModBlockGroup.OAK_STRAINER_FRAME.getKey(), ModBlockGroup.ACACIA_STRAINER_FRAME.getKey(), ModBlockGroup.BIRCH_STRAINER_FRAME.getKey(), ModBlockGroup.CHERRY_STRAINER_FRAME.getKey(), ModBlockGroup.DARK_OAK_STRAINER_FRAME.getKey(), ModBlockGroup.JUNGLE_STRAINER_FRAME.getKey(), ModBlockGroup.MANGROVE_STRAINER_FRAME.getKey(), ModBlockGroup.SPRUCE_STRAINER_FRAME.getKey(), ModBlockGroup.CRIMSON_STRAINER_FRAME.getKey(), ModBlockGroup.WARPED_STRAINER_FRAME.getKey());
        tag(BlockTag.STONE_STRAINER_FRAME).add(ModBlockGroup.STONE_STRAINER_FRAME.getKey());
        tag(BlockTag.METAL_STRAINER_FRAME).add(ModBlockGroup.IRON_STRAINER_FRAME.getKey());

        tag(BlockTag.STRAINER_FRAME).addTags(BlockTag.WOODEN_STRAINER_FRAME, BlockTag.STONE_STRAINER_FRAME, BlockTag.METAL_STRAINER_FRAME);
        tag(BlockTags.MINEABLE_WITH_AXE).addTag(BlockTag.WOODEN_STRAINER_FRAME);
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlockGroup.DRYING_RACK.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTags(BlockTag.METAL_STRAINER_FRAME, BlockTag.STONE_STRAINER_FRAME);

        if(GTRegistryHelper.getRubberLog() != null) tag(BlockTag.RUBBER_LOG).add(GTRegistryHelper.getRubberLog());
        if(GTRegistryHelper.getRubberWood() != null) tag(BlockTag.RUBBER_LOG).add(GTRegistryHelper.getRubberWood());
        tag(BlockTag.BIRCH_LOG).add(Blocks.BIRCH_LOG, Blocks.BIRCH_WOOD);
        tag(BlockTag.ACACIA_LOG).add(Blocks.ACACIA_LOG, Blocks.ACACIA_WOOD);
        tag(BlockTag.CHERRY_LOG).add(Blocks.CHERRY_LOG, Blocks.CHERRY_WOOD);
        tag(BlockTag.CRIMSON_STEM).add(Blocks.CRIMSON_STEM, Blocks.CRIMSON_HYPHAE);
        tag(BlockTag.DARK_OAK_LOG).add(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_WOOD);
        tag(BlockTag.JUNGLE_LOG).add(Blocks.JUNGLE_LOG, Blocks.JUNGLE_WOOD);
        tag(BlockTag.MANGROVE_LOG).add(Blocks.MANGROVE_LOG, Blocks.MANGROVE_WOOD);
        tag(BlockTag.OAK_LOG).add(Blocks.OAK_LOG, Blocks.OAK_WOOD);
        tag(BlockTag.SPRUCE_LOG).add(Blocks.SPRUCE_LOG, Blocks.SPRUCE_WOOD);
        tag(BlockTag.WARPED_STEM).add(Blocks.WARPED_STEM, Blocks.WARPED_HYPHAE);

        tag(BlockTag.LOG).addTags(BlockTag.RUBBER_LOG, BlockTag.BIRCH_LOG, BlockTag.ACACIA_LOG, BlockTag.CHERRY_LOG, BlockTag.CRIMSON_STEM, BlockTag.DARK_OAK_LOG, BlockTag.JUNGLE_LOG, BlockTag.MANGROVE_LOG, BlockTag.OAK_LOG, BlockTag.SPRUCE_LOG, BlockTag.WARPED_STEM);
        tag(BlockTag.PLANTS).add(Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN, Blocks.CRIMSON_ROOTS, Blocks.WARPED_ROOTS, Blocks.NETHER_SPROUTS, Blocks.WEEPING_VINES, Blocks.TWISTING_VINES, Blocks.SEAGRASS, Blocks.DEAD_BUSH, Blocks.SUGAR_CANE, Blocks.BIG_DRIPLEAF, Blocks.CRIMSON_FUNGUS, Blocks.WARPED_FUNGUS, Blocks.HANGING_ROOTS, Blocks.SMALL_DRIPLEAF, Blocks.GLOW_LICHEN, Blocks.KELP, Blocks.NETHER_WART, Blocks.SEA_PICKLE, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM);
        tag(BlockTags.MINEABLE_WITH_HOE).addTags(BlockTag.PLANTS, BlockTags.FLOWERS, BlockTags.SAPLINGS, BlockTags.CORAL_PLANTS, BlockTags.CROPS);
        tag(BlockTags.MINEABLE_WITH_SHOVEL).addTags(BlockTag.PLANTS, BlockTags.FLOWERS, BlockTags.SAPLINGS, BlockTags.CORAL_PLANTS, BlockTags.CROPS);
    }
}

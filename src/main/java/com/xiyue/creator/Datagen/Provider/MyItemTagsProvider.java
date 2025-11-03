package com.xiyue.creator.Datagen.Provider;

import com.xiyue.creator.ModItems.FunctionItems.Meshes;
import com.xiyue.creator.ModItems.ModItemGroup;
import com.xiyue.creator.tag.BlockTag;
import com.xiyue.creator.tag.ItemTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MyItemTagsProvider extends ItemTagsProvider {

    public MyItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, modId, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        copy(BlockTag.METAL_STRAINER_FRAME, ItemTag.METAL_STRAINER_FRAME);
        copy(BlockTag.STONE_STRAINER_FRAME, ItemTag.STONE_STRAINER_FRAME);
        copy(BlockTag.WOODEN_STRAINER_FRAME, ItemTag.WOODEN_STRAINER_FRAME);
        copy(BlockTag.STRAINER_FRAME, ItemTag.STRAINER_FRAME);

        copy(BlockTag.RUBBER_LOG, ItemTag.RUBBER_LOG);
        copy(BlockTag.BIRCH_LOG, ItemTag.BIRCH_LOG);
        copy(BlockTag.ACACIA_LOG, ItemTag.ACACIA_LOG);
        copy(BlockTag.CHERRY_LOG, ItemTag.CHERRY_LOG);
        copy(BlockTag.CRIMSON_STEM, ItemTag.CRIMSON_STEM);
        copy(BlockTag.DARK_OAK_LOG, ItemTag.DARK_OAK_LOG);
        copy(BlockTag.JUNGLE_LOG, ItemTag.JUNGLE_LOG);
        copy(BlockTag.MANGROVE_LOG, ItemTag.MANGROVE_LOG);
        copy(BlockTag.OAK_LOG, ItemTag.OAK_LOG);
        copy(BlockTag.SPRUCE_LOG, ItemTag.SPRUCE_LOG);
        copy(BlockTag.WARPED_STEM, ItemTag.WARPED_STEM);
        tag(ItemTag.LOG).addTags(ItemTag.RUBBER_LOG, ItemTag.BIRCH_LOG, ItemTag.ACACIA_LOG, ItemTag.CHERRY_LOG, ItemTag.CRIMSON_STEM, ItemTag.DARK_OAK_LOG, ItemTag.JUNGLE_LOG, ItemTag.MANGROVE_LOG, ItemTag.OAK_LOG, ItemTag.SPRUCE_LOG, ItemTag.WARPED_STEM);

        for (int i = 0; i < ModItemGroup.ITEM_GROUP.size(); i++) {
            if(ModItemGroup.ITEM_GROUP.get(i).asItem() instanceof Meshes meshes){
                tag(ItemTag.MESH).add(ModItemGroup.ITEM_GROUP.get(i).getKey());
                if (meshes.getCan_used_in_lava()) tag(ItemTag.LAVA_MESH).add(ModItemGroup.ITEM_GROUP.get(i).getKey());
            }
        }
        tag(ItemTag.BIRCH_LUMBER).add(ModItemGroup.BIRCH_LUMBER.getKey());
        tag(ItemTag.ACACIA_LUMBER).add(ModItemGroup.ACACIA_LUMBER.getKey());
        tag(ItemTag.CHERRY_LUMBER).add(ModItemGroup.CHERRY_LUMBER.getKey());
        tag(ItemTag.CRIMSON_LUMBER).add(ModItemGroup.CRIMSON_LUMBER.getKey());
        tag(ItemTag.DARK_OAK_LUMBER).add(ModItemGroup.DARK_OAK_LUMBER.getKey());
        tag(ItemTag.JUNGLE_LUMBER).add(ModItemGroup.JUNGLE_LUMBER.getKey());
        tag(ItemTag.MANGROVE_LUMBER).add(ModItemGroup.MANGROVE_LUMBER.getKey());
        tag(ItemTag.OAK_LUMBER).add(ModItemGroup.OAK_LUMBER.getKey());
        tag(ItemTag.SPRUCE_LUMBER).add(ModItemGroup.SPRUCE_LUMBER.getKey());
        tag(ItemTag.WARPED_LUMBER).add(ModItemGroup.WARPED_LUMBER.getKey());
        tag(ItemTag.RUBBER_LUMBER).add(ModItemGroup.RUBBER_LUMBER.getKey());
        tag(ItemTag.LUMBER).addTags(ItemTag.BIRCH_LUMBER,ItemTag.ACACIA_LUMBER,ItemTag.CHERRY_LUMBER,ItemTag.CRIMSON_LUMBER,ItemTag.DARK_OAK_LUMBER,ItemTag.JUNGLE_LUMBER,ItemTag.OAK_LUMBER,ItemTag.SPRUCE_LUMBER,ItemTag.WARPED_LUMBER,ItemTag.RUBBER_LUMBER);

        tag(ItemTag.BIRCH_BARK).add(ModItemGroup.BIRCH_BARK.getKey());
        tag(ItemTag.ACACIA_BARK).add(ModItemGroup.ACACIA_BARK.getKey());
        tag(ItemTag.CHERRY_BARK).add(ModItemGroup.CHERRY_BARK.getKey());
        tag(ItemTag.CRIMSON_BARK).add(ModItemGroup.CRIMSON_BARK.getKey());
        tag(ItemTag.DARK_OAK_BARK).add(ModItemGroup.DARK_OAK_BARK.getKey());
        tag(ItemTag.JUNGLE_BARK).add(ModItemGroup.JUNGLE_BARK.getKey());
        tag(ItemTag.MANGROVE_BARK).add(ModItemGroup.MANGROVE_BARK.getKey());
        tag(ItemTag.OAK_BARK).add(ModItemGroup.OAK_BARK.getKey());
        tag(ItemTag.SPRUCE_BARK).add(ModItemGroup.SPRUCE_BARK.getKey());
        tag(ItemTag.WARPED_BARK).add(ModItemGroup.WARPED_BARK.getKey());
        tag(ItemTag.RUBBER_BARK).add(ModItemGroup.RUBBER_BARK.getKey());
        tag(ItemTag.DRY_BARK).add(ModItemGroup.DRY_BARK.getKey());
        tag(ItemTag.BARK).addTags(ItemTag.MANGROVE_BARK,ItemTag.BIRCH_BARK,ItemTag.ACACIA_BARK,ItemTag.CHERRY_BARK,ItemTag.CRIMSON_BARK,ItemTag.DARK_OAK_BARK,ItemTag.JUNGLE_BARK,ItemTag.OAK_BARK,ItemTag.SPRUCE_BARK,ItemTag.WARPED_BARK,ItemTag.RUBBER_BARK);

        tag(ItemTag.DRY_BAMBOO).add(ModItemGroup.DRY_BAMBOO.getKey());
        tag(ItemTag.PLANT_FIBER).add(ModItemGroup.PLANT_FIBER.getKey());
        tag(ItemTag.BAMBOO).add(Items.BAMBOO);


    }
}

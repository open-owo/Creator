package com.xiyue.creator.Datagen.Provider.LootTable;

import com.xiyue.creator.BuilderSystem.BuilderSystem;
import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.ModItems.ModItemGroup;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class MyBlockLootSubProvider extends BlockLootSubProvider {
    public MyBlockLootSubProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlockGroup.BLOCKS.getEntries()
                .stream()
                .map(e -> (Block) e.value())
                .toList();
    }

    @Override
    protected void generate() {
        for (int i = 0; i < ModBlockGroup.blocks.size(); i++) {
            dropSelf(ModBlockGroup.blocks.get(i).get());
        }
    }
}

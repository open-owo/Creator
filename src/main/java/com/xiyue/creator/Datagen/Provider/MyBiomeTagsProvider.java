package com.xiyue.creator.Datagen.Provider;

import com.xiyue.creator.tag.BiomeTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MyBiomeTagsProvider extends BiomeTagsProvider {

    public MyBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider lookupProvider){
        tag(BiomeTag.IS_PLAIN).add(Biomes.PLAINS,Biomes.SUNFLOWER_PLAINS);
        tag(BiomeTag.IS_CHERRY_GROVE).add(Biomes.CHERRY_GROVE);
        tag(BiomeTag.IS_DESERT).add(Biomes.DESERT);
        tag(BiomeTag.IS_DARK_FOREST).add(Biomes.DARK_FOREST);
        tag(BiomeTag.IS_BADLANDS).add(Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS);
        tag(BiomeTag.IS_GIANT_TREE_TAIGA).add(Biomes.OLD_GROWTH_PINE_TAIGA,Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        tag(BiomeTag.IS_JUNGLE).add(Biomes.JUNGLE,Biomes.BAMBOO_JUNGLE,Biomes.JUNGLE,Biomes.SPARSE_JUNGLE);
        tag(BiomeTag.IS_SNOWY_TAIGA).add(Biomes.SNOWY_TAIGA);
        tag(BiomeTag.IS_SAVANNA).add(Biomes.SAVANNA,Biomes.SAVANNA_PLATEAU,Biomes.WINDSWEPT_SAVANNA);
        tag(BiomeTag.IS_MOUNTAIN).add(Biomes.WINDSWEPT_FOREST,Biomes.STONY_PEAKS,Biomes.WINDSWEPT_GRAVELLY_HILLS,Biomes.FROZEN_PEAKS,Biomes.MEADOW,Biomes.GROVE,Biomes.JAGGED_PEAKS,Biomes.SNOWY_SLOPES);
        tag(BiomeTag.IS_BEACH).add(Biomes.STONY_SHORE,Biomes.SNOWY_BEACH);
        tag(BiomeTag.IS_RIVER).add(Biomes.RIVER,Biomes.FROZEN_RIVER);
        tag(BiomeTag.IS_BIRCH_FOREST).add(Biomes.BIRCH_FOREST,Biomes.OLD_GROWTH_BIRCH_FOREST);
        tag(BiomeTag.IS_FOREST).add(Biomes.FOREST,Biomes.FLOWER_FOREST);
        tag(BiomeTag.IS_TAIGA).add(Biomes.TAIGA);
        tag(BiomeTag.IS_SWAMP).add(Biomes.SWAMP,Biomes.MANGROVE_SWAMP);
        tag(BiomeTag.IS_MUSHROOM_FIELD).add(Biomes.MUSHROOM_FIELDS);
        tag(BiomeTag.IS_SNOWY_PLAIN).add(Biomes.SNOWY_PLAINS,Biomes.ICE_SPIKES);
        tag(BiomeTag.IS_OCEAN).add(Biomes.OCEAN,Biomes.WARM_OCEAN,Biomes.LUKEWARM_OCEAN,Biomes.COLD_OCEAN,Biomes.FROZEN_OCEAN,Biomes.DEEP_OCEAN,Biomes.DEEP_LUKEWARM_OCEAN,Biomes.DEEP_COLD_OCEAN,Biomes.DEEP_FROZEN_OCEAN);
    }
}

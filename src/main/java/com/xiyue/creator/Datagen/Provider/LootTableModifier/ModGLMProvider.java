package com.xiyue.creator.Datagen.Provider.LootTableModifier;

import com.xiyue.creator.Creator;
import com.xiyue.creator.Integration.GT.GTceuIntegration.GTRegistryHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGLMProvider extends GlobalLootModifierProvider {

    public ModGLMProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Creator.MODID);
    }

    @Override
    protected void start() {
        add("short_grass_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.SHORT_GRASS.getLootTable().location()).build()},
                        0.2f, Items.SHORT_GRASS.getDefaultInstance())
        );

        add("fern_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.FERN.getLootTable().location()).build()},
                        0.2f, Items.FERN.getDefaultInstance())
        );

        add("tall_grass_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.TALL_GRASS.getLootTable().location()).build()},
                        0.2f, Items.TALL_GRASS.getDefaultInstance())
        );

        add("large_fern_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.LARGE_FERN.getLootTable().location()).build()},
                        0.2f, Items.LARGE_FERN.getDefaultInstance())
        );

        add("oak_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.OAK_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.OAK_LEAVES.asItem().getDefaultInstance())
        );
        add("spruce_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.SPRUCE_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.SPRUCE_LEAVES.asItem().getDefaultInstance())
        );

        add( "flowering_azalea_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.FLOWERING_AZALEA_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.FLOWERING_AZALEA_LEAVES.asItem().getDefaultInstance())
        );
        add( "azalea_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.AZALEA_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.AZALEA_LEAVES.asItem().getDefaultInstance())
        );
        add( "oak_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.OAK_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.OAK_LEAVES.asItem().getDefaultInstance())
        );
        add( "cherry_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.CHERRY_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.CHERRY_LEAVES.asItem().getDefaultInstance())
        );
        add( "mangrove_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.MANGROVE_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.MANGROVE_LEAVES.asItem().getDefaultInstance())
        );
        add( "dark_oak_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.DARK_OAK_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.DARK_OAK_LEAVES.asItem().getDefaultInstance())
        );
        add( "acacia_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.ACACIA_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.ACACIA_LEAVES.asItem().getDefaultInstance())
        );
        add( "jungle_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.JUNGLE_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.JUNGLE_LEAVES.asItem().getDefaultInstance())
        );
        add( "birch_leaves_modifier",
                new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(Blocks.BIRCH_LEAVES.getLootTable().location()).build()},
                        0.4f, Blocks.BIRCH_LEAVES.asItem().getDefaultInstance())
        );
        if (Creator.HAS_GTCEU) {
            add( "rubber_leaves_modifier",
                    new ShearsToDropModifier(new LootItemCondition[]{LootTableIdCondition.builder(GTRegistryHelper.getRubberLeaves().getLootTable().location()).build()},
                            0.4f, GTRegistryHelper.getRubberLeaves().asItem().getDefaultInstance())
            );
        }
    }
}

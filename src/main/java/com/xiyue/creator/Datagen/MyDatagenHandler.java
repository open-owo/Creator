package com.xiyue.creator.Datagen;

import com.xiyue.creator.Creator;
import com.xiyue.creator.Datagen.Provider.*;
import com.xiyue.creator.Datagen.Provider.LootTable.MyLootTableProvider;
import com.xiyue.creator.Datagen.Provider.LootTableModifier.ModGLMProvider;
import com.xiyue.creator.Datagen.Provider.MyRecipe.MyRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber()
public class MyDatagenHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        BlockTagsProvider blockTags = new MyBlockTagsProvider(output, lookupProvider, Creator.MODID, existingFileHelper);
        //生物群系
        event.getGenerator().addProvider(
                event.includeServer(),
                new MyBiomeTagsProvider(output, lookupProvider, Creator.MODID, existingFileHelper)
        );
        //方块
        event.getGenerator().addProvider(
                event.includeServer(),
                blockTags
        );
        //物品
        event.getGenerator().addProvider(
                event.includeServer(),
                new MyItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), Creator.MODID, existingFileHelper)
        );
        //方块掉落
        event.getGenerator().addProvider(
                event.includeServer(),
                new MyLootTableProvider(output, lookupProvider)
        );
        //配方
        event.getGenerator().addProvider(
                event.includeServer(),
                new MyRecipeProvider(output, lookupProvider)
        );
        //战利品修改
        event.getGenerator().addProvider(
                event.includeServer(),
                new ModGLMProvider(output, event.getLookupProvider())
        );

    }
}

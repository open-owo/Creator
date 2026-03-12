package com.xiyue.creator;


import com.mojang.logging.LogUtils;
import com.xiyue.creator.Datagen.ModGLM;
import com.xiyue.creator.ModBlockEntities.ModBlockEntities;
import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.ModGUIS.ModMenus;
import com.xiyue.creator.ModItems.ModItemGroup;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;


import java.util.List;

@Mod(Creator.MODID)
public class Creator {
    public static final String MODID = "creator";
    public static final boolean HAS_GTCEU = true;
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Creator.MODID);
    DeferredHolder<CreativeModeTab, CreativeModeTab> CREATOR_CREATIVE_TAB = CREATIVE_MODE_TABS.register("creator",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + Creator.MODID + ".creator"))
                    .icon(() -> new ItemStack(ModItemGroup.BIRCH_BARK.get()))
                    .displayItems((params, output) -> {
                        List<DeferredHolder<Item, ? extends Item>> list = ModItemGroup.ITEMS.getEntries().stream().toList();
                        for (DeferredHolder<Item, ? extends Item> item : list) {
                            output.accept(item.get());
                        }
                    })
                    .build());


    public Creator(IEventBus modEventBus){
        CREATIVE_MODE_TABS.register(modEventBus);
        ModBlockGroup.BLOCKS.register(modEventBus);
        ModBlockGroup.BUILDER_REGISTER.register(modEventBus);
        ModItemGroup.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModMenus.REGISTER.register(modEventBus);
        RegisterRecipe.RECIPE_TYPES.register(modEventBus);
        RegisterRecipe.RECIPE_SERIALIZERS.register(modEventBus);
        ModGLM.SERIALIZERS.register(modEventBus);
    }
}

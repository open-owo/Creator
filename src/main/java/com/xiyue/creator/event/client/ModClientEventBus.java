package com.xiyue.creator.event.client;

import com.xiyue.creator.Creator;
import com.xiyue.creator.KeyMapping.KeyBinding;
import com.xiyue.creator.ModBlockEntities.ModBlockEntities;
import com.xiyue.creator.ModGUIS.Menus.StrainerFrameMenu;
import com.xiyue.creator.ModGUIS.ModMenus;
import com.xiyue.creator.ModGUIS.Screens.BuilderGUI;
import com.xiyue.creator.ModGUIS.Screens.StrainerFrameGUI;
import com.xiyue.creator.ModItems.ModItemGroup;
import com.xiyue.creator.api.BlockRenderer.Builder.BuilderRenderer;
import com.xiyue.creator.api.BlockRenderer.DryingRackRenderer;
import com.xiyue.creator.api.ModGUIS.Menus.MenuGuiDefinition;
import com.xiyue.creator.api.registry.MyRegistry.MachineTypeDeferredRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import com.xiyue.creator.api.BlockRenderer.StrainerFrameRenderer;

@EventBusSubscriber(modid = Creator.MODID, value = Dist.CLIENT)
public class ModClientEventBus {

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.BUILD_KEY.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (KeyBinding.BUILD_KEY.get().consumeClick()) {
            Minecraft.getInstance().setScreen(new BuilderGUI(Minecraft.getInstance().level));
        }
    }

    @SubscribeEvent
    public static void registerMenuScreen(RegisterMenuScreensEvent event){
        event.<StrainerFrameMenu, StrainerFrameGUI<StrainerFrameMenu>>register(ModBlockEntities.STRAINER_FRAME.get().getMenuType().get(), (menu, inv, title) -> new StrainerFrameGUI<>(menu, inv, title, ModBlockEntities.STRAINER_FRAME.get().getGuiDef()));
        event.<StrainerFrameMenu, StrainerFrameGUI<StrainerFrameMenu>>register(ModBlockEntities.STONE_STRAINER_FRAME.get().getMenuType().get(), (menu, inv, title) -> new StrainerFrameGUI<>(menu, inv, title, ModBlockEntities.STONE_STRAINER_FRAME.get().getGuiDef()));
        event.<StrainerFrameMenu, StrainerFrameGUI<StrainerFrameMenu>>register(ModBlockEntities.IRON_STRAINER_FRAME.get().getMenuType().get(), (menu, inv, title) -> new StrainerFrameGUI<>(menu, inv, title, ModBlockEntities.IRON_STRAINER_FRAME.get().getGuiDef()));

       // event.<StrainerFrameMenu, StrainerFrameGUI<StrainerFrameMenu>>register(ModMenus.STRAINER_MENU.get(), (menu, playerInventory, title) -> new StrainerFrameGUI<>(menu, playerInventory, title, "strainer_frame_gui", 193, 176));
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.STRAINER_FRAME.get().getBlockEntityFactory().get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.IRON_STRAINER_FRAME.get().getBlockEntityFactory().get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.STONE_STRAINER_FRAME.get().getBlockEntityFactory().get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.BUILDER_BLOCK.get(),
                BuilderRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.DRYING_RACK.get().getBlockEntityFactory().get(),
                DryingRackRenderer::new
        );
    }
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> 0xFFBA6337, ModItemGroup.ACACIA_LUMBER);
        event.register((stack, tintIndex) -> 0xFFD7C185, ModItemGroup.BIRCH_LUMBER);
        event.register((stack, tintIndex) -> 0xFFE7C2BB, ModItemGroup.CHERRY_LUMBER);
        event.register((stack, tintIndex) -> 0xFF7E3A56, ModItemGroup.CRIMSON_LUMBER);
        event.register((stack, tintIndex) -> 0xFF4F3218, ModItemGroup.DARK_OAK_LUMBER);
        event.register((stack, tintIndex) -> 0xFFB88764, ModItemGroup.JUNGLE_LUMBER);
        event.register((stack, tintIndex) -> 0xFF7F4234, ModItemGroup.MANGROVE_LUMBER);
        event.register((stack, tintIndex) -> 0xFFB8945F, ModItemGroup.OAK_LUMBER);
        event.register((stack, tintIndex) -> 0xFF82613A, ModItemGroup.SPRUCE_LUMBER);
        event.register((stack, tintIndex) -> 0xFF398382, ModItemGroup.WARPED_LUMBER);
        event.register((stack, tintIndex) -> 0xFF5A4530, ModItemGroup.RUBBER_LUMBER);
    }
}

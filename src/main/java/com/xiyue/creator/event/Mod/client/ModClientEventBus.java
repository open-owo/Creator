package com.xiyue.creator.event.Mod.client;

import com.xiyue.creator.Creator;
import com.xiyue.creator.KeyMapping.KeyBinding;
import com.xiyue.creator.ModBlockEntities.ModBlockEntities;
import com.xiyue.creator.ModGUIS.ModMenus;
import com.xiyue.creator.ModGUIS.Screens.StrainerFrame.*;
import com.xiyue.creator.ModItems.ModItemGroup;
import com.xiyue.creator.api.BlockRenderer.Builder.BuilderRenderer;
import com.xiyue.creator.api.BlockRenderer.DryingRackRenderer;
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
    public static void registerMenuScreen(RegisterMenuScreensEvent event){
        event.register(ModMenus.OAK_STRAINER_FRAME_MENU.get(), OakStrainerFrameGUI::new);
        event.register(ModMenus.ACACIA_STRAINER_FRAME_MENU.get(), AcaciaStrainerFrameGUI::new);
        event.register(ModMenus.BIRCH_STRAINER_FRAME_MENU.get(), BirchStrainerFrameGUI::new);
        event.register(ModMenus.CHERRY_STRAINER_FRAME_MENU.get(), CherryStrainerFrameGUI::new);
        event.register(ModMenus.DARK_OAK_STRAINER_FRAME_MENU.get(), DarkOakStrainerFrameGUI::new);
        event.register(ModMenus.JUNGLE_STRAINER_FRAME_MENU.get(), JungleStrainerFrameGUI::new);
        event.register(ModMenus.MANGROVE_STRAINER_FRAME_MENU.get(), MangroveStrainerFrameGUI::new);
        event.register(ModMenus.SPRUCE_STRAINER_FRAME_MENU.get(), SpruceStrainerFrameGUI::new);
        event.register(ModMenus.CRIMSON_STRAINER_FRAME_MENU.get(), CrimsonStrainerFrameGUI::new);
        event.register(ModMenus.WARPED_STRAINER_FRAME_MENU.get(), WarpedStrainerFrameGUI::new);
        event.register(ModMenus.RUBBER_STRAINER_FRAME_MENU.get(), RubberStrainerFrameGUI::new);
        event.register(ModMenus.IRON_STRAINER_FRAME_MENU.get(), IronStrainerFrameGUI::new);
        event.register(ModMenus.STONE_STRAINER_FRAME_MENU.get(), StoneStrainerFrameGUI::new);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.OAK_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.ACACIA_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.BIRCH_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.CHERRY_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.DARK_OAK_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.JUNGLE_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.MANGROVE_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.SPRUCE_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.CRIMSON_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.WARPED_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.IRON_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.STONE_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.RUBBER_STRAINER_FRAME.get(),
                StrainerFrameRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.BUILDER_BLOCK.get(),
                BuilderRenderer::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.DRYING_RACK.get(),
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

package com.xiyue.creator.event.common;

import com.xiyue.creator.Creator;
import com.xiyue.creator.Handler.DryingRack.DryingRackItemHandler;
import com.xiyue.creator.ModBlockEntities.ModBlockEntities;
import com.xiyue.creator.networking.BuilderBlockNetworking.BuildingPacket;
import com.xiyue.creator.networking.BuilderBlockNetworking.BuildingPayloadClient;
import com.xiyue.creator.networking.BuilderBlockNetworking.BuildingPayloadServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Creator.MODID)
public class ModEventBus {

    @SubscribeEvent
    public static void PayloadRegister(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(BuildingPacket.TYPE, BuildingPacket.STREAM_CODEC,
            new DirectionalPayloadHandler<>(
                    BuildingPayloadClient::handle,
                    BuildingPayloadServer::handle
                )
        );
    }

    @SubscribeEvent
    //注册方块能力
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.STRAINER_FRAME.get().getBlockEntityFactory().get(),
                (be, side) -> be.getAutomationHandler()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.IRON_STRAINER_FRAME.get().getBlockEntityFactory().get(),
                (be, side) -> be.getAutomationHandler()
        );
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.STONE_STRAINER_FRAME.get().getBlockEntityFactory().get(),
                (be, side) -> be.getAutomationHandler()
        );
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.DRYING_RACK.get().getBlockEntityFactory().get(),
                (be, side) -> new DryingRackItemHandler(be)
        );
    }

}

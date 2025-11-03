package com.xiyue.creator.networking.BuilderBlockNetworking;

import com.xiyue.creator.Creator;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class BuildingPayloadServer {
    public static void handle(final BuildingPacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    context.reply(new BuildingPacket(data.id(), data.BuildingStrainer()));

                    context.player().getPersistentData().putBoolean("BuildingStrainer", data.BuildingStrainer());
                    context.player().getPersistentData().putString("recipe", data.id());
                })
                .exceptionally(e -> {
                    context.disconnect(Component.translatable(Creator.MODID + ".build.networking.failed", e.getMessage()));
                    return null;
                });
    }
}

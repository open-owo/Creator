package com.xiyue.creator.networking.BuilderBlockNetworking;

import com.xiyue.creator.BuilderSystem.BuilderSystem;
import com.xiyue.creator.Creator;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class BuildingPayloadClient {
    public static void handle(final BuildingPacket data, final IPayloadContext context) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        context.enqueueWork(() -> {
            if (!data.BuildingStrainer()) {
                BuilderSystem.reset();
            }

            context.player().getPersistentData().putBoolean("BuildingStrainer", data.BuildingStrainer());
            context.player().getPersistentData().putString("recipe", data.id());
        }).exceptionally(e -> {
            context.disconnect(Component.translatable(Creator.MODID + ".build.networking.failed", e.getMessage()));
            return null;
        });
    }
}

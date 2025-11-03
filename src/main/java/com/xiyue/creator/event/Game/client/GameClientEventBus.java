package com.xiyue.creator.event.Game.client;

import com.xiyue.creator.Creator;
import com.xiyue.creator.KeyMapping.KeyBinding;
import com.xiyue.creator.ModGUIS.Screens.BuilderGUI;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Creator.MODID, value = Dist.CLIENT)
public class GameClientEventBus {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (KeyBinding.BUILD_KEY.get().consumeClick()) {
            Minecraft.getInstance().setScreen(new BuilderGUI(Minecraft.getInstance().level));
        }
    }
}

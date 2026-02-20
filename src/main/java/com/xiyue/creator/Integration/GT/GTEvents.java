package com.xiyue.creator.Integration.GT;

import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.xiyue.creator.Creator;
import com.xiyue.creator.Integration.GT.Material.MyMaterialIcon;
import com.xiyue.creator.Integration.GT.Prefixes.MyPrefixes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

@EventBusSubscriber(modid = Creator.MODID)
public class GTEvents {
    @SubscribeEvent
    public static void Register(RegisterEvent event) {
        event.register(GTRegistries.TAG_PREFIX_REGISTRY, helper -> MyPrefixes.init());
        event.register(GTRegistries.MATERIAL_REGISTRY, helper -> MyMaterialIcon.init());
    }
}

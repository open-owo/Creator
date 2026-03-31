package com.xiyue.creator.api.registry.MyRegistry;

import com.xiyue.creator.Creator;
import com.xiyue.creator.api.registry.type.MachineType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = Creator.MODID)
public class ModRegistries {
    public static final ResourceKey<Registry<MachineType>> MACHINE_TYPE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Creator.MODID, "machine_type"));

    public static final Registry<MachineType> MACHINE_TYPE =
            new RegistryBuilder<>(MACHINE_TYPE_KEY)
                    .sync(true)
                    .defaultKey(ResourceLocation.fromNamespaceAndPath(Creator.MODID, "empty"))
                    .create();

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(MACHINE_TYPE);
    }
}
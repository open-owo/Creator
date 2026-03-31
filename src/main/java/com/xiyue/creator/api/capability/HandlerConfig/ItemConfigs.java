package com.xiyue.creator.api.capability.HandlerConfig;

import com.xiyue.creator.ModItems.FunctionItems.Meshes;
import com.xiyue.creator.api.capability.HandlerContext;
import com.xiyue.creator.api.capability.SlotType;
import com.xiyue.creator.api.capability.item.ItemConfig;

public class ItemConfigs {
    public static final ItemConfig STRAINER_FRAME_CONFIG = ItemConfig.builder(28)
            .forContext(HandlerContext.AUTOMATION)
            .setSlotType(0, SlotType.INPUT)
            .setSlotType(1, 27, SlotType.OUTPUT)
            .setInsertRule((slotType, slot,itemStack) -> slotType == SlotType.INPUT && (itemStack.getItem() instanceof Meshes))
            .setExtractRule((slotType, slot,itemStack) -> slotType == SlotType.OUTPUT)
            .endContext()
            .forContext(HandlerContext.PLAYER)
            .copy(HandlerContext.AUTOMATION)
            .setInsertRule((slotType, slot,itemStack) -> (slotType == SlotType.INPUT && itemStack.getItem() instanceof Meshes) || slotType == SlotType.OUTPUT)
            .setExtractRule((slotType, slot,itemStack) -> true)
            .endContext()
            .build();
    public static final ItemConfig IRON_STRAINER_FRAME_CONFIG = ItemConfig.builder(39)
            .forContext(HandlerContext.AUTOMATION)
            .setSlotType(0, SlotType.INPUT)
            .setSlotType(1, 38, SlotType.OUTPUT)
            .setInsertRule((slotType, slot,itemStack) -> slotType == SlotType.INPUT && (itemStack.getItem() instanceof Meshes))
            .setExtractRule((slotType, slot,itemStack) -> slotType == SlotType.OUTPUT)
            .endContext()
            .forContext(HandlerContext.PLAYER)
            .copy(HandlerContext.AUTOMATION)
            .setInsertRule((slotType, slot,itemStack) -> (slotType == SlotType.INPUT && itemStack.getItem() instanceof Meshes) || slotType == SlotType.OUTPUT)
            .setExtractRule((slotType, slot,itemStack) -> true)
            .endContext()
            .build();
    public static final ItemConfig DRYING_RACK_CONFIG = ItemConfig.builder(4)
            .forContext(HandlerContext.AUTOMATION)
            .setSlotType(0, 4, SlotType.BOTH)
            .endContext()
            .forContext(HandlerContext.PLAYER)
            .copy(HandlerContext.AUTOMATION)
            .endContext()
            .build();
}

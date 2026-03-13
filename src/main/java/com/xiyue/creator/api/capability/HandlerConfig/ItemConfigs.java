package com.xiyue.creator.api.capability.HandlerConfig;

import com.xiyue.creator.api.capability.HandlerContext;
import com.xiyue.creator.api.capability.SlotType;
import com.xiyue.creator.api.capability.item.ItemConfig;

public class ItemConfigs {
    public static final ItemConfig STRAINER_FRAME_CONFIG = ItemConfig.builder(28)
            .forContext(HandlerContext.AUTOMATION)
            .setSlotType(0, SlotType.INPUT)
            .setSlotType(1, 27, SlotType.OUTPUT)
            .endContext()
            .forContext(HandlerContext.PLAYER)
            .copy(HandlerContext.AUTOMATION)
            .changeSlotType(SlotType.INPUT, SlotType.BOTH)
            .changeSlotType(SlotType.OUTPUT,SlotType.BOTH)
            .endContext()
            .build();
}

package com.xiyue.creator.api.ModGUIS.Menus.MenuConfigs;

import com.xiyue.creator.api.ModGUIS.Menus.MenuConfig;
import com.xiyue.creator.api.capability.SlotType;

public class MenuConfigs {
    public static final MenuConfig STRAINER_FRAME_MENU = MenuConfig.Builder(8, 111)
            .set(SlotType.INPUT, 80, 18, 1, 1)
            .set(SlotType.OUTPUT, 8,45, 3, 9)
            .build();
    public static final MenuConfig IRON_STRAINER_FRAME_MENU = MenuConfig.Builder(44, 111)
            .set(SlotType.INPUT, 116, 18, 1, 1)
            .set(SlotType.OUTPUT, 8,45, 3, 13)
            .build();
}

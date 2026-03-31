package com.xiyue.creator.api.ModGUIS.Menus.MenuConfigs;

import com.xiyue.creator.ModGUIS.Menus.StrainerFrameMenu;
import com.xiyue.creator.api.ModGUIS.Menus.MenuGuiDefinition;

public class MenuGUIConfigs {
    public static final MenuGuiDefinition<StrainerFrameMenu> StrainerFrameGUI = MenuGuiDefinition.<StrainerFrameMenu>builder()
            .texture("strainer_frame_gui")
            .size(176, 193)
            .menuFactory(StrainerFrameMenu::new)
            .build();

    public static final MenuGuiDefinition<StrainerFrameMenu> IronStrainerFrameGUI = MenuGuiDefinition.<StrainerFrameMenu>builder()
            .texture("iron_strainer_frame_gui")
            .size(247, 194)
            .menuFactory(StrainerFrameMenu::new)
            .build();
}

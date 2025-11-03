package com.xiyue.creator.ModGUIS.Screens.StrainerFrame;

import com.xiyue.creator.ModGUIS.Menus.IronStrainerFrameMenu;
import com.xiyue.creator.api.ModGUIS.Screens.StrainerFrameGUI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class IronStrainerFrameGUI extends StrainerFrameGUI<IronStrainerFrameMenu> {
    public IronStrainerFrameGUI(IronStrainerFrameMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title,194,247 , "iron_strainer_frame_gui");
    }

}

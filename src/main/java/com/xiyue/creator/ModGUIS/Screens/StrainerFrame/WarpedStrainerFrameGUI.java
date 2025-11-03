package com.xiyue.creator.ModGUIS.Screens.StrainerFrame;

import com.xiyue.creator.ModGUIS.Menus.WarpedStrainerFrameMenu;
import com.xiyue.creator.api.ModGUIS.Screens.StrainerFrameGUI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WarpedStrainerFrameGUI extends StrainerFrameGUI<WarpedStrainerFrameMenu> {
    public WarpedStrainerFrameGUI(WarpedStrainerFrameMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}

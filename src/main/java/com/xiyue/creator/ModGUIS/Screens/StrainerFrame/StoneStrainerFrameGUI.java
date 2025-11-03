package com.xiyue.creator.ModGUIS.Screens.StrainerFrame;

import com.xiyue.creator.ModGUIS.Menus.StoneStrainerFrameMenu;
import com.xiyue.creator.api.ModGUIS.Screens.StrainerFrameGUI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class StoneStrainerFrameGUI extends StrainerFrameGUI<StoneStrainerFrameMenu> {
    public StoneStrainerFrameGUI(StoneStrainerFrameMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}

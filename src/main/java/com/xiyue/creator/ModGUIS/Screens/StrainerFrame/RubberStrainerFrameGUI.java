package com.xiyue.creator.ModGUIS.Screens.StrainerFrame;

import com.xiyue.creator.ModGUIS.Menus.OakStrainerFrameMenu;
import com.xiyue.creator.ModGUIS.Menus.RubberStrainerFrameMenu;
import com.xiyue.creator.api.ModGUIS.Screens.StrainerFrameGUI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class RubberStrainerFrameGUI extends StrainerFrameGUI<RubberStrainerFrameMenu> {
    public RubberStrainerFrameGUI(RubberStrainerFrameMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}

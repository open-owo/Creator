package com.xiyue.creator.ModGUIS.Screens.StrainerFrame;

import com.xiyue.creator.ModGUIS.Menus.AcaciaStrainerFrameMenu;
import com.xiyue.creator.api.ModGUIS.Screens.StrainerFrameGUI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AcaciaStrainerFrameGUI extends StrainerFrameGUI<AcaciaStrainerFrameMenu> {
    public AcaciaStrainerFrameGUI(AcaciaStrainerFrameMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}

package com.xiyue.creator.ModGUIS.Screens.StrainerFrame;

import com.xiyue.creator.ModGUIS.Menus.MangroveStrainerFrameMenu;
import com.xiyue.creator.api.ModGUIS.Screens.StrainerFrameGUI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MangroveStrainerFrameGUI extends StrainerFrameGUI<MangroveStrainerFrameMenu> {
    public MangroveStrainerFrameGUI(MangroveStrainerFrameMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}

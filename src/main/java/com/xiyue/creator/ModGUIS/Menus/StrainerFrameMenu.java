package com.xiyue.creator.ModGUIS.Menus;

import com.xiyue.creator.api.ModGUIS.Menus.MachineMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public class StrainerFrameMenu extends MachineMenu {

    public StrainerFrameMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, BlockEntity entity, ContainerLevelAccess access) {
        super(menuType, containerId, playerInventory, entity, access);
    }
}
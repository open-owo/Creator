package com.xiyue.creator.ModGUIS.Menus;

import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.ModGUIS.ModMenus;
import com.xiyue.creator.api.ModGUIS.Menus.StrainerFrameMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;

public class IronStrainerFrameMenu extends StrainerFrameMenu {
    private final ContainerLevelAccess access;

    public IronStrainerFrameMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()), ContainerLevelAccess.NULL);
    }

    public IronStrainerFrameMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerLevelAccess access) {
        super(containerId,inventory,entity, ModMenus.IRON_STRAINER_FRAME_MENU.get(), 44, 111, 8, 45, 3, 13, 116, 18);
        this.access = access;
    }

    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(access, player, ModBlockGroup.IRON_STRAINER_FRAME.get());
    }
}

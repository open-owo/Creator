package com.xiyue.creator.api.ModGUIS.Menus;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMenu extends AbstractContainerMenu {
    protected BaseMenu(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }
}

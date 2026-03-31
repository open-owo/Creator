package com.xiyue.creator.api.ModGUIS.Menus;

import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.MenuType;

public interface IBaseMenuProvider extends MenuProvider {
    MenuConfig getMenuConfig();
    default MenuType<?> getMenuType() { return null; }
}

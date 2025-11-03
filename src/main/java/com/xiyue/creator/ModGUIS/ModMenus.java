package com.xiyue.creator.ModGUIS;

import com.xiyue.creator.Creator;
import com.xiyue.creator.ModGUIS.Menus.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenus {
    //menu注册器
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(Registries.MENU, Creator.MODID);

    //menu
    public static final Supplier<MenuType<OakStrainerFrameMenu>> OAK_STRAINER_FRAME_MENU = REGISTER.register("oak_strainer_frame_menu", () -> IMenuTypeExtension.create(OakStrainerFrameMenu::new));
    public static final Supplier<MenuType<AcaciaStrainerFrameMenu>> ACACIA_STRAINER_FRAME_MENU = REGISTER.register("acacia_strainer_frame_menu", () -> IMenuTypeExtension.create(AcaciaStrainerFrameMenu::new));
    public static final Supplier<MenuType<BirchStrainerFrameMenu>> BIRCH_STRAINER_FRAME_MENU = REGISTER.register("birch_strainer_frame_menu", () -> IMenuTypeExtension.create(BirchStrainerFrameMenu::new));
    public static final Supplier<MenuType<CherryStrainerFrameMenu>> CHERRY_STRAINER_FRAME_MENU = REGISTER.register("cherry_strainer_frame_menu", () -> IMenuTypeExtension.create(CherryStrainerFrameMenu::new));
    public static final Supplier<MenuType<DarkOakStrainerFrameMenu>> DARK_OAK_STRAINER_FRAME_MENU = REGISTER.register("dark_oak_strainer_frame_menu", () -> IMenuTypeExtension.create(DarkOakStrainerFrameMenu::new));
    public static final Supplier<MenuType<JungleStrainerFrameMenu>> JUNGLE_STRAINER_FRAME_MENU = REGISTER.register("jungle_strainer_frame_menu", () -> IMenuTypeExtension.create(JungleStrainerFrameMenu::new));
    public static final Supplier<MenuType<MangroveStrainerFrameMenu>> MANGROVE_STRAINER_FRAME_MENU = REGISTER.register("mangrove_strainer_frame_menu", () -> IMenuTypeExtension.create(MangroveStrainerFrameMenu::new));
    public static final Supplier<MenuType<SpruceStrainerFrameMenu>> SPRUCE_STRAINER_FRAME_MENU = REGISTER.register("spruce_strainer_frame_menu", () -> IMenuTypeExtension.create(SpruceStrainerFrameMenu::new));
    public static final Supplier<MenuType<CrimsonStrainerFrameMenu>> CRIMSON_STRAINER_FRAME_MENU = REGISTER.register("crimson_strainer_frame_menu", () -> IMenuTypeExtension.create(CrimsonStrainerFrameMenu::new));
    public static final Supplier<MenuType<WarpedStrainerFrameMenu>> WARPED_STRAINER_FRAME_MENU = REGISTER.register("warped_strainer_frame_menu", () -> IMenuTypeExtension.create(WarpedStrainerFrameMenu::new));
    public static final Supplier<MenuType<RubberStrainerFrameMenu>> RUBBER_STRAINER_FRAME_MENU = REGISTER.register("rubber_strainer_frame_menu", () -> IMenuTypeExtension.create(RubberStrainerFrameMenu::new));
    public static final Supplier<MenuType<IronStrainerFrameMenu>> IRON_STRAINER_FRAME_MENU = REGISTER.register("iron_strainer_frame_menu", () -> IMenuTypeExtension.create(IronStrainerFrameMenu::new));
    public static final Supplier<MenuType<StoneStrainerFrameMenu>> STONE_STRAINER_FRAME_MENU = REGISTER.register("stone_strainer_frame_menu", () -> IMenuTypeExtension.create(StoneStrainerFrameMenu::new));
}

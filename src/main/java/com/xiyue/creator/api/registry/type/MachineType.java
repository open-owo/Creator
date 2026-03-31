package com.xiyue.creator.api.registry.type;

import com.xiyue.creator.api.BlockEntities.Machines.MachineBlockEntity;
import com.xiyue.creator.api.BlockEntities.Machines.MachineSpec;
import com.xiyue.creator.api.Blocks.Macines.MachineBlock;
import com.xiyue.creator.api.ModGUIS.Menus.MachineMenu;
import com.xiyue.creator.api.ModGUIS.Menus.MenuGuiDefinition;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class MachineType<B extends MachineBlock, E extends MachineBlockEntity, M extends MachineMenu> {
    private final MachineSpec spec;
    private final Supplier<BlockEntityType<E>> blockEntityFactory;
    private final Map<String, Supplier<B>> blockSuppliers;
    private final Supplier<MenuType<M>> menuType;
    private final MenuGuiDefinition<M> guiDef;
    private final Map<String, DeferredItem<BlockItem>> item;

    public MachineType(MachineSpec spec, Supplier<BlockEntityType<E>> blockEntityFactory, Map<String, Supplier<B>> blockSuppliers,Map<String, DeferredItem<BlockItem>>  item, Supplier<MenuType<M>> menuType, MenuGuiDefinition<M> guiDef) {
        this.spec = spec;
        this.blockEntityFactory = blockEntityFactory;
        this.blockSuppliers = blockSuppliers;
        this.menuType = menuType;
        this.guiDef = guiDef;
        this.item =item;
    }

    public MachineSpec getSpec() {
        return spec;
    }
    public Map<String, DeferredItem<BlockItem>> getItems() {
        return item;
    }

    public DeferredItem<BlockItem> getItems(String name) {
        return item.get(name);
    }

    public Supplier<B> getBlockSupplier(String name) {
        return blockSuppliers.get(name);
    }

    public Set<Supplier<? extends B>> getBlockSuppliers() {
        return new HashSet<>(blockSuppliers.values());
    }

    public Supplier<BlockEntityType<E>> getBlockEntityFactory() {
        return blockEntityFactory;
    }

    public Supplier<MenuType<M>> getMenuType() {
        return menuType;
    }

    public MenuGuiDefinition<M> getGuiDef() {
        return guiDef;
    }
}
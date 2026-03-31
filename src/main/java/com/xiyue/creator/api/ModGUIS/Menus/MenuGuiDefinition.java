package com.xiyue.creator.api.ModGUIS.Menus;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public record MenuGuiDefinition<M extends MachineMenu>(String textureName, int imageWidth, int imageHeight, IMenuFactory<M> menuFactory) {
    public static <M extends MachineMenu> Builder<M> builder() {
        return new Builder<>();
    }

    public static class Builder<M extends MachineMenu> {
        private String textureName;
        private int imageWidth = 176, imageHeight = 166;
        private IMenuFactory<M> menuFactory;

        public Builder<M> texture(String name) {
            this.textureName = name;
            return this;
        }

        public Builder<M> size(int width, int height) {
            this.imageWidth = width;
            this.imageHeight = height;
            return this;
        }

        public Builder<M> menuFactory(IMenuFactory<M> factory) {
            this.menuFactory = factory;
            return this;
        }

        public MenuGuiDefinition<M> build() {
            return new MenuGuiDefinition<>(textureName, imageWidth, imageHeight, menuFactory);
        }
    }

    @FunctionalInterface
    public interface IMenuFactory<M extends MachineMenu> {
        M create(MenuType<M> menuType, int containerId, Inventory playerInv, BlockEntity entity, ContainerLevelAccess access);
    }
}
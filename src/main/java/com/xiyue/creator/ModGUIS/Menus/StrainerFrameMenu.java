package com.xiyue.creator.ModGUIS.Menus;

import com.xiyue.creator.ModItems.FunctionItems.Meshes;
import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import com.xiyue.creator.api.ModGUIS.Menus.BaseMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public abstract class StrainerFrameMenu extends BaseMenu {
    private final ContainerLevelAccess access;
    private final int MeshSlot = 0;
    private final int FirstOutSlot = 1;
    private int OutSlotSize;
    private int FirstInventorySlot;
    private final int InventorySlotSize = 36;
    private StrainerFrameEntity entity;

    protected StrainerFrameMenu(int containerId, Inventory inventory, BlockEntity entity, MenuType<?> menuType, ContainerLevelAccess access) {
        super(menuType, containerId);
        StrainerFrameEntity strainerFrame = (StrainerFrameEntity)entity;
        this.entity = strainerFrame;
        ItemStackHandler itemHandler = strainerFrame.getItemHandler();
        this.addSlot(new SlotItemHandler(itemHandler, 0, 80, 18));
        this.layoutSlots(itemHandler, 8, 45, 3, 9);
        this.layoutPlayerInventorySlots(inventory, 8, 111);
        this.access = access;
    }

    protected StrainerFrameMenu(int containerId, Inventory inventory, BlockEntity entity, MenuType<?> menuType, ContainerLevelAccess access,int InventorySlotStratX, int InventorySlotStratY, int SlotStratX, int SlotStratY, int row, int column, int meshX, int meshY) {
        super(menuType, containerId);
        StrainerFrameEntity strainerFrame = (StrainerFrameEntity)entity;
        ItemStackHandler itemHandler = strainerFrame.getItemHandler();
        this.addSlot(new SlotItemHandler(itemHandler, 0, meshX, meshY));
        this.layoutSlots(itemHandler, SlotStratX, SlotStratY, row, column);
        this.layoutPlayerInventorySlots(inventory, InventorySlotStratX, InventorySlotStratY);
        this.access = access;
    }
    //容器
    private void layoutSlots(ItemStackHandler itemHandler, int stratX, int stratY, int row, int column) {
        int n = 1;
        for(int i = 0; i < row; ++i) {
            for(int j = 0; j < column; ++j) {
                this.addSlot(new SlotItemHandler(itemHandler, n, stratX + j * 18, stratY + i * 18));
                ++n;
            }
        }
        this.OutSlotSize = n - 1;
    }
    //背包和快捷栏
    private void layoutPlayerInventorySlots(Inventory inventory, int InventorySlotStratX, int InventorySlotStratY) {
        int i;
        FirstInventorySlot = OutSlotSize + 1;
        int QuickSlotStratY = InventorySlotStratY + 58;
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, InventorySlotStratX + j * 18, InventorySlotStratY + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, InventorySlotStratX + i * 18, QuickSlotStratY));
        }

    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot.hasItem()) {
            ItemStack rawStack = slot.getItem();
            quickMovedStack = rawStack.copy();
            if (slotIndex == 0) {
                if (!this.moveItemStackTo(rawStack, FirstInventorySlot, FirstInventorySlot + InventorySlotSize, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex >= FirstOutSlot && slotIndex < FirstOutSlot + OutSlotSize) {
                if (!this.moveItemStackTo(rawStack, FirstInventorySlot, FirstInventorySlot + InventorySlotSize, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex >= FirstInventorySlot && slotIndex < FirstInventorySlot + InventorySlotSize) {
                if (rawStack.getItem() instanceof Meshes) {
                    // 首先尝试将滤网放入滤网专用槽
                    if (this.slots.getFirst().getItem().isEmpty()) {
                        // 滤网槽为空，尝试放入
                        if (!this.moveItemStackTo(rawStack, MeshSlot, MeshSlot + 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        // 滤网槽不为空，尝试放入工作槽
                        if (!this.moveItemStackTo(rawStack, FirstOutSlot, FirstOutSlot + OutSlotSize, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                } else {
                    // 不是滤网物品，直接尝试放入工作槽
                    if (!this.moveItemStackTo(rawStack, FirstOutSlot, FirstOutSlot + OutSlotSize, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (rawStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (rawStack.getCount() == quickMovedStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, rawStack);
        }
        return quickMovedStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return AbstractContainerMenu.stillValid(access, player, this.entity.getBlockState().getBlock());
    }
}

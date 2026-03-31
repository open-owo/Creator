package com.xiyue.creator.api.ModGUIS.Menus;

import com.xiyue.creator.api.BlockEntities.Machines.MachineBlockEntity;
import com.xiyue.creator.api.capability.SlotType;
import com.xiyue.creator.api.capability.item.ConfigurableItemHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MachineMenu extends BaseMenu{
    private int FirstInventorySlot = 0;
    private int FirstContainerSlot = 0;
    private int EndInventorySlot = 0;
    private int EndContainerSlot = 0;
    private final ContainerLevelAccess access;
    private MachineBlockEntity entity;
    private ConfigurableItemHandler playerHandler;
    private ConfigurableItemHandler AutomationHandler;

    public MachineMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, BlockEntity entity, ContainerLevelAccess access) {
        super(menuType, containerId);
        if (entity instanceof MachineBlockEntity blockEntity){
            this.entity = blockEntity;
            this.playerHandler = blockEntity.getPlayerHandler();
            this.AutomationHandler = blockEntity.getAutomationHandler();
        }
        if (entity instanceof IBaseMenuProvider menuProvider){
            var menuConfig = menuProvider.getMenuConfig();
            this.FirstContainerSlot = this.slots.size();
            for (var entry : menuConfig.getSlotConfigs().entrySet()){
                var config = entry.getValue();
                layoutSlots(config.getSlotStartX(), config.getSlotStartY(), config.getRow(), config.getColumn(), entry.getKey());
            }
            this.EndContainerSlot = this.slots.size();
            layoutPlayerInventorySlots(playerInventory, menuConfig.getInvStarX(), menuConfig.getInvStarY());
        }
        this.access = access;
    }

    private void layoutSlots(int startX, int startY, int row, int column, SlotType slotType) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < AutomationHandler.getSlots(); i++){
            if (AutomationHandler.getRules().getSlotType(i) == slotType){
                this.addSlot(new SlotItemHandler(playerHandler, i, startX + x * 18, startY + y * 18));
                x++;
                if (x == column){
                    x = 0;
                    y++;
                    if (y >= row) { // 使用 >= 防止超出行数
                        return;
                    }
                }
            }
        }
    }

    //背包和快捷栏
    private void layoutPlayerInventorySlots(Inventory inventory, int StartX, int StartY) {
        FirstInventorySlot = this.slots.size();
        int QuickSlotStratY = StartY + 58;
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, StartX + j * 18, StartY + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, StartX + i * 18, QuickSlotStratY));
        }
        EndInventorySlot = this.slots.size();
    }


    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        if (index >= FirstContainerSlot && index < EndContainerSlot) {
            if (!moveItemStackTo(stack, FirstInventorySlot, EndInventorySlot, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= FirstInventorySlot && index < EndInventorySlot) {
            if (!moveItemStackTo(stack, FirstContainerSlot, EndContainerSlot, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return original;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return AbstractContainerMenu.stillValid(access, player, this.entity.getBlockState().getBlock());
    }
}

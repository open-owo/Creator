package com.xiyue.creator.ModBlockEntities.MyModBlockEntities.StrainerFrame;

import com.xiyue.creator.ModBlockEntities.ModBlockEntities;
import com.xiyue.creator.ModGUIS.Menus.SpruceStrainerFrameMenu;
import com.xiyue.creator.api.BlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SpruceStrainerFrameEntity extends StrainerFrameEntity {
    public SpruceStrainerFrameEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPRUCE_STRAINER_FRAME.get(), pos, state, 28);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int ContainerId, Inventory PlayerInventory, Player Player) {
        return new SpruceStrainerFrameMenu(ContainerId, PlayerInventory, this, ContainerLevelAccess.create(Player.level(), this.getBlockPos()));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.creator.spruce_strainer_frame");
    }
}

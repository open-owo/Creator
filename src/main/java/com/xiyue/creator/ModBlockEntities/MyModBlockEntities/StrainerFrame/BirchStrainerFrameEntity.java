package com.xiyue.creator.ModBlockEntities.MyModBlockEntities.StrainerFrame;

import com.xiyue.creator.ModBlockEntities.ModBlockEntities;
import com.xiyue.creator.ModGUIS.Menus.BirchStrainerFrameMenu;
import com.xiyue.creator.api.BlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BirchStrainerFrameEntity extends StrainerFrameEntity {
    public BirchStrainerFrameEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIRCH_STRAINER_FRAME.get(), pos, state, 28);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.creator.birch_strainer_frame");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int ContainerId, Inventory inventory, Player player) {
        return new BirchStrainerFrameMenu(ContainerId, inventory, this, ContainerLevelAccess.create(player.level(), this.getBlockPos()));
    }
}

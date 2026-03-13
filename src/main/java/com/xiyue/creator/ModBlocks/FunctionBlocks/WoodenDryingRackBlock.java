package com.xiyue.creator.ModBlocks.FunctionBlocks;

import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.WoodenDryingRackEntity;
import com.xiyue.creator.api.Blocks.DryingRackBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WoodenDryingRackBlock extends DryingRackBlock {
    public WoodenDryingRackBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WoodenDryingRackEntity(blockPos, blockState);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : (BlockEntityTicker<T>) (BlockEntityTicker<WoodenDryingRackEntity>) WoodenDryingRackEntity::serverTick;
    }
}

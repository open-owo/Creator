package com.xiyue.creator.ModBlocks.FunctionBlocks.StrainerFrame;

import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.StrainerFrame.StoneStrainerFrameEntity;
import com.xiyue.creator.api.Blocks.StrainerFrameBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class StoneStrainerFrameBlock extends StrainerFrameBlock {
    public StoneStrainerFrameBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider ? (MenuProvider) blockEntity : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new StoneStrainerFrameEntity(blockPos, blockState);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : (BlockEntityTicker<T>) (BlockEntityTicker<StoneStrainerFrameEntity>) StoneStrainerFrameEntity::serverTick;

    }
    @Override
    public boolean isFlammable(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction direction) {
        return false;
    }
}

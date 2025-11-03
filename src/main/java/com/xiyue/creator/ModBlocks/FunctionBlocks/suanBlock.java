package com.xiyue.creator.ModBlocks.FunctionBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;

public class suanBlock extends Block{
    //方块状态
    public static final IntegerProperty SUAN = IntegerProperty.create("suan", 1, 3);

    //构造器
    public suanBlock(Properties properties) {
        super(Properties.of().strength(4f,10f));
        this.registerDefaultState(this.defaultBlockState().setValue(SUAN,1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SUAN);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int i = state.getValue(SUAN);
        if (!level.isClientSide && InteractionHand.MAIN_HAND == player.getUsedItemHand()) {
            if (state.getValue(SUAN) < 3) {
                level.setBlock(pos, state.setValue(SUAN, ++i), 3);
            }else {
                level.setBlock(pos, state.setValue(SUAN, 1), 3);
            }
        }
        return InteractionResult.SUCCESS;
    }
}

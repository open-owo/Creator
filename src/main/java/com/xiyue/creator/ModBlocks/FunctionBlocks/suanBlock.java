package com.xiyue.creator.ModBlocks.FunctionBlocks;

import com.xiyue.creator.ModGUIS.Screens.BlueScreenScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

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
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
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

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (stack.getItem() == Items.STICK && level.isClientSide()) {
            Minecraft mc = Minecraft.getInstance();
            boolean wasFullscreen = mc.getWindow().isFullscreen();
            if (!wasFullscreen) {
                mc.getWindow().toggleFullScreen();
            }
            mc.setScreen(new BlueScreenScreen(wasFullscreen));
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}

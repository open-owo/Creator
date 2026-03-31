package com.xiyue.creator.api.Blocks.Macines;

import com.xiyue.creator.MultiblockMapData;
import com.xiyue.creator.api.BlockEntities.Machines.MultiblockEntity;
import com.xiyue.creator.api.Blocks.BaseBlock;
import com.xiyue.creator.api.util.ShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MultiComponentBlock extends BaseBlock {


    public MultiComponentBlock(Properties properties, ShapeHelper shapeHelper, int Flammability, int FireSpreadSpeed) {
        super(properties, shapeHelper, Flammability, FireSpreadSpeed);
    }

    public MultiComponentBlock(Properties properties, ShapeHelper shapeHelper) {
        super(properties, shapeHelper);
    }

    public MultiComponentBlock(Properties properties) {
        super(properties);
    }

    protected void NotifyNearbyBlock(ServerLevel level, BlockPos pos){
        MultiblockMapData data = MultiblockMapData.get(level);
    }

    @Override
    protected void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (level instanceof ServerLevel serverLevel){
            NotifyNearbyBlock(serverLevel, pos);
        }
    }
}

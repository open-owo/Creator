package com.xiyue.creator.ModBlockEntities.MyModBlockEntities;

import com.xiyue.creator.ModBlockEntities.ModBlockEntities;
import com.xiyue.creator.api.BlockEntities.DryingRackEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class WoodenDryingRackEntity extends DryingRackEntity {

    public WoodenDryingRackEntity(BlockPos pos, BlockState blockState){
        super(ModBlockEntities.DRYING_RACK.get(), pos, blockState, 4);
    }

}

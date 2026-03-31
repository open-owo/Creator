package com.xiyue.creator.api.BlockEntities.Machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MultiblockEntity extends MachineBlockEntity{
    public enum multiblockType{
        masterBlock,
        slaveBlock
    }

    private final multiblockType Type;

    public MultiblockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, MachineSpec spec, multiblockType multiblockType) {
        super(type, pos, state, spec);
        this.Type = multiblockType;
    }

    @Override
    protected boolean canWork(Level level, BlockPos pos, BlockState state) {
        return this.Type == multiblockType.masterBlock;
    }
}

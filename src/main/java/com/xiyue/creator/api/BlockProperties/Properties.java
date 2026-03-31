package com.xiyue.creator.api.BlockProperties;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.xiyue.creator.api.Blocks.Property.BlockStateProperties.LAVALOGGED;

public class Properties {
    public static final BlockBehaviour.Properties STRAINER_FRAME_PROPERTIES = BlockBehaviour.Properties.of().noOcclusion().lightLevel(state -> state.getValue(LAVALOGGED) ? 15 : 0).strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava();
    public static final BlockBehaviour.Properties DRYING_RACK_PROPERTIES = BlockBehaviour.Properties.of().noOcclusion().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava();

}

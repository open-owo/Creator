package com.xiyue.creator.api.Blocks.Property;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class BlockStateProperties {
    public static BooleanProperty WATERLOGGED = net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;
    public static DirectionProperty DIRECTION = net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;
    public static BooleanProperty LAVALOGGED = BooleanProperty.create("lavalogged");
    public static BooleanProperty HAS_MESH = BooleanProperty.create("has_mesh");
    public static BooleanProperty HAVE_SUN = BooleanProperty.create("have_sun");
}

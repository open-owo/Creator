package com.xiyue.creator.api.BlockShape;

import com.xiyue.creator.api.util.ShapeHelper;

import static com.xiyue.creator.api.BlockShape.BlockShapes.*;
import static com.xiyue.creator.api.Blocks.Property.BlockStateProperties.*;
import static com.xiyue.creator.api.util.ShapeHelper.Context.*;

public class BlockShapeConfigs {
    public static final ShapeHelper STRAINER_FRAME_SHAPE = ShapeHelper.builder().forContext(CollisionShape)
            .setShapeRule((state, BlockGetter, BlockPos, CollisionContext) -> {
                if (state.getValue(HAS_MESH)){
                    return STRAINER_FRAME_SHAPE_HAS_MESH;
                }
                return STRAINER_FRAME_SHAPE_NO_MESH;
            }).endContext().build();

    public static final ShapeHelper DRYING_RACK_SHAPE = ShapeHelper.builder().forContext(CollisionShape)
            .setShapeRule((state, BlockGetter, BlockPos, CollisionContext) -> switch (state.getValue(DIRECTION)) {
                case SOUTH -> DRYING_RACK_SOUTH_SHAPE;
                case EAST -> DRYING_RACK_EAST_SHAPE_c;
                case WEST -> DRYING_RACK_WEST_SHAPE_c;
                default -> DRYING_RACK_NORTH_SHAPE_c;
            }).endContext()
            .forContext(Shape).setShapeRule((state, BlockGetter, BlockPos, CollisionContext) -> switch (state.getValue(DIRECTION)) {
                case SOUTH -> DRYING_RACK_SOUTH_SHAPE_c;
                case EAST -> DRYING_RACK_EAST_SHAPE;
                case WEST -> DRYING_RACK_WEST_SHAPE;
                default -> DRYING_RACK_NORTH_SHAPE;
            }).endContext()
            .build();
}

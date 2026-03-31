package com.xiyue.creator.api.BlockShape;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockShapes {
    public static final VoxelShape STRAINER_FRAME_SHAPE_NO_MESH;
    public static final VoxelShape STRAINER_FRAME_SHAPE_HAS_MESH;

    public static final VoxelShape DRYING_RACK_NORTH_SHAPE;
    public static final VoxelShape DRYING_RACK_EAST_SHAPE;
    public static final VoxelShape DRYING_RACK_WEST_SHAPE;
    public static final VoxelShape DRYING_RACK_SOUTH_SHAPE;
    public static final VoxelShape DRYING_RACK_NORTH_SHAPE_c;
    public static final VoxelShape DRYING_RACK_EAST_SHAPE_c;
    public static final VoxelShape DRYING_RACK_WEST_SHAPE_c;
    public static final VoxelShape DRYING_RACK_SOUTH_SHAPE_c;


    static {
        VoxelShape north1 = Block.box(0, 4, 4, 16, 8, 8);
        VoxelShape north2 = Block.box(0, 8, 8, 16, 12, 12);
        VoxelShape north3 = Block.box(0, 12, 12, 16, 16, 16);
        VoxelShape north4 = Block.box(0, 0, 0, 16, 4, 4);
        VoxelShape north5 = Block.box(0, 0, 15, 1, 12, 16);
        VoxelShape north6 = Block.box(15, 0, 15, 16, 12, 16);
        DRYING_RACK_NORTH_SHAPE_c = Shapes.or(north1, north2, north3, north4, north5, north6);

        VoxelShape north1c = Block.box(0, 0, 0, 16, 4, 16);
        VoxelShape north2c = Block.box(0, 4, 4, 16, 8, 16);
        VoxelShape north3c = Block.box(0, 8, 8, 16, 12, 16);
        VoxelShape north4c = Block.box(0, 12, 12, 16, 16, 16);
        DRYING_RACK_NORTH_SHAPE = Shapes.or(north1c, north2c, north3c, north4c);

        VoxelShape east1 = Block.box(8, 4, 0, 12, 8, 16);
        VoxelShape east2 = Block.box(4, 8, 0, 8, 12, 16);
        VoxelShape east3 = Block.box(0, 12, 0, 4, 16, 16);
        VoxelShape east4 = Block.box(12, 0, 0, 16, 4, 16);
        VoxelShape east5 = Block.box(0, 0, 0, 1, 12, 1);
        VoxelShape east6 = Block.box(0, 0, 15, 1, 12, 16);
        DRYING_RACK_EAST_SHAPE_c = Shapes.or(east1, east2, east3, east4, east5, east6);

        VoxelShape east1c = Block.box(0, 0, 0, 16, 4, 16);
        VoxelShape east2c = Block.box(0, 4, 0, 12, 8, 16);
        VoxelShape east3c = Block.box(0, 8, 0, 8, 12, 16);
        VoxelShape east4c = Block.box(0, 12, 0, 4, 16, 16);
        DRYING_RACK_EAST_SHAPE = Shapes.or(east1c, east2c, east3c, east4c);

        VoxelShape west1 = Block.box(4, 4, 0, 8, 8, 16);
        VoxelShape west2 = Block.box(8, 8, 0, 12, 12, 16);
        VoxelShape west3 = Block.box(12, 12, 0, 16, 16, 16);
        VoxelShape west4 = Block.box(0, 0, 0, 4, 4, 16);
        VoxelShape west5 = Block.box(15, 0, 15, 16, 12, 16);
        VoxelShape west6 = Block.box(15, 0, 0, 16, 12, 1);
        DRYING_RACK_WEST_SHAPE_c = Shapes.or(west1, west2, west3, west4, west5, west6);

        VoxelShape west1c = Block.box(0, 0, 0, 16, 4, 16);
        VoxelShape west2c = Block.box(4, 4, 0, 16, 8, 16);
        VoxelShape west3c = Block.box(8, 8, 0, 16, 12, 16);
        VoxelShape west4c = Block.box(12, 12, 0, 16, 16, 16);
        DRYING_RACK_WEST_SHAPE = Shapes.or(west1c, west2c, west3c, west4c);

        VoxelShape south1 = Block.box(0, 0, 12, 16, 4, 16);
        VoxelShape south2 = Block.box(0, 4, 8, 16, 8, 12);
        VoxelShape south3 = Block.box(0, 8, 4, 16, 12, 8);
        VoxelShape south4 = Block.box(0, 12, 0, 16, 16, 4);
        VoxelShape south5 = Block.box(0, 0, 0, 1, 12, 1);
        VoxelShape south6 = Block.box(15, 0, 0, 16, 12, 1);
        DRYING_RACK_SOUTH_SHAPE_c = Shapes.or(south1, south2, south3, south4, south5, south6);

        VoxelShape south1c = Block.box(0, 0, 0, 16, 4, 16);
        VoxelShape south2c = Block.box(0, 4, 0, 16, 8, 12);
        VoxelShape south3c = Block.box(0, 8, 0, 16, 12, 8);
        VoxelShape south4c = Block.box(0, 12, 0, 16, 16, 4);
        DRYING_RACK_SOUTH_SHAPE = Shapes.or(south1c, south2c, south3c, south4c);
    }


    static {
        VoxelShape part1 = Block.box(0, 0, 2, 2, 2, 14);
        VoxelShape part2 = Block.box(14, 0, 2, 16, 2, 14);
        VoxelShape part3 = Block.box(14, 14, 2, 16, 16, 14);
        VoxelShape part4 = Block.box(0, 14, 2, 2, 16, 14);
        VoxelShape part5 = Block.box(0, 0, 14, 2, 2, 16);
        VoxelShape part6 = Block.box(14, 2, 0, 16, 14, 2);
        VoxelShape part7 = Block.box(14, 2, 14, 16, 14, 16);
        VoxelShape part8 = Block.box(0, 2, 14, 2, 14, 16);
        VoxelShape part9 = Block.box(14, 0, 0, 16, 2, 2);
        VoxelShape part10 = Block.box(14, 0, 14, 16, 2, 16);
        VoxelShape part11 = Block.box(0, 2, 0, 2, 14, 2);
        VoxelShape part12 = Block.box(0, 0, 0, 2, 2, 2);
        VoxelShape part13 = Block.box(0, 14, 0, 2, 16, 2);
        VoxelShape part14 = Block.box(0, 14, 14, 2, 16, 16);
        VoxelShape part15 = Block.box(14, 14, 14, 16, 16, 16);
        VoxelShape part16 = Block.box(2, 0, 0, 14, 2, 2);
        VoxelShape part17 = Block.box(2, 14, 0, 14, 16, 2);
        VoxelShape part18 = Block.box(2, 14, 14, 14, 16, 16);
        VoxelShape part19 = Block.box(2, 0, 14, 14, 2, 16);
        VoxelShape part20 = Block.box(14, 14, 0, 16, 16, 2);
        VoxelShape part21 = Block.box(2, 14, 2, 14, 16, 14);
        VoxelShape part22 = Block.box(2, 0, 2, 14, 2, 14);
        VoxelShape part23 = Block.box(0, 2, 2, 2, 14, 14);
        VoxelShape part24 = Block.box(14, 2, 2, 16, 14, 14);
        VoxelShape part25 = Block.box(2, 2, 0, 14, 14, 2);
        VoxelShape part26 = Block.box(2, 2, 14, 14, 14, 16);

        VoxelShape group = Shapes.or(part1, part2, part3, part4, part5, part6, part7, part8, part9, part10, part11, part12, part13, part14, part15, part16, part17, part18, part19, part20, part21, part22, part23, part24, part25, part26);
        STRAINER_FRAME_SHAPE_HAS_MESH = Shapes.or(group);

        double[][] elements = new double[][]{{0, 0, 14, 2, 2, 16}, {14, 0, 14, 16, 2, 16}, {14, 14, 14, 16, 16, 16}, {0, 14, 14, 2, 16, 16}, {14, 14, 0, 16, 16, 2}, {0, 0, 0, 2, 2, 2}, {14, 0, 0, 16, 2, 2}, {2, 0, 0, 14, 2, 2}, {2, 0, 14, 14, 2, 16}, {2, 14, 0, 14, 16, 2}, {2, 14, 14, 14, 16, 16}, {0, 2, 0, 2, 14, 2}, {0, 2, 14, 2, 14, 16}, {14, 2, 0, 16, 14, 2}, {14, 2, 14, 16, 14, 16}, {0, 14, 0, 2, 16, 2}, {0, 0, 2, 2, 2, 14}, {14, 0, 2, 16, 2, 14}, {14, 14, 2, 16, 16, 14}, {0, 14, 2, 2, 16, 14}};
        VoxelShape shape = Shapes.empty();
        for (double[] element : elements) {
            double minX = element[0] / 16.0;
            double minY = element[1] / 16.0;
            double minZ = element[2] / 16.0;
            double maxX = element[3] / 16.0;
            double maxY = element[4] / 16.0;
            double maxZ = element[5] / 16.0;
            VoxelShape part = Shapes.box(minX, minY, minZ, maxX, maxY, maxZ);shape = Shapes.or(shape, part);
        }
        STRAINER_FRAME_SHAPE_NO_MESH = shape;
    }


}

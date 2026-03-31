package com.xiyue.creator.api.Blocks;

import com.xiyue.creator.api.util.ShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BaseBlock extends Block {
    private final ShapeHelper shapeHelper;
    public BaseBlock(Properties properties, ShapeHelper shapeHelper,int Flammability, int FireSpreadSpeed) {
        super(properties);
        this.shapeHelper = shapeHelper;
        setFlammable(Flammability, FireSpreadSpeed);
    }

    public BaseBlock(Properties properties, ShapeHelper shapeHelper) {
        super(properties);
        this.shapeHelper = shapeHelper;
    }

    public BaseBlock(Properties properties) {
        super(properties);
        this.shapeHelper = null;
    }

    public void setFlammable(int Flammability, int FireSpreadSpeed){
        FireBlock fireblock = (FireBlock)Blocks.FIRE;
        fireblock.setFlammable(this, Flammability, FireSpreadSpeed);
    }
    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (shapeHelper != null && shapeHelper.forContext(ShapeHelper.Context.Shape) != null){
            return shapeHelper.forContext(ShapeHelper.Context.Shape).apply(state, level, pos, context);
        }
        return super.getShape(state, level, pos, context);
    }
    @Override
    protected @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (shapeHelper != null && shapeHelper.forContext(ShapeHelper.Context.CollisionShape) != null){
            return shapeHelper.forContext(ShapeHelper.Context.CollisionShape).apply(state, level, pos, context);
        }
        return super.getCollisionShape(state, level, pos, context);
    }
}

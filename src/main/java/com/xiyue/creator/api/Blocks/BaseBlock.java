package com.xiyue.creator.api.Blocks;

import com.xiyue.creator.api.util.ShapeHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class BaseBlock extends Block {

    public BaseBlock(Properties properties, ShapeHelper shapeHelper,int Flammability, int FireSpreadSpeed) {
        super(properties);
        setFlammable(Flammability, FireSpreadSpeed);
    }

    public BaseBlock(Properties properties, ShapeHelper shapeHelper) {
        super(properties);
    }

    public void setFlammable(int Flammability, int FireSpreadSpeed){
        FireBlock fireblock = (FireBlock)Blocks.FIRE;
        fireblock.setFlammable(this, Flammability, FireSpreadSpeed);
    }
}

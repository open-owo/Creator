package com.xiyue.creator.ModBlocks;

import com.xiyue.creator.BuilderSystem.BuilderSystem;
import com.xiyue.creator.Creator;
import com.xiyue.creator.ModBlocks.FunctionBlocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ModBlockGroup {
    //方块集合
    public static final ArrayList<DeferredBlock<? extends Block>> blocks = new ArrayList<>();

    //注册器
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Creator.MODID);
    public static final DeferredRegister.Blocks BUILDER_REGISTER = DeferredRegister.createBlocks(Creator.MODID);

    //功能方块
    public static final DeferredBlock<suanBlock> SUAN_BLOCK = register("suan", () -> new suanBlock(BlockBehaviour.Properties.of().strength(1.5f, 6.0f).sound(SoundType.STONE)));

    //public static final DeferredBlock<AdobeBlock> ADOBE_BLOCK = register("adobe_block",() -> new WoodenDryingRackBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY)));

    public static final DeferredBlock<Block> BUILDER = BUILDER_REGISTER.register("builder", BuilderSystem.BuilderBlock::new);
    //流体方块

    //注册
    public static <B extends Block> DeferredBlock<B> register(String name, Supplier<? extends B> sup) {
        DeferredBlock<B> block = BLOCKS.register(name, sup);
        blocks.add(block);
        return block;
    }
}

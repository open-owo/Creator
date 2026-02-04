package com.xiyue.creator.ModBlocks;

import com.xiyue.creator.BuilderSystem.BuilderSystem;
import com.xiyue.creator.Creator;
import com.xiyue.creator.ModBlocks.FunctionBlocks.*;
import com.xiyue.creator.ModBlocks.FunctionBlocks.StrainerFrame.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
    public static final DeferredBlock<OakStrainerFrameBlock> OAK_STRAINER_FRAME = register("oak_strainer_frame",() -> new OakStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<AcaciaStrainerFrameBlock> ACACIA_STRAINER_FRAME = register("acacia_strainer_frame",() -> new AcaciaStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<BirchStrainerFrameBlock> BIRCH_STRAINER_FRAME = register("birch_strainer_frame",() -> new BirchStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<CherryStrainerFrameBlock> CHERRY_STRAINER_FRAME = register("cherry_strainer_frame",() -> new CherryStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<DarkOakStrainerFrameBlock> DARK_OAK_STRAINER_FRAME = register("dark_oak_strainer_frame",() -> new DarkOakStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<JungleStrainerFrameBlock> JUNGLE_STRAINER_FRAME = register("jungle_strainer_frame",() -> new JungleStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<MangroveStrainerFrameBlock> MANGROVE_STRAINER_FRAME = register("mangrove_strainer_frame",() -> new MangroveStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<SpruceStrainerFrameBlock> SPRUCE_STRAINER_FRAME = register("spruce_strainer_frame",() -> new SpruceStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<CrimsonStrainerFrameBlock> CRIMSON_STRAINER_FRAME = register("crimson_strainer_frame",() -> new CrimsonStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<WarpedStrainerFrameBlock> WARPED_STRAINER_FRAME = register("warped_strainer_frame",() -> new WarpedStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<RubberStrainerFrameBlock> RUBBER_STRAINER_FRAME = register("rubber_strainer_frame",() -> new RubberStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<IronStrainerFrameBlock> IRON_STRAINER_FRAME = register("iron_strainer_frame",() -> new IronStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.7f, 2f).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final DeferredBlock<StoneStrainerFrameBlock> STONE_STRAINER_FRAME = register("stone_strainer_frame",() -> new StoneStrainerFrameBlock(BlockBehaviour.Properties.of().strength(1.3f, 5f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final DeferredBlock<WoodenDryingRackBlock> DRYING_RACK = register("drying_rack",() -> new WoodenDryingRackBlock(BlockBehaviour.Properties.of().strength(2f, 3f).sound(SoundType.WOOD)));
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

package com.xiyue.creator.ModBlockEntities;

import com.xiyue.creator.BuilderSystem.BuilderSystem;
import com.xiyue.creator.Creator;
import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.StrainerFrame.*;
import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.WoodenDryingRackEntity;
import com.xiyue.creator.ModBlocks.FunctionBlocks.StrainerFrame.OakStrainerFrameBlock;
import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.api.BlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import com.xiyue.creator.api.Blocks.StrainerFrameBlock;
import com.xiyue.creator.api.util.BlockRegistrationBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    //方块实体注册器
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Creator.MODID);

    //方块实体
    public static final Supplier<BlockEntityType<OakStrainerFrameEntity>> OAK_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "oak_strainer_frame",
            () -> BlockEntityType.Builder.of(OakStrainerFrameEntity::new, ModBlockGroup.OAK_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<AcaciaStrainerFrameEntity>> ACACIA_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "acacia_strainer_frame",
            () -> BlockEntityType.Builder.of(AcaciaStrainerFrameEntity::new, ModBlockGroup.ACACIA_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<BirchStrainerFrameEntity>> BIRCH_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "birch_strainer_frame",
            () -> BlockEntityType.Builder.of(BirchStrainerFrameEntity::new, ModBlockGroup.BIRCH_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<CherryStrainerFrameEntity>> CHERRY_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "cherry_strainer_frame",
            () -> BlockEntityType.Builder.of(CherryStrainerFrameEntity::new, ModBlockGroup.CHERRY_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<DarkOakStrainerFrameEntity>> DARK_OAK_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "dark_oak_strainer_frame",
            () -> BlockEntityType.Builder.of(DarkOakStrainerFrameEntity::new, ModBlockGroup.DARK_OAK_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<JungleStrainerFrameEntity>> JUNGLE_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "jungle_strainer_frame",
            () -> BlockEntityType.Builder.of(JungleStrainerFrameEntity::new, ModBlockGroup.JUNGLE_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<MangroveStrainerFrameEntity>> MANGROVE_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "mangrove_strainer_frame",
            () -> BlockEntityType.Builder.of(MangroveStrainerFrameEntity::new, ModBlockGroup.MANGROVE_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<SpruceStrainerFrameEntity>> SPRUCE_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "spruce_strainer_frame",
            () -> BlockEntityType.Builder.of(SpruceStrainerFrameEntity::new, ModBlockGroup.SPRUCE_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<CrimsonStrainerFrameEntity>> CRIMSON_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "crimson_strainer_frame",
            () -> BlockEntityType.Builder.of(CrimsonStrainerFrameEntity::new, ModBlockGroup.CRIMSON_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<WarpedStrainerFrameEntity>> WARPED_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "warped_strainer_frame",
            () -> BlockEntityType.Builder.of(WarpedStrainerFrameEntity::new, ModBlockGroup.WARPED_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<RubberStrainerFrameEntity>> RUBBER_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "rubber_strainer_frame",
            () -> BlockEntityType.Builder.of(RubberStrainerFrameEntity::new, ModBlockGroup.RUBBER_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<IronStrainerFrameEntity>> IRON_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "iron_strainer_frame",
            () -> BlockEntityType.Builder.of(IronStrainerFrameEntity::new, ModBlockGroup.IRON_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<StoneStrainerFrameEntity>> STONE_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "stone_strainer_frame",
            () -> BlockEntityType.Builder.of(StoneStrainerFrameEntity::new, ModBlockGroup.STONE_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<BuilderSystem.BuilderBlockEntity>> BUILDER_BLOCK = BLOCK_ENTITY_TYPES.register(
            "builder_block",
            () -> BlockEntityType.Builder.of(BuilderSystem.BuilderBlockEntity::new, ModBlockGroup.BUILDER.get()).build(null));

    public static final Supplier<BlockEntityType<WoodenDryingRackEntity>> DRYING_RACK = BLOCK_ENTITY_TYPES.register(
            "drying_rack",
            () -> BlockEntityType.Builder.of(WoodenDryingRackEntity::new, ModBlockGroup.DRYING_RACK.get()).build(null));

}

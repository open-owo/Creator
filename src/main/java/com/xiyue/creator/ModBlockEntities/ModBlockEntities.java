package com.xiyue.creator.ModBlockEntities;

import com.xiyue.creator.BuilderSystem.BuilderSystem;

import com.xiyue.creator.Creator;
import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.WoodenDryingRackEntity;
import com.xiyue.creator.ModBlocks.FunctionBlocks.StrainerFrame.StrainerFrameBlock;
import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.api.capability.HandlerConfig.MachineSpecs;
import com.xiyue.creator.api.registry.MyRegistry.MachineTypeDeferredRegister;
import com.xiyue.creator.api.registry.type.MachineType;
import com.xiyue.creator.api.util.ShapeHelper;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;
import java.util.function.Supplier;

import static com.xiyue.creator.Creator.BLOCK_ENTITIES;

public class ModBlockEntities {
    public static final MachineTypeDeferredRegister MACHINE_TYPES = new MachineTypeDeferredRegister(Creator.MODID);

    public static final DeferredHolder<MachineType, MachineType> STRAINER_FRAME = MACHINE_TYPES.registerMachine(MachineSpecs.STRAINER_FRAME_SPEC, "strainer_frame", null,
            StrainerFrameEntity::new,
            Map.of(
                    "oak_strainer_frame",() -> new StrainerFrameBlock(BlockBehaviour.Properties.of().noOcclusion().lightLevel(state -> state.getValue(LAVALOGGED) ? 15 : 0), new ShapeHelper()),
                    "birch_strainer_frame",() -> new StrainerFrameBlock(BlockBehaviour.Properties.of(), new ShapeHelper()),
                    "cherry_strainer_frame",() -> new StrainerFrameBlock(BlockBehaviour.Properties.of(), new ShapeHelper()),
                    "dark_oak_strainer_frame",() -> new StrainerFrameBlock(BlockBehaviour.Properties.of(), new ShapeHelper()),
                    "jungle_strainer_frame",() -> new StrainerFrameBlock(BlockBehaviour.Properties.of(), new ShapeHelper()),
                    "mangrove_strainer_frame",() -> new StrainerFrameBlock(BlockBehaviour.Properties.of(), new ShapeHelper()),
                    "spruce_strainer_frame",() -> new StrainerFrameBlock(BlockBehaviour.Properties.of(), new ShapeHelper()),
                    "crimson_strainer_frame",() -> new StrainerFrameBlock(BlockBehaviour.Properties.of(), new ShapeHelper()),
                    "warped_strainer_frame",() -> new StrainerFrameBlock(BlockBehaviour.Properties.of(), new ShapeHelper()),
                    "rubber_strainer_frame",() -> new StrainerFrameBlock(BlockBehaviour.Properties.of(), new ShapeHelper())
            )
    );

    public static final Supplier<BlockEntityType<IronStrainerFrameEntity>> IRON_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "iron_strainer_frame",
            () -> BlockEntityType.Builder.of(IronStrainerFrameEntity::new, ModBlockGroup.IRON_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<StoneStrainerFrameEntity>> STONE_STRAINER_FRAME = BLOCK_ENTITY_TYPES.register(
            "stone_strainer_frame",
            () -> BlockEntityType.Builder.of(StoneStrainerFrameEntity::new, ModBlockGroup.STONE_STRAINER_FRAME.get()).build(null));

    public static final Supplier<BlockEntityType<BuilderSystem.BuilderBlockEntity>> BUILDER_BLOCK = BLOCK_ENTITY_TYPES.register(
            "builder_block",
            () -> BlockEntityType.Builder.of(BuilderSystem.BuilderBlockEntity::new, ModBlockGroup.BUILDER.get()).build(null));

    public static final Supplier<BlockEntityType<WoodenDryingRackEntity>> DRYING_RACK = BLOCK_ENTITIES.register(
            "drying_rack",
            () -> BlockEntityType.Builder.of(WoodenDryingRackEntity::new, ModBlockGroup.DRYING_RACK.get()).build(null));

}

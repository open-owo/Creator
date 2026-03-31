package com.xiyue.creator.ModBlockEntities;

import com.xiyue.creator.BuilderSystem.BuilderSystem;

import com.xiyue.creator.Creator;
import com.xiyue.creator.ModBlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import com.xiyue.creator.ModBlocks.FunctionBlocks.StrainerFrameBlock;
import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.ModGUIS.Menus.StrainerFrameMenu;
import com.xiyue.creator.api.BlockShape.BlockShapeConfigs;
import com.xiyue.creator.ModBlocks.FunctionBlocks.DryingRackBlock;
import com.xiyue.creator.api.ModGUIS.Menus.BaseMenu;
import com.xiyue.creator.api.ModGUIS.Menus.MachineMenu;
import com.xiyue.creator.api.capability.HandlerConfig.MachineSpecs;
import com.xiyue.creator.api.registry.MyRegistry.MachineTypeDeferredRegister;
import com.xiyue.creator.api.registry.type.MachineType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;
import java.util.function.Supplier;

import static com.xiyue.creator.Creator.BLOCK_ENTITIES;
import static com.xiyue.creator.api.BlockProperties.Properties.*;
import static com.xiyue.creator.api.ModGUIS.Menus.MenuConfigs.MenuConfigs.*;
import static com.xiyue.creator.api.ModGUIS.Menus.MenuConfigs.MenuGUIConfigs.*;

public class ModBlockEntities {
    public static final MachineTypeDeferredRegister MACHINE_TYPES = new MachineTypeDeferredRegister(Creator.MODID);

    public static final DeferredHolder<MachineType, MachineType<StrainerFrameBlock,StrainerFrameEntity, StrainerFrameMenu>> STRAINER_FRAME = MACHINE_TYPES.registerMachine(
            MachineSpecs.STRAINER_FRAME_SPEC, "wooden_strainer_frame", null,StrainerFrameGUI,
            (entityHolder, menuTypeSupplier) -> ( pos,  state,  spec) -> new StrainerFrameEntity(entityHolder.get(), pos,  state,  spec, false, STRAINER_FRAME_MENU, menuTypeSupplier::get),
            (entityHolder) -> Map.ofEntries(
                    Map.entry("oak_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5)),
                    Map.entry("birch_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5)),
                    Map.entry("cherry_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5)),
                    Map.entry("dark_oak_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5)),
                    Map.entry("jungle_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5)),
                    Map.entry("mangrove_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5)),
                    Map.entry("spruce_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5)),
                    Map.entry("crimson_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5)),
                    Map.entry("warped_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5)),
                    Map.entry("rubber_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5)),
                    Map.entry("acacia_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES, BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get, 20, 5))
            )
    );

    public static final DeferredHolder<MachineType, MachineType<StrainerFrameBlock,StrainerFrameEntity, StrainerFrameMenu>> STONE_STRAINER_FRAME = MACHINE_TYPES.registerMachine(
            MachineSpecs.STRAINER_FRAME_SPEC, "stone_strainer_frame", null, StrainerFrameGUI,
            (entityHolder, menuTypeSupplier) -> ( pos,  state,  spec) -> new StrainerFrameEntity(entityHolder.get(), pos,  state,  spec, true, STRAINER_FRAME_MENU, menuTypeSupplier::get),
            (entityHolder) ->  Map.of(
                    "stone_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES.sound(SoundType.STONE).strength(1.7f, 5f), BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get)
            )
    );

    public static final DeferredHolder<MachineType, MachineType<StrainerFrameBlock,StrainerFrameEntity, StrainerFrameMenu>> IRON_STRAINER_FRAME = MACHINE_TYPES.registerMachine(
            MachineSpecs.IRON_STRAINER_FRAME_SPEC, "iron_strainer_frame", null, IronStrainerFrameGUI,
            (entityHolder, menuTypeSupplier) -> ( pos,  state,  spec) -> new StrainerFrameEntity(entityHolder.get(), pos,  state,  spec, true, IRON_STRAINER_FRAME_MENU, menuTypeSupplier::get),
            (entityHolder) ->   Map.of(
                    "iron_strainer_frame", () -> new StrainerFrameBlock(STRAINER_FRAME_PROPERTIES.sound(SoundType.METAL), BlockShapeConfigs.STRAINER_FRAME_SHAPE, entityHolder::get)
            )
    );

    public static final DeferredHolder<MachineType, MachineType<DryingRackBlock,DryingRackEntity, MachineMenu>> DRYING_RACK =  MACHINE_TYPES.registerMachine(
            MachineSpecs.DRYING_RACK_SPEC, "drying_rack", null,
            (entityHolder) -> ( pos,  state,  spec) -> new DryingRackEntity(entityHolder.get(), pos, state,  spec),
            (entityHolder) ->   Map.ofEntries(
                    Map.entry("drying_rack", () -> new DryingRackBlock(DRYING_RACK_PROPERTIES, BlockShapeConfigs.DRYING_RACK_SHAPE, entityHolder::get, 20, 5))
            )
    );

    public static final Supplier<BlockEntityType<BuilderSystem.BuilderBlockEntity>> BUILDER_BLOCK = BLOCK_ENTITIES.register(
            "builder_block",
            () -> BlockEntityType.Builder.of(BuilderSystem.BuilderBlockEntity::new, ModBlockGroup.BUILDER.get()).build(null));



}

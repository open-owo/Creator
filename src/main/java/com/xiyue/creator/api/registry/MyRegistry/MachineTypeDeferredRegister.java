package com.xiyue.creator.api.registry.MyRegistry;

import com.xiyue.creator.api.BlockEntities.Machines.MachineBlockEntity;
import com.xiyue.creator.api.BlockEntities.Machines.MachineSpec;
import com.xiyue.creator.api.Blocks.Macines.MachineBlock;
import com.xiyue.creator.api.ModGUIS.Menus.IBaseMenuProvider;
import com.xiyue.creator.api.ModGUIS.Menus.MachineMenu;
import com.xiyue.creator.api.ModGUIS.Menus.MenuGuiDefinition;
import com.xiyue.creator.api.registry.type.MachineType;
import com.xiyue.creator.api.util.TripleFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import com.mojang.datafixers.types.Type;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MachineTypeDeferredRegister {
    private final DeferredRegister<MachineType> machineTypeRegister;
    private final DeferredRegister.Blocks blocksRegister;
    private final DeferredRegister.Items itemsRegister;
    private final DeferredRegister<BlockEntityType<?>> blockEntitiesRegister;
    private final DeferredRegister<MenuType<?>> menuRegister;

    private static final Map<Block, Supplier<BlockEntityType<?>>> BLOCK_TO_ENTITY_TYPE = new ConcurrentHashMap<>();

    public static Supplier<BlockEntityType<?>> getBlockEntityType(Block block) {
        return BLOCK_TO_ENTITY_TYPE.get(block);
    }

    public DeferredRegister.Items getItemsRegister() {
        return itemsRegister;
    }

    public DeferredRegister<BlockEntityType<?>> getBlockEntitiesRegister() {
        return blockEntitiesRegister;
    }

    public MachineTypeDeferredRegister(String modid) {
        this.machineTypeRegister = DeferredRegister.create(ModRegistries.MACHINE_TYPE_KEY, modid);
        this.blocksRegister = DeferredRegister.createBlocks(modid);
        this.itemsRegister = DeferredRegister.createItems(modid);
        this.blockEntitiesRegister = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, modid);
        this.menuRegister = DeferredRegister.create(Registries.MENU, modid);
    }

    public <T extends MachineBlockEntity, U extends MachineBlock, M extends MachineMenu> DeferredHolder<MachineType, MachineType<U, T, M>> registerMachine
            (MachineSpec spec, String blockEntityName, Type<?> dataType, MenuGuiDefinition<M> menuGuiDef,
             BiFunction<Supplier<BlockEntityType<T>>, Supplier<MenuType<M>>, TripleFunction<BlockPos, BlockState, MachineSpec, T>> beFactoryFunction,
             Function<Supplier<BlockEntityType<T>>, Map<String ,Supplier<U>>> blockSupplierFactory
            ) {
        Supplier<MenuType<M>> menuTypeSupplier;
        if (menuGuiDef != null) {
            String menuName = blockEntityName + "_menu";
            final Supplier<MenuType<M>>[] menuHolder = new Supplier[1];
            menuHolder[0] = menuRegister.register(menuName, () ->
                    IMenuTypeExtension.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        if (be instanceof IBaseMenuProvider) {
                            return menuGuiDef.menuFactory().create(menuHolder[0].get(), windowId, inv, be, ContainerLevelAccess.create(inv.player.level(), pos)
                            );
                        }
                        return null;
                    })
            );
            menuTypeSupplier = menuHolder[0];
        } else {
            menuTypeSupplier = null;
        }

        List<DeferredBlock<U>> blockHolders = new ArrayList<>();

        @SuppressWarnings("unchecked")
        Supplier<BlockEntityType<T>>[] entityHolderRef = new Supplier[1];

        Supplier<BlockEntityType<T>> entityTypeSupplier = () -> {
            Block[] blocks = blockHolders.stream().map(DeferredBlock::get).toArray(Block[]::new);
            BiFunction<BlockPos, BlockState, T> beBiFunction = (pos, state) -> {
                TripleFunction<BlockPos, BlockState, MachineSpec, T> triple = beFactoryFunction.apply(entityHolderRef[0], menuTypeSupplier);
                return triple.apply(pos, state, spec);
            };
            BlockEntityType<T> type = BlockEntityType.Builder.of(beBiFunction::apply, blocks).build(dataType);
            for (DeferredBlock<U> holder : blockHolders) {
                BLOCK_TO_ENTITY_TYPE.put(holder.get(), () -> type);
            }
            return type;
        };

        entityHolderRef[0] = blockEntitiesRegister.register(blockEntityName, entityTypeSupplier);

        Map<String, Supplier<U>> blockSuppliers = blockSupplierFactory.apply(entityHolderRef[0]);

        Map<String, Supplier<U>> safeSupplierMap = new HashMap<>();
        Map<String, DeferredItem<BlockItem>> ItemMap = new HashMap<>();
        for (Map.Entry<String, Supplier<U>> entry : blockSuppliers.entrySet()) {
            String name = entry.getKey();
            DeferredBlock<U> blockHolder = blocksRegister.register(name, entry.getValue());
            blockHolders.add(blockHolder);
            ItemMap.put(name, itemsRegister.register(name, () -> new BlockItem(blockHolder.get(), new Item.Properties())));
            safeSupplierMap.put(name, blockHolder);
        }

        MachineType<U, T, M> typeSafeMachineType;
        if (menuTypeSupplier != null) {
            typeSafeMachineType = new MachineType<>(spec, entityHolderRef[0], safeSupplierMap, ItemMap, menuTypeSupplier, menuGuiDef);
        } else {
            typeSafeMachineType = new MachineType<>(spec, entityHolderRef[0], safeSupplierMap, ItemMap, null, null);
        }
        return machineTypeRegister.register(blockEntityName, () -> typeSafeMachineType);
    }

    public <T extends MachineBlockEntity, U extends MachineBlock, M extends MachineMenu> DeferredHolder<MachineType, MachineType<U, T, M>> registerMachine
            (MachineSpec spec, String blockEntityName, Type<?> dataType,
             Function<Supplier<BlockEntityType<T>>, TripleFunction<BlockPos, BlockState, MachineSpec, T>> beFactoryFunction,
             Function<Supplier<BlockEntityType<T>>, Map<String ,Supplier<U>>> blockSupplierFactory
            ) {

        List<DeferredBlock<U>> blockHolders = new ArrayList<>();

        @SuppressWarnings("unchecked")
        Supplier<BlockEntityType<T>>[] entityHolderRef = new Supplier[1];

        Supplier<BlockEntityType<T>> entityTypeSupplier = () -> {
            Block[] blocks = blockHolders.stream().map(DeferredBlock::get).toArray(Block[]::new);
            BiFunction<BlockPos, BlockState, T> beBiFunction = (pos, state) -> {
                TripleFunction<BlockPos, BlockState, MachineSpec, T> triple = beFactoryFunction.apply(entityHolderRef[0]);
                return triple.apply(pos, state, spec);
            };
            BlockEntityType<T> type = BlockEntityType.Builder.of(beBiFunction::apply, blocks).build(dataType);
            for (DeferredBlock<U> holder : blockHolders) {
                BLOCK_TO_ENTITY_TYPE.put(holder.get(), () -> type);
            }
            return type;
        };

        entityHolderRef[0] = blockEntitiesRegister.register(blockEntityName, entityTypeSupplier);

        Map<String, Supplier<U>> blockSuppliers = blockSupplierFactory.apply(entityHolderRef[0]);

        Map<String, Supplier<U>> safeSupplierMap = new HashMap<>();
        Map<String, DeferredItem<BlockItem>> ItemMap = new HashMap<>();
        for (Map.Entry<String, Supplier<U>> entry : blockSuppliers.entrySet()) {
            String name = entry.getKey();
            DeferredBlock<U> blockHolder = blocksRegister.register(name, entry.getValue());
            blockHolders.add(blockHolder);
            ItemMap.put(name, itemsRegister.register(name, () -> new BlockItem(blockHolder.get(), new Item.Properties())));
            safeSupplierMap.put(name, blockHolder);
        }

        MachineType<U, T, M> typeSafeMachineType;

        typeSafeMachineType = new MachineType<>(spec, entityHolderRef[0], safeSupplierMap, ItemMap, null, null);

        return machineTypeRegister.register(blockEntityName, () -> typeSafeMachineType);
    }

    public void register(IEventBus modEventBus) {
        machineTypeRegister.register(modEventBus);
        blocksRegister.register(modEventBus);
        itemsRegister.register(modEventBus);
        blockEntitiesRegister.register(modEventBus);
        menuRegister.register(modEventBus);
    }
}
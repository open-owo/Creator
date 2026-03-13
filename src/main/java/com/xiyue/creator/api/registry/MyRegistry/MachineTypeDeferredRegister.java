package com.xiyue.creator.api.registry.MyRegistry;

import com.xiyue.creator.api.BlockEntities.Machines.MachineBlockEntity;
import com.xiyue.creator.api.BlockEntities.Machines.MachineSpec;
import com.xiyue.creator.api.Blocks.Macines.MachineBlock;
import com.xiyue.creator.api.registry.type.MachineType;
import com.xiyue.creator.api.util.TripleFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import com.mojang.datafixers.types.Type;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class MachineTypeDeferredRegister {
    private final DeferredRegister<MachineType> machineTypeRegister;
    private final DeferredRegister.Blocks blocksRegister;
    private final DeferredRegister.Items itemsRegister;
    private final DeferredRegister<BlockEntityType<?>> blockEntitiesRegister;

    private static final Map<Block, Supplier<BlockEntityType<?>>> BLOCK_TO_ENTITY_TYPE = new ConcurrentHashMap<>();

    public static Supplier<BlockEntityType<?>> getBlockEntityType(Block block) {
        return BLOCK_TO_ENTITY_TYPE.get(block);
    }



    public MachineTypeDeferredRegister(String modid) {
        this.machineTypeRegister = DeferredRegister.create(ModRegistries.MACHINE_TYPE_KEY, modid);
        this.blocksRegister = DeferredRegister.createBlocks(modid);
        this.itemsRegister = DeferredRegister.createItems(modid);
        this.blockEntitiesRegister = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, modid);
    }

    public <T extends MachineBlockEntity, U extends MachineBlock> DeferredHolder<MachineType, MachineType>  registerMachine(MachineSpec spec, String BlockEntityName ,Type<?> dataType,TripleFunction<BlockPos, BlockState, MachineSpec, T> beFactory, Map<String,  Supplier<U>> blockSuppliers) {
        BiFunction<BlockPos, BlockState, T> beBiFunction = (pos, state) -> beFactory.apply(pos, state, spec);
        List<U> list = new ArrayList<>();
        List<DeferredBlock<U>> blockHolders = new ArrayList<>();
        Map<String,  Supplier<?>> map = new HashMap<>();
        for (var entrySet: blockSuppliers.entrySet()){
            String name = entrySet.getKey();
            DeferredBlock<U> blockHolder = blocksRegister.register(name, entrySet.getValue());
            blockHolders.add(blockHolder);
            itemsRegister.register(name, () -> new BlockItem(blockHolder.get(), new Item.Properties()));
            list.add(blockHolder.get());
            map.put(name, blockHolder);
        }
        int size = list.size();
        Block[] blocks = new Block[size];
        for (int i = 0; i < list.size(); i++) {
            blocks[i] = list.get(i);
        }

        Supplier<BlockEntityType<?>> entityTypeSupplier = blockEntitiesRegister.register(BlockEntityName, () -> BlockEntityType.Builder.of(beBiFunction::apply, blocks).build(dataType));

        for (DeferredBlock<U> holder : blockHolders) {
            BLOCK_TO_ENTITY_TYPE.put(holder.get(), entityTypeSupplier);
        }
        return machineTypeRegister.register(BlockEntityName, () -> new MachineType(spec, entityTypeSupplier, map));
    }

    public void register(IEventBus modEventBus) {
        machineTypeRegister.register(modEventBus);
        blocksRegister.register(modEventBus);
        itemsRegister.register(modEventBus);
        blockEntitiesRegister.register(modEventBus);
    }
}

package com.xiyue.creator.api.registry.type;


import com.xiyue.creator.api.BlockEntities.Machines.MachineBlockEntity;
import com.xiyue.creator.api.BlockEntities.Machines.MachineSpec;
import com.xiyue.creator.api.Blocks.Macines.MachineBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class MachineType {
    private final MachineSpec spec;
    private final Supplier<BlockEntityType<?>> blockEntityFactory;
    private final Map<String,  Supplier<?>> blockSuppliers;

    public MachineType(MachineSpec spec, Supplier<BlockEntityType<?>> blockEntityFactory, Map<String,  Supplier<?>> blockSuppliers) {
        this.spec = spec;
        this.blockEntityFactory = blockEntityFactory;
        this.blockSuppliers = blockSuppliers;
    }

    public MachineSpec getSpec() {
        return spec;
    }

    public Supplier<?> getBlockSupplier(String name) {
        return this.blockSuppliers.get(name);
    }

    public Set<Supplier<?>> getBlockSuppliers() {
        return new HashSet<>(blockSuppliers.values());
    }

    public Supplier<BlockEntityType<?>> getBlockEntityFactory() {
        return blockEntityFactory;
    }
}

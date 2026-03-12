package com.xiyue.creator.api.util;

import com.mojang.datafixers.types.Type;
import com.xiyue.creator.Creator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

public class BlockRegistrationBuilder<T extends Block> {
    private final String name;
    private final Supplier<T> blockSupplier;
    private Supplier<BlockItem> blockItemSupplier;
    private Supplier<BlockEntityType<?>> blockEntitySupplier;

    public BlockRegistrationBuilder(String name, Supplier<T> blockSupplier) {
        this.name = name;
        this.blockSupplier = blockSupplier;
    }

    public BlockRegistrationBuilder<T> withBlockItem() {
        this.blockItemSupplier = () -> new BlockItem(blockSupplier.get(), new Item.Properties());
        return this;
    }

    public BlockRegistrationBuilder<T> withBlockEntity(Supplier<BlockEntityType.BlockEntitySupplier<?>> beSupplier, Type<?> dataType) {
        this.blockEntitySupplier = () -> BlockEntityType.Builder.of(beSupplier.get(), blockSupplier.get()).build(dataType);
        return this;
    }

    public void register() {
        DeferredBlock<T> block = Creator.BLOCKS.registerBlock(name, p -> blockSupplier.get());
        if (blockItemSupplier != null) {
            Creator.ITEMS.register(name, blockItemSupplier);
        }
        if (blockEntitySupplier != null) {
            Creator.BLOCK_ENTITIES.register(name, blockEntitySupplier);
        }
    }
}

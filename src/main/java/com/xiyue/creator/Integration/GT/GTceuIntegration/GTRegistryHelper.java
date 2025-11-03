package com.xiyue.creator.Integration.GT.GTceuIntegration;

import com.xiyue.creator.Creator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class GTRegistryHelper {
    private static final Predicate<BlockState> IS_NATURAL;
    private static final Item STICKY_RESIN;
    private static final Block RUBBER_LOG;
    private static final Block RUBBER_WOOD;
    private static final Block RUBBER_LEAVES;

    public static Block getRubberLog(){
        return RUBBER_LOG;
    }

    public static Block getRubberWood(){
        return RUBBER_WOOD;
    }

    public static Block getRubberLeaves(){
        return RUBBER_LEAVES;
    }

    static {
        Block rubber_wood = null;
        Block rubber_log = null;
        Block rubber_leaves = null;
        Predicate<BlockState> isNatural = state -> false;
        Item resin = Items.AIR;

        if (Creator.HAS_GTCEU) {
            try {
                resin = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("gtceu", "sticky_resin"));
                rubber_log = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("gtceu", "rubber_log"));
                rubber_leaves = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("gtceu", "rubber_leaves"));
                rubber_wood = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("gtceu", "rubber_wood"));

                Class<?> RubberLogBlock = Class.forName("com.gregtechceu.gtceu.common.block.RubberLogBlock");

                Method isNaturalMethod = RubberLogBlock.getMethod("isNatural", BlockState.class);

                isNatural = state -> {
                    try {
                        Block block = state.getBlock();

                        if (RubberLogBlock.isInstance(block)) {
                            return (boolean) isNaturalMethod.invoke(block, state);
                        }

                    } catch (Exception ignored) {}
                    return false;
                };
            } catch (Throwable ignored) {}
        }
        RUBBER_WOOD = rubber_wood;
        RUBBER_LOG = rubber_log;
        RUBBER_LEAVES = rubber_leaves;
        IS_NATURAL = isNatural;
        STICKY_RESIN = resin;
    }

    public static boolean isNaturalRubberLog(BlockState state) {
        return RUBBER_LOG != null && state.is(RUBBER_LOG) && IS_NATURAL.test(state);
    }

    public static Item getStickyResin() {
        return STICKY_RESIN;
    }

}
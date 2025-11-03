package com.xiyue.creator.api.BlockEntities.StrainerFrameEntity;

import com.xiyue.creator.MyEnum.DustType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class GetDustForTag {
    private static final Map<DustType, List<Item>> ITEM_GROUPS = new EnumMap<>(DustType.class);
    private static final Map<DustType, ItemStack> STACKS = new EnumMap<>(DustType.class);

    static {
        // 初始化所有矿物组
        for (DustType type : DustType.values()) {
            List<Item> items = new ArrayList<>();
            BuiltInRegistries.ITEM.getTag(type.getTag())
                    .ifPresent(holders -> holders.forEach(holder -> items.add(holder.value())));
            //同tag物品
            ITEM_GROUPS.put(type, items);
            //默认物品stack
            if (!items.isEmpty()) {
                STACKS.put(type, items.getFirst().getDefaultInstance());
            }
        }
    }

    public static List<Item> getItemsForType(DustType type) {
        return ITEM_GROUPS.getOrDefault(type, Collections.emptyList());
    }

    public static ItemStack getDefaultStack(DustType type) {
        return STACKS.getOrDefault(type, ItemStack.EMPTY);
    }
}

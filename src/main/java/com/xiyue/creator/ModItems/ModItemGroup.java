package com.xiyue.creator.ModItems;

import com.xiyue.creator.Creator;
import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.ModItems.FunctionItems.Meshes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ModItemGroup {
    //注册器
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Creator.MODID);

    //物品数组
    public static final ArrayList<DeferredItem<? extends Item>> ITEM_GROUP = new ArrayList<>();

    //滤网
    public static final DeferredItem<Item> ORDINARY_MESH = CommonItemRegister("string_mesh", () -> new Meshes(new Meshes.Properties().durability(100),5, 7, "string_mesh"));
    public static final DeferredItem<Item> LEAF_MESH = CommonItemRegister("leaf_mesh", () -> new Meshes(new Meshes.Properties().durability(20),3,5, "leaf_mesh"));
    public static final DeferredItem<Item> BAMBOO_MESH = CommonItemRegister("bamboo_mesh", () -> new Meshes(new Meshes.Properties().durability(200),4,8, "bamboo_mesh"));
    public static final DeferredItem<Item> CARBON_FIBER_MESH = CommonItemRegister("carbon_fiber_mesh", () -> new Meshes(new Meshes.Properties().durability(1000),1,3, true, "carbon_fiber_mesh"));

    //普通物品
    public static final DeferredItem<Item> PLANT_FIBER = CommonItemRegister("plant_fiber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DRY_BAMBOO = CommonItemRegister("dry_bamboo", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DRY_BARK = CommonItemRegister("dry_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STRAW = CommonItemRegister("straw", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CLAY_EMBRYO = CommonItemRegister("clay_embryo", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MOIST_CLAY = CommonItemRegister("moist_clay", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DIRT_EMBRYO = CommonItemRegister("dirt_embryo", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BIRCH_LUMBER = CommonItemRegister("birch_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ACACIA_LUMBER = CommonItemRegister("acacia_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CHERRY_LUMBER = CommonItemRegister("cherry_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CRIMSON_LUMBER = CommonItemRegister("crimson_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DARK_OAK_LUMBER = CommonItemRegister("dark_oak_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> JUNGLE_LUMBER = CommonItemRegister("jungle_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MANGROVE_LUMBER = CommonItemRegister("mangrove_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OAK_LUMBER = CommonItemRegister("oak_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPRUCE_LUMBER = CommonItemRegister("spruce_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WARPED_LUMBER = CommonItemRegister("warped_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RUBBER_LUMBER = CommonItemRegister("rubber_lumber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WARPED_BARK = CommonItemRegister("warped_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CRIMSON_BARK = CommonItemRegister("crimson_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ACACIA_BARK = CommonItemRegister("acacia_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BIRCH_BARK = CommonItemRegister("birch_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CHERRY_BARK = CommonItemRegister("cherry_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DARK_OAK_BARK = CommonItemRegister("dark_oak_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> JUNGLE_BARK = CommonItemRegister("jungle_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MANGROVE_BARK = CommonItemRegister("mangrove_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OAK_BARK = CommonItemRegister("oak_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPRUCE_BARK = CommonItemRegister("spruce_bark", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RUBBER_BARK = CommonItemRegister("rubber_bark", () -> new Item(new Item.Properties()));

    //方块物品
    public static final DeferredItem<BlockItem> SUAN = BlockItemRegister("suan", ModBlockGroup.SUAN_BLOCK);

    //普通物品注册
    public static <I extends Item> DeferredItem<I> CommonItemRegister(String name, Supplier<? extends I> sup) {
        DeferredItem<I> item = ITEMS.register(name, sup);
        ITEM_GROUP.add(item);
        return item;
    }


    //方块物品注册
    public static DeferredItem<BlockItem> BlockItemRegister(String name, Supplier<? extends Block> block){
        DeferredItem<BlockItem> item = ITEMS.registerSimpleBlockItem(name, block);
        ITEM_GROUP.add(item);
        return item;
    }

}

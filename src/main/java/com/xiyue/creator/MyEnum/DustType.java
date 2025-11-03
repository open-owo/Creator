package com.xiyue.creator.MyEnum;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public enum DustType {
    TINY_DUST_CLAY,
    SMALL_DUST_CLAY,
    DUST_CLAY,
    TINY_DUST_STONE,
    SMALL_DUST_STONE,
    DUST_STONE,
    TINY_DUST_SAND,
    SMALL_DUST_SAND,
    DUST_SAND,

    TINY_IMPURE_DUST_HEMATITE,
    TINY_IMPURE_DUST_MAGNETITE,
    TINY_IMPURE_DUST_PYRITE,
    TINY_IMPURE_DUST_CHALCOPYRITE,
    TINY_IMPURE_DUST_MALACHITE,
    TINY_IMPURE_DUST_COPPER,
    TINY_IMPURE_DUST_CASSITERITE,
    TINY_IMPURE_DUST_GALENA,
    TINY_IMPURE_DUST_SPHALERITE,
    TINY_IMPURE_DUST_BAUXITE,
    TINY_IMPURE_DUST_MAGNESITE,
    TINY_IMPURE_DUST_COAL,
    TINY_IMPURE_DUST_REDSTONE,
    TINY_IMPURE_DUST_SULFUR,
    TINY_IMPURE_DUST_SALT,
    TINY_IMPURE_DUST_ROCK_SALT,
    TINY_IMPURE_DUST_LAPIS,
    TINY_IMPURE_DUST_GOLD,
    TINY_IMPURE_DUST_SILVER,
    TINY_IMPURE_DUST_PLATINUM,
    TINY_IMPURE_DUST_CALCITE,
    TINY_IMPURE_DUST_ILMENITE,
    TINY_IMPURE_DUST_CHROMITE,
    TINY_IMPURE_DUST_CINNABAR,
    TINY_IMPURE_DUST_SPODUMENE,
    TINY_IMPURE_DUST_BERYL,
    TINY_IMPURE_DUST_GRAPHITE,
    TINY_IMPURE_DUST_PENTLANDITE,
    TINY_IMPURE_DUST_CASSITERITE_SAND,
    TINY_IMPURE_DUST_YELLOW_LIMONITE,
    TINY_IMPURE_DUST_BORNITE,
    TINY_IMPURE_DUST_CHALCOCITE,
    TINY_IMPURE_DUST_REALGAR,
    TINY_IMPURE_DUST_GYPSUM,
    TINY_IMPURE_DUST_APATITE,
    TINY_IMPURE_DUST_GLAUCONITE_SAND,
    TINY_IMPURE_DUST_TALC,
    TINY_IMPURE_DUST_DIATOMITE,
    TINY_IMPURE_DUST_COBALTITE,
    TINY_IMPURE_DUST_EMERALD,
    TINY_IMPURE_DUST_SOAPSTONE,
    TINY_IMPURE_DUST_PYROLUSITE,
    TINY_IMPURE_DUST_SODALITE;

    private final String tagPath;

    //默认路径
    DustType() {
        String name = this.name();
        String dustMarker = "_DUST_";
        int dustIndex = name.indexOf(dustMarker);

        if (dustIndex != -1) {
            // 处理包含"_DUST_"的情况
            String beforeDust = name.substring(0, dustIndex).toLowerCase();
            String afterDust = name.substring(dustIndex + dustMarker.length()).toLowerCase();

            this.tagPath = beforeDust + "_dusts/" + afterDust;
        } else if (name.startsWith("DUST_")) {
            // 处理以"DUST_"开头的情况
            String afterDust = name.substring("DUST_".length()).toLowerCase();
            this.tagPath = "dusts/" + afterDust;
        } else {
            // 兜底处理（实际应不会执行到这里）
            this.tagPath = "dusts/" + name.toLowerCase();
        }
    }

    //自定义路径
    DustType(String customPath) {
        this.tagPath = customPath;
    }
    //全自定义路径
    DustType(String path, String gtPath) {
        this.tagPath = path;
    }

    public TagKey<Item> getTag() {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", tagPath));
    }
}

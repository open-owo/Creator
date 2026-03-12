package com.xiyue.creator.Integration.GT.Prefixes;


import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.tag.TagPrefix;
import com.xiyue.creator.Integration.GT.Material.MyMaterialIcon;
import net.minecraft.network.chat.Component;

import static com.gregtechceu.gtceu.api.tag.TagPrefix.Conditions.hasOreProperty;
import static com.gregtechceu.gtceu.data.item.GTMaterialItems.purifyMap;

public class MyPrefixes {
    public static void init() {
        purifyMap.put(SmallImpureDust, TagPrefix.dustSmall);
        purifyMap.put(TinyImpureDust, TagPrefix.dustTiny);
    }
    // 1/4 of a Dust
    public static final TagPrefix SmallImpureDust = new TagPrefix("smallImpureDust")
            .idPattern("small_impure_%s_dust")
            .defaultTagPath("small_impure_dusts/%s")
            .unformattedTagPath("small_impure_dusts")
            .langValue("Small Pile of Impure %s Dust")
            .materialAmount(GTValues.M / 4)
            .materialIconType(MyMaterialIcon.dustImpureSmall)
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(hasOreProperty)
            .tooltip((mat, tooltips) -> tooltips.add(Component.translatable("metaitem.dust.tooltip.purify")));
    // 1/9th of a Dust.
    public static final TagPrefix TinyImpureDust = new TagPrefix("tinyImpureDust")
            .idPattern("tiny_impure_%s_dust")
            .defaultTagPath("tiny_impure_dusts/%s")
            .unformattedTagPath("tiny_impure_dusts")
            .langValue("Tiny Pile of Impure %s Dust")
            .materialAmount(GTValues.M / 9)
            .materialIconType(MyMaterialIcon.dustImpureTiny)
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(hasOreProperty)
            .tooltip((mat, tooltips) -> tooltips.add(Component.translatable("metaitem.dust.tooltip.purify")));
}

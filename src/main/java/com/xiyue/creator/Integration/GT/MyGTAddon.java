package com.xiyue.creator.Integration.GT;


import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.material.ChemicalHelper;
import com.gregtechceu.gtceu.api.material.material.Material;
import com.gregtechceu.gtceu.api.material.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.material.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.api.tag.TagPrefix;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.xiyue.creator.Creator;
import com.xiyue.creator.Integration.GT.Prefixes.MyPrefixes;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.gregtechceu.gtceu.api.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.api.tag.TagPrefix.dustTiny;
import static com.gregtechceu.gtceu.data.recipe.GTRecipeTypes.PACKER_RECIPES;

@GTAddon(Creator.MODID)
public class MyGTAddon implements IGTAddon {

    private final GTRegistrate registrate;

    public MyGTAddon() {
        this.registrate = GTRegistrate.create(Creator.MODID);
    }

    @Override
    public GTRegistrate getRegistrate() {
        return registrate;
    }

    @Override
    public void gtInitComplete() {

    }

    @Override
    public void addRecipes(RecipeOutput provider) {
        for (Material material : GTRegistries.MATERIALS) {
//            processImpureDusts(provider, material);
            processTinyDust(provider, material);
            processSmallDust(provider, material);
        }
    }

    private static void processImpureDusts(RecipeOutput provider, Material material) {
        if (!material.hasProperty(PropertyKey.DUST)) return;

        ItemStack ImpureDustStack = ChemicalHelper.get(TagPrefix.dustImpure, material);
        ItemStack SmallImpureDust = ChemicalHelper.get(MyPrefixes.SmallImpureDust, material);
        ItemStack TinyImpureDust = ChemicalHelper.get(MyPrefixes.TinyImpureDust, material);

        // ========== SmallImpureDust 配方 ==========
        if (material.shouldGenerateRecipesFor(MyPrefixes.SmallImpureDust)) {
            PACKER_RECIPES.recipeBuilder("package_small_impure_" + material.getName())
                    .inputItems(MyPrefixes.SmallImpureDust, material, 4)
                    .outputItems(ImpureDustStack)
                    .save(provider);
            VanillaRecipeHelper.addShapedRecipe(provider,
                    "small_impure_to_dust_" + material.getName(),
                    ImpureDustStack,
                    "XX", "XX",
                    'X', new MaterialEntry(TagPrefix.dustImpure, material)
            );
        }



        // ========== TinyImpureDust 配方 ==========
        if (material.shouldGenerateRecipesFor(MyPrefixes.TinyImpureDust)) {
            //9x TinyImpureDust -> 1x impureDust
            PACKER_RECIPES.recipeBuilder("package_tiny_impure_" + material.getName())
                    .inputItems(MyPrefixes.TinyImpureDust, material, 9)
                    .circuitMeta(1) // 打包操作
                    .outputItems(ImpureDustStack)
                    .save(provider);
            VanillaRecipeHelper.addShapedRecipe(provider,
                    "tiny_impure_to_dust_" + material.getName(),
                    ImpureDustStack,
                    "XXX", "XXX", "XXX",
                    'X', new MaterialEntry(MyPrefixes.TinyImpureDust, material)
            );
        }
    }

    private static void processTinyDust(@NotNull RecipeOutput provider, @NotNull Material material) {
        if (!material.shouldGenerateRecipesFor(MyPrefixes.TinyImpureDust) || !material.hasProperty(PropertyKey.DUST)) {
            return;
        }

        // tiny dust retains magnetism
        ItemStack TinyImpureDust = ChemicalHelper.get(MyPrefixes.TinyImpureDust, material);
        ItemStack ImpureDustStack = ChemicalHelper.get(TagPrefix.dustImpure, material);

        VanillaRecipeHelper.addStrictShapedRecipe(provider,
                String.format("impure_tiny_dust_disassembling_%s", material.getName()),
                TinyImpureDust.copyWithCount(9),
                "X ", "  ", 'X',
                new MaterialEntry(TagPrefix.dustImpure, material));
        VanillaRecipeHelper.addShapedRecipe(provider,
                String.format("impure_tiny_dust_assembling_%s", material.getName()),
                ImpureDustStack,
                "XXX", "XXX", "XXX", 'X',
                new MaterialEntry(MyPrefixes.TinyImpureDust, material));

        PACKER_RECIPES.recipeBuilder("package_" + material.getName() + "_impure_tiny_dust")
                .inputItems(MyPrefixes.TinyImpureDust, material, 9)
                .circuitMeta(1)
                .outputItems(ImpureDustStack)
                .save(provider);

        PACKER_RECIPES.recipeBuilder("unpackage_" + material.getName() + "_impure_tiny_dust")
                .inputItems(TagPrefix.dustImpure, material)
                .circuitMeta(1)
                .outputItems(TinyImpureDust.copyWithCount(9))
                .save(provider);
    }

    private static void processSmallDust(@NotNull RecipeOutput provider, @NotNull Material material) {
        if (!material.shouldGenerateRecipesFor(MyPrefixes.SmallImpureDust) || !material.hasProperty(PropertyKey.DUST)) {
            return;
        }

        // small dust retains magnetism
        ItemStack SmallImpureDust = ChemicalHelper.get(MyPrefixes.SmallImpureDust, material);
        ItemStack ImpureDustStack = ChemicalHelper.get(TagPrefix.dustImpure, material);

        VanillaRecipeHelper.addStrictShapedRecipe(provider,
                String.format("impure_small_dust_disassembling_%s", material.getName()),
                SmallImpureDust.copyWithCount(4), " X", "  ", 'X', new MaterialEntry(TagPrefix.dustImpure, material));
        VanillaRecipeHelper.addShapedRecipe(provider, String.format("impure_small_dust_assembling_%s", material.getName()),
                ImpureDustStack, "XX", "XX", 'X', new MaterialEntry(MyPrefixes.SmallImpureDust, material));

        PACKER_RECIPES.recipeBuilder("package_" + material.getName() + "_impure_small_dust")
                .inputItems(MyPrefixes.SmallImpureDust, material, 4)
                .circuitMeta(1)
                .outputItems(ImpureDustStack)
                .save(provider);

        PACKER_RECIPES.recipeBuilder("unpackage_" + material.getName() + "_impure_small_dust")
                .inputItems(TagPrefix.dustImpure, material)
                .circuitMeta(2)
                .outputItems(SmallImpureDust.copyWithCount(4))
                .save(provider);
    }
}
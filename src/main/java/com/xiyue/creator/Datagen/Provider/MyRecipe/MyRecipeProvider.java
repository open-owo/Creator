package com.xiyue.creator.Datagen.Provider.MyRecipe;

import com.xiyue.creator.Creator;
import com.xiyue.creator.Datagen.Provider.MyRecipe.Builder.subBuilderRecipeBuilder;
import com.xiyue.creator.Datagen.Provider.MyRecipe.DryingRack.subDryingRackBuilder;
import com.xiyue.creator.Datagen.Provider.MyRecipe.NoConsume.NoConsumeBuilder;
import com.xiyue.creator.Datagen.Provider.MyRecipe.Stripping.SubStrippingBuilder;
import com.xiyue.creator.Integration.GT.GTceuIntegration.GTRegistryHelper;
import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.ModItems.ModItemGroup;
import com.xiyue.creator.MyIngredient.IngredientWithCount.IngredientWithCount;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderInput;
import com.xiyue.creator.MyRecipe.ChanceResult;
import com.xiyue.creator.tag.BlockTag;
import com.xiyue.creator.tag.ItemTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.crafting.BlockTagIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MyRecipeProvider extends RecipeProvider {
    public MyRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        builderRecipe(output);
        strippingRecipe(output);
        DryingRack(output);
        CraftingShapeless(output);
    }

    private void CraftingShapeless(RecipeOutput output){
        new NoConsumeBuilder(RecipeCategory.MISC, Items.STICK, 2)
                .requires(Items.FLINT)
                .requires(ItemTag.BARK)
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_flint", has(Items.FLINT))
                .unlockedBy("has_bark", has(ItemTag.BARK))
                .save(output, Creator.MODID);
    }

    private void DryingRack(RecipeOutput output){
        new subDryingRackBuilder(Ingredient.of(ItemTag.BARK),
                300 * 20,
                ModItemGroup.DRY_BARK.toStack(1)
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "dry_bark"));

        new subDryingRackBuilder(Ingredient.of(ItemTag.BAMBOO),
                300 * 20,
                ModItemGroup.DRY_BAMBOO.toStack(1)
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "dry_bamboo"));

        new subDryingRackBuilder(Ingredient.of(Items.SHORT_GRASS, Items.FERN),
                300 * 20,
                ModItemGroup.STRAW.toStack(1)
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "dry_grass"));

        new subDryingRackBuilder(Ingredient.of(Items.TALL_GRASS, Items.LARGE_FERN),
                300 * 20,
                ModItemGroup.STRAW.toStack(2)
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "dry_tall_grass"));
    }

    private void strippingRecipe(@NotNull RecipeOutput output){
        float axeBarkChance = 0.7f;
        float knifeBarkChance = 0.6f;
        float axeBarkStickyResin = 0.6f;
        float knifeBarkStickyResin = 0.7f;
        float flintChance = 0.3f;
        //斧头
        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.RUBBER_LOG),
                List.of(new ChanceResult(ModItemGroup.RUBBER_BARK.toStack(1), axeBarkChance, 0), new ChanceResult(GTRegistryHelper.getStickyResin().getDefaultInstance(), axeBarkStickyResin, 1))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_rubber"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.BIRCH_LOG),
                List.of(new ChanceResult(ModItemGroup.BIRCH_BARK.get().getDefaultInstance(), axeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_birch"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.ACACIA_LOG),
                List.of(new ChanceResult(ModItemGroup.ACACIA_BARK.toStack(1), axeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_acacia"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.CHERRY_LOG),
                List.of(new ChanceResult(ModItemGroup.CHERRY_BARK.toStack(1), axeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_cherry"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.CRIMSON_STEM),
                List.of(new ChanceResult(ModItemGroup.CRIMSON_BARK.toStack(1), axeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_stem"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.DARK_OAK_LOG),
                List.of(new ChanceResult(ModItemGroup.DARK_OAK_BARK.toStack(1), axeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_dark_oak"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.JUNGLE_LOG),
                List.of(new ChanceResult(ModItemGroup.JUNGLE_BARK.toStack(1), axeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_jungle"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.ACACIA_LOG),
                List.of(new ChanceResult(ModItemGroup.MANGROVE_BARK.toStack(1), axeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_mangrove"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.OAK_LOG),
                List.of(new ChanceResult(ModItemGroup.OAK_BARK.toStack(1), axeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_oak"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.SPRUCE_LOG),
                List.of(new ChanceResult(ModItemGroup.SPRUCE_BARK.toStack(1), axeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_spruce"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTags.AXES),
                new BlockTagIngredient(BlockTag.WARPED_STEM),
                List.of(new ChanceResult(ModItemGroup.WARPED_BARK.toStack(1), axeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/axes_stripping_warped"));
        //小刀
        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.RUBBER_LOG),
                List.of(new ChanceResult(ModItemGroup.RUBBER_BARK.toStack(1), knifeBarkChance, 0), new ChanceResult(GTRegistryHelper.getStickyResin().getDefaultInstance(), knifeBarkStickyResin, 1))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_rubber"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.BIRCH_LOG),
                List.of(new ChanceResult(ModItemGroup.BIRCH_BARK.toStack(1), knifeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_birch"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.ACACIA_LOG),
                List.of(new ChanceResult(ModItemGroup.ACACIA_BARK.toStack(1), knifeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_acacia"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.CHERRY_LOG),
                List.of(new ChanceResult(ModItemGroup.CHERRY_BARK.toStack(1), knifeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_cherry"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.CRIMSON_STEM),
                List.of(new ChanceResult(ModItemGroup.CRIMSON_BARK.toStack(1), knifeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_stem"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.DARK_OAK_LOG),
                List.of(new ChanceResult(ModItemGroup.DARK_OAK_BARK.toStack(1), knifeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_dark_oak"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.JUNGLE_LOG),
                List.of(new ChanceResult(ModItemGroup.JUNGLE_BARK.toStack(1), knifeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_jungle"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.ACACIA_LOG),
                List.of(new ChanceResult(ModItemGroup.MANGROVE_BARK.toStack(1), knifeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_mangrove"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.OAK_LOG),
                List.of(new ChanceResult(ModItemGroup.OAK_BARK.toStack(1), knifeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_oak"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.SPRUCE_LOG),
                List.of(new ChanceResult(ModItemGroup.SPRUCE_BARK.toStack(1), knifeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_spruce"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.KNIFE),
                new BlockTagIngredient(BlockTag.WARPED_STEM),
                List.of(new ChanceResult(ModItemGroup.WARPED_BARK.toStack(1), knifeBarkChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/knives_stripping_warped"));

        //燧石
        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.RUBBER_LOG),
                List.of(new ChanceResult(ModItemGroup.RUBBER_BARK.toStack(1), flintChance, 0), new ChanceResult(GTRegistryHelper.getStickyResin().getDefaultInstance(), knifeBarkStickyResin, 1))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_rubber"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.BIRCH_LOG),
                List.of(new ChanceResult(ModItemGroup.BIRCH_BARK.toStack(1), flintChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_birch"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.ACACIA_LOG),
                List.of(new ChanceResult(ModItemGroup.ACACIA_BARK.toStack(1), flintChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_acacia"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.CHERRY_LOG),
                List.of(new ChanceResult(ModItemGroup.CHERRY_BARK.toStack(1), flintChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_cherry"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.CRIMSON_STEM),
                List.of(new ChanceResult(ModItemGroup.CRIMSON_BARK.toStack(1), flintChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_stem"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.DARK_OAK_LOG),
                List.of(new ChanceResult(ModItemGroup.DARK_OAK_BARK.toStack(1), flintChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_dark_oak"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.JUNGLE_LOG),
                List.of(new ChanceResult(ModItemGroup.JUNGLE_BARK.toStack(1), flintChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_jungle"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.ACACIA_LOG),
                List.of(new ChanceResult(ModItemGroup.MANGROVE_BARK.toStack(1), flintChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_mangrove"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.OAK_LOG),
                List.of(new ChanceResult(ModItemGroup.OAK_BARK.toStack(1), flintChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_oak"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.SPRUCE_LOG),
                List.of(new ChanceResult(ModItemGroup.SPRUCE_BARK.toStack(1), flintChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_spruce"));

        new SubStrippingBuilder(
                Ingredient.of(ItemTag.FLINT),
                new BlockTagIngredient(BlockTag.WARPED_STEM),
                List.of(new ChanceResult(ModItemGroup.WARPED_BARK.toStack(1), flintChance, 0))
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping/flint_stripping_warped"));
    }

    private void builderRecipe(@NotNull RecipeOutput output) {
        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.OAK_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.OAK_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.OAK_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_oak_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.ACACIA_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.ACACIA_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.ACACIA_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_acacia_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.BIRCH_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.BIRCH_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.BIRCH_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_birch_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.CHERRY_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.CHERRY_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.CHERRY_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_cherry_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.DARK_OAK_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.DARK_OAK_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.DARK_OAK_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_dark_oak_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.JUNGLE_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.JUNGLE_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.JUNGLE_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_jungle_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.MANGROVE_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.MANGROVE_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.MANGROVE_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_mangrove_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.SPRUCE_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.SPRUCE_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.SPRUCE_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_spruce_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.CRIMSON_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.CRIMSON_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.CRIMSON_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_crimson_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.WARPED_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.WARPED_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.WARPED_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_warped_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.RUBBER_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.RUBBER_LUMBER, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(4, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))),
                ModBlockGroup.RUBBER_STRAINER_FRAME.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "frames/build_rubber_strainer_frame"));

        new subBuilderRecipeBuilder(
                List.of(new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.ROOD_WOODEN, 4)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(2, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.ROOD_WOODEN, 2)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(2, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.ROOD_WOODEN, 2)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(2, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER))),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromItemTag(ItemTag.ROOD_WOODEN, 2)),
                        new BuilderInput.BuilderStep(IngredientWithCount.fromCompoundIngredient(2, Ingredient.of(ItemTag.STRING), Ingredient.of(ItemTag.PLANT_FIBER)))
                ),
                ModBlockGroup.DRYING_RACK.get().defaultBlockState()
        ).save(output, ResourceLocation.fromNamespaceAndPath(Creator.MODID, "drying_rack/build_drying_rack"));
    }
}

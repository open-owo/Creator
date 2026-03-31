package com.xiyue.creator.Integration.Jei;

import com.xiyue.creator.Creator;
import com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.Category.*;
import com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.RecipeType.StrainerFrameRecipeType;
import com.xiyue.creator.ModBlockEntities.ModBlockEntities;
import com.xiyue.creator.ModItems.ModItemGroup;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import com.xiyue.creator.ModBlockEntities.StrainerFrameEntity.BiomeDustMap;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@JeiPlugin
public class CreatorJeiPlugin implements IModPlugin {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "creator_jei_plugin");
    public static final ResourceLocation TEXTURE_PATH = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "textures/gui/jei/jei_icon.png");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new WaterStrainerFrameCategory(guiHelper));
        registration.addRecipeCategories(new LavaStrainerFrameCategory(guiHelper));
        registration.addRecipeCategories(new StippingCategory(guiHelper));
        registration.addRecipeCategories(new BuilderCategory(guiHelper));
        registration.addRecipeCategories(new DryingRackCategory(guiHelper));
        registration.addRecipeCategories(new SoakingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        RegisterRecipe registerRecipe = new RegisterRecipe();

        //滤水
        List<StrainerFrameRecipeType> WaterStrainerFrameRecipe = new ArrayList<>();
        for (Map.Entry<TagKey<Biome>, List<BiomeDustMap.WeightedDust>> entry : BiomeDustMap.getWaterDustForBiome().entrySet()) {
            WaterStrainerFrameRecipe.add(new StrainerFrameRecipeType(StrainerFrameRecipeType.fluidEnum.WATER, entry.getKey()));
        }
        registration.addRecipes(WaterStrainerFrameCategory.TYPE, WaterStrainerFrameRecipe);

        //滤熔岩
        List<StrainerFrameRecipeType> LavaStrainerFrameRecipe = new ArrayList<>();
        for (Map.Entry<TagKey<Biome>, List<BiomeDustMap.WeightedDust>> entry : BiomeDustMap.getWaterDustForBiome().entrySet()) {
            LavaStrainerFrameRecipe.add(new StrainerFrameRecipeType(StrainerFrameRecipeType.fluidEnum.LAVA, entry.getKey()));
        }
        registration.addRecipes(LavaStrainerFrameCategory.TYPE, LavaStrainerFrameRecipe);

        //去皮
        registration.addRecipes(StippingCategory.TYPE, registerRecipe.getRecipeManager().getAllRecipesFor(RegisterRecipe.STRIPPING_TYPE.get()));

        //builder
        registration.addRecipes(BuilderCategory.TYPE, registerRecipe.getRecipeManager().getAllRecipesFor(RegisterRecipe.BUILDER_TYPE.get()));

        //晾晒
        registration.addRecipes(DryingRackCategory.TYPE, registerRecipe.getRecipeManager().getAllRecipesFor(RegisterRecipe.DRYING_RACK_TYPE.get()));

        //浸润
        registration.addRecipes(SoakingCategory.TYPE, registerRecipe.getRecipeManager().getAllRecipesFor(RegisterRecipe.SOAKING_TYPE.get()));

    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registerRecipeCatalysts(registration, ModBlockEntities.STRAINER_FRAME.get().getBlockSuppliers(), WaterStrainerFrameCategory.TYPE);
        registerRecipeCatalysts(registration, ModBlockEntities.IRON_STRAINER_FRAME.get().getBlockSuppliers(), WaterStrainerFrameCategory.TYPE, LavaStrainerFrameCategory.TYPE);
        registerRecipeCatalysts(registration, ModBlockEntities.STONE_STRAINER_FRAME.get().getBlockSuppliers(),WaterStrainerFrameCategory.TYPE, LavaStrainerFrameCategory.TYPE);

        registerRecipeCatalysts(registration, ModBlockEntities.DRYING_RACK.get().getBlockSuppliers(), DryingRackCategory.TYPE);
    }

    private void registerRecipeCatalysts(IRecipeCatalystRegistration registration, Set<? extends Supplier<? extends Block>> blocks, RecipeType<?>... recipeTypes) {
        for (var block : blocks) {
            registration.addRecipeCatalyst(new ItemStack(block.get()), recipeTypes);
        }
    }
}
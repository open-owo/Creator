package com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.Category;

import com.xiyue.creator.Creator;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import com.xiyue.creator.MyRecipe.SoakingRecipe.SoakingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoakingCategory implements IRecipeCategory<RecipeHolder<SoakingRecipe>> {
    public static final RecipeType<RecipeHolder<SoakingRecipe>> TYPE = RecipeType.createFromVanilla(RegisterRecipe.SOAKING_TYPE.get());
    private final IDrawable ICON;

    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;

    @Override
    public @NotNull RecipeType<RecipeHolder<SoakingRecipe>> getRecipeType() {
        return TYPE;
    }

    public SoakingCategory(IGuiHelper guiHelper){
        this.ICON = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, Items.WATER_BUCKET.getDefaultInstance());
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable(Creator.MODID+ ".jei.category.Soaking");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return ICON;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<SoakingRecipe> recipeHolder, @NotNull IFocusGroup focuses) {
        SoakingRecipe recipe = recipeHolder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 20, 30).addIngredients(recipe.inputItem()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 30).addFluidStack(recipe.inputBlockState().getBlock().defaultBlockState().getFluidState().getType()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 30).addItemStack(recipe.resultItem()).setStandardSlotBackground();
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
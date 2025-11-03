package com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.Category;

import com.xiyue.creator.Creator;
import com.xiyue.creator.ModItems.ModItemGroup;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderRecipe;
import com.xiyue.creator.MyRecipe.DryingRackRecipe.DryingRackRecipe;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DryingRackCategory implements IRecipeCategory<RecipeHolder<DryingRackRecipe>> {
    public static final RecipeType<RecipeHolder<DryingRackRecipe>> TYPE = RecipeType.createFromVanilla(RegisterRecipe.DRYING_RACK_TYPE.get());

    private final IDrawable ICON;

    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;

    public DryingRackCategory(IGuiHelper guiHelper){
        this.ICON = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ModItemGroup.DRYING_RACK.toStack(1));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<DryingRackRecipe> recipeHolder, IFocusGroup focuses) {
        DryingRackRecipe recipe =  recipeHolder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 20, 30).addIngredients(recipe.getInputItem()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 30).addItemStack(recipe.getResultItem()).setStandardSlotBackground();
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public RecipeType<RecipeHolder<DryingRackRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable(Creator.MODID+ ".jei.category.Drying");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return ICON;
    }
}

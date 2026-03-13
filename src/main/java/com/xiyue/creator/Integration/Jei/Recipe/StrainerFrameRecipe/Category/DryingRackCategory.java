package com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.Category;

import com.xiyue.creator.Creator;
import com.xiyue.creator.ModItems.ModItemGroup;
import com.xiyue.creator.MyRecipe.DryingRackRecipe.DryingRackRecipe;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.xiyue.creator.Integration.Jei.CreatorJeiPlugin.TEXTURE_PATH;

public class DryingRackCategory implements IRecipeCategory<RecipeHolder<DryingRackRecipe>> {
    public static final RecipeType<RecipeHolder<DryingRackRecipe>> TYPE = RecipeType.createFromVanilla(RegisterRecipe.DRYING_RACK_TYPE.get());

    private final IDrawable ICON;
    private final IDrawable SunIcon;
    private final IDrawable MoonIcon;

    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;

    public DryingRackCategory(IGuiHelper guiHelper){
        this.ICON = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ModItemGroup.DRYING_RACK.toStack(1));
        this.SunIcon = guiHelper.createDrawable(TEXTURE_PATH, 0, 34, 16, 16);
        this.MoonIcon = guiHelper.createDrawable(TEXTURE_PATH, 0, 50, 16, 16);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<DryingRackRecipe> recipeHolder, @NotNull IFocusGroup focuses) {
        DryingRackRecipe recipe =  recipeHolder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 20, 20).addIngredients(recipe.inputItem()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 70, 20).addItemStack(recipe.resultItem()).setStandardSlotBackground();
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
    public @NotNull RecipeType<RecipeHolder<DryingRackRecipe>> getRecipeType() {
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

    @Override
    public void draw(@NotNull RecipeHolder<DryingRackRecipe> recipe, @NotNull IRecipeSlotsView slots, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        SunIcon.draw(gui, 42, 20);
    }
}

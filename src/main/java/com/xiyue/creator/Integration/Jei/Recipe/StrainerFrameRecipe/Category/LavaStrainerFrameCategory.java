package com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.Category;

import com.xiyue.creator.Creator;
import com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.RecipeType.StrainerFrameRecipeType;
import com.xiyue.creator.ModItems.ModItemGroup;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.xiyue.creator.Integration.Jei.CreatorJeiPlugin.TEXTURE_PATH;

public class LavaStrainerFrameCategory implements IRecipeCategory<StrainerFrameRecipeType> {
    public static final RecipeType<StrainerFrameRecipeType> TYPE = RecipeType.create(Creator.MODID, "lava_filtering", StrainerFrameRecipeType.class);

    private final IDrawable Icon;
    private final IDrawable arrowIcon;
    private final IDrawable plusIcon;

    private static final int WIDTH = 176;
    private static final int HEIGHT = 110;

    public LavaStrainerFrameCategory(IGuiHelper guiHelper){
        this.Icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItemGroup.ORDINARY_MESH.get()));
        this.arrowIcon = guiHelper.createDrawable(TEXTURE_PATH, 0, 0, 16, 10);
        this.plusIcon = guiHelper.getRecipePlusSign();
    }

    @Override
    public @NotNull RecipeType<StrainerFrameRecipeType> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei.category.filtering");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return Icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, StrainerFrameRecipeType recipe, @NotNull IFocusGroup focuses) {
        List<ItemStack> list = recipe.getItemStackList();
        int rows = (int) Math.ceil(list.size() / 9.0);
        int outputY = 40;
        int outputX = 5;
        int count = 0;
        for (int row = 0; row < rows; row++) {
            int colsInRow = Math.min(9, list.size() - count);
            for (int col = 0; col < colsInRow; col++) {
                builder.addSlot(RecipeIngredientRole.OUTPUT, outputX + col * 18, outputY + row * 18)
                        .addItemStack(list.get(count)).setStandardSlotBackground();
                count++;
            }
        }

        builder.addSlot(RecipeIngredientRole.CATALYST, 5, 17)
                .addIngredients(recipe.getIngredients()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.CATALYST, 45, 17)
                .addFluidStack(recipe.getFluids()).setStandardSlotBackground();
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
    public void draw(StrainerFrameRecipeType recipe, @NotNull IRecipeSlotsView slots, GuiGraphics gui, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        gui.drawString(font, Component.translatable("creator.jei." + recipe.getBiomeName()), 5, 5, 5635925, false);
        plusIcon.draw(gui, 26, 18);
        arrowIcon.draw(gui, 64, 21);
    }
}

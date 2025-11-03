package com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.Category;

import com.xiyue.creator.Creator;
import com.xiyue.creator.MyIngredient.IngredientWithCount.IngredientWithCount;
import com.xiyue.creator.MyIngredient.IngredientWithCount.RecipeUtils;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderInput;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderRecipe;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BuilderCategory implements IRecipeCategory<RecipeHolder<BuilderRecipe>> {
    public static final RecipeType<RecipeHolder<BuilderRecipe>> TYPE = RecipeType.createFromVanilla(RegisterRecipe.BUILDER_TYPE.get());

    private static final ResourceLocation TEXTURE_PATH = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "textures/gui/jei/jei_icon.png");
    private final IDrawable ICON;
    private final IDrawable RIGHT_ARROW;
    private final IDrawable LEFT_ARROW;
    private final IDrawable DOWN_ARROW;

    private static final int WIDTH = 134;
    private static final int HEIGHT = 110;

    public BuilderCategory(IGuiHelper guiHelper){
        this.ICON = guiHelper.createDrawable(TEXTURE_PATH, 0, 20 ,16, 16);
        this.RIGHT_ARROW = guiHelper.createDrawable(TEXTURE_PATH, 0, 10, 8 , 5);
        this.LEFT_ARROW = guiHelper.createDrawable(TEXTURE_PATH, 0, 15, 8 , 5);
        this.DOWN_ARROW = guiHelper.createDrawable(TEXTURE_PATH, 10, 11, 5 , 8);
    }

    @Override
    public @NotNull RecipeType<RecipeHolder<BuilderRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable(Creator.MODID+ ".jei.category.building");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return ICON;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<BuilderRecipe> recipe, IFocusGroup focuses) {
        int startX = 5;
        int startY = 2;

        int i;

        List<BuilderInput.BuilderStep> list = recipe.value().getBuilderSteps();
        for (i = 0; i < list.size(); i++) {
            int[] pos = getPos(startX, startY, i);
            startX = pos[0];
            startY = pos[1];
            IngredientWithCount ingredient = list.get(i).stack();
            builder.addSlot(RecipeIngredientRole.INPUT, startX, startY).addIngredients(RecipeUtils.iWCToIngredientSaveCount(ingredient)).setStandardSlotBackground();
        }

        int[] pos = getPos(startX, startY, i);
        startX = pos[0];
        startY = pos[1];
        ItemStack result = recipe.value().getResult().getBlock().asItem().getDefaultInstance();
        builder.addSlot(RecipeIngredientRole.OUTPUT, startX, startY).addItemStack(result).setStandardSlotBackground();
    }


    private static int[] getPos(int x, int y, int i) {
        int offsetX = 36;
        int offsetY = 36;

        int count = (i + 1) % 8;
        int count1 = (i + 1 ) / 4 + 1;

        if ((i + 1 ) % 4 == 0) count1 = (i + 1 ) / 4;

        y = 2 + offsetY * (count1 - 1);

        if (count != 1 && count != 5 && count1 % 2 == 1) {
            x += offsetX;
        } else if (count != 1 && count != 5 && count1 % 2 == 0) {
            x -= offsetX;
        }
        return new int[]{x, y};
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
    public void draw(@NotNull RecipeHolder<BuilderRecipe> recipe, @NotNull IRecipeSlotsView slots, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        int startX = 27;
        int startY = 7;

        int startX1 = 5;
        int startY1 = 2;

        int i, j;
        List<BuilderInput.BuilderStep> list = recipe.value().getBuilderSteps();

        for (j = 0; j < list.size() + 1; j++) {
            int[] pos = getPos(startX1, startY1, j);
            startX1 = pos[0];
            startY1 = pos[1];
        }
        gui.drawString(Minecraft.getInstance().font, Component.translatable(Creator.MODID + ".jei.building.output"), startX1, startY1 + 17, 16755200, true);

        for (i = 0; i < list.size(); i++) {
            int[] pos = getArrowPos(startX, startY, i, gui);
            startX = pos[0];
            startY = pos[1];
        }
    }

    private int[] getArrowPos(int x, int y, int i, GuiGraphics gui) {
        int startLeftX = 27;
        int startRightX = 99;

        int startDownY = 24;

        int offsetX = 36;
        int offsetY = 36;

        int count1 = (i + 1) % 8;
        int count2 = (i + 1) / 4 + 1;

        if ((i + 1 ) % 4 == 0) count2 = (i + 1 ) / 4;

        int y1 = 7 + (count2 - 1) * offsetY;
        int y2 = startDownY + (count2 - 1) * offsetY;

        if (count1 != 0 && count1 != 1 && count1 != 4 && count1 != 5 && count2 % 2 == 1) {
            x += offsetX;
            RIGHT_ARROW.draw(gui, x, y1);
            return new int[]{x, y1};
        } else if (count1 != 0 && count1 != 1 && count1 != 4 && count1 != 5 && count2 % 2 == 0) {
            x -= offsetX;
            LEFT_ARROW.draw(gui, x, y1);
            return new int[]{x, y1};
        } else if (count1 == 4) {
            x = 118;
            DOWN_ARROW.draw(gui, x, y2);
            return new int[]{x, y2};
        } else if (count1 == 0) {
            x = 9;
            DOWN_ARROW.draw(gui, x, y2);
            return new int[]{x, y2};
        } else if (count1 == 1) {
            x = startLeftX;
            RIGHT_ARROW.draw(gui, x, y1);
            return new int[]{x, y1};
        } else if (count1 == 5) {
            x = startRightX;
            LEFT_ARROW.draw(gui, x, y1);
            return new int[]{x, y1};
        }
        return new int[]{x, y};
    }
}

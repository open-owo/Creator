package com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.Category;

import com.xiyue.creator.Creator;
import com.xiyue.creator.MyRecipe.ChanceResult;
import com.xiyue.creator.MyRecipe.StrippingRecipe.StrippingRecipe;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StippingCategory implements IRecipeCategory<RecipeHolder<StrippingRecipe>> {
    public static final RecipeType<RecipeHolder<StrippingRecipe>> TYPE = RecipeType.createFromVanilla(RegisterRecipe.STRIPPING_TYPE.get());

    private final IDrawable Icon;
    private final IDrawable arrowIcon;

    private static final int WIDTH = 100;
    private static final int HEIGHT = 40;

    public StippingCategory(IGuiHelper guiHelper){
        this.Icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.DIAMOND_AXE));
        this.arrowIcon = guiHelper.getRecipeArrow();
    }

    @Override
    public @NotNull RecipeType<RecipeHolder<StrippingRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable(Creator.MODID+ ".jei.category.stripping");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return Icon;
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<StrippingRecipe> recipe, @NotNull IFocusGroup focuses) {
        int startX = 65;
        int startY = 13;
        Ingredient inputTool = recipe.value().inputItem();

        Ingredient inputBlock = recipe.value().inputBlock().toVanilla();
        List<ChanceResult> results = recipe.value().results();


        builder.addSlot(RecipeIngredientRole.CATALYST, 5, 2).addIngredients(inputTool).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 22).addIngredients(inputBlock).setStandardSlotBackground();

        for (int i = 0; i < results.size(); i++) {
            float chance = results.get(i).chance();
            int[] pos = calculateGridPositions(startX, startY, i, results.size());
            startX = pos[0];
            startY = pos[1];
            String chanceText = String.format("%.1f%%", chance * 100);
            builder.addSlot(RecipeIngredientRole.OUTPUT, startX, startY).addItemStack(results.get(i).stack()).setStandardSlotBackground().addRichTooltipCallback((recipeSlotView, tooltip) -> {
                if (chance > 0 && chance< 1) tooltip.add(Component.translatable(Creator.MODID + ".jei.stripping.chance", chanceText).withColor(getChanceColor(chance)));
            });
        }
    }

    private static int[] calculateGridPositions(int x, int y, int i, int n){
        if (n != 2) {
            int size = (int) Math.ceil(Math.sqrt(n));

            int centerX = x + 9;
            int centerY = y + 9;

            int totalSize = size * 18;
            int startX = centerX - totalSize / 2;
            int startY = centerY - totalSize / 2;

            int row = i / size;
            int col = i % size;
            int posX = startX + col * 18;
            int posY = startY + row * 18;
            return new int[]{posX, posY};
        } else {
            int X = x;
            if (i == 0) X = x - 9;
            X = X + (18 * i);
            return new int[]{X, y};
        }
    }

    private static int getChanceColor(float chance) {
        chance = Math.max(0.0f, Math.min(1.0f, chance));
        if (chance < 0.25f) {
            int red = 255;
            int green = (int)(255 * (chance / 0.25f));
            return (red << 16) | (green << 8);
        } else if (chance < 0.5f) {
            int red = 255;
            int green = (int)(255 * ((chance - 0.25f) / 0.25f));
            return (red << 16) | (green << 8);
        } else if (chance < 0.75f) {
            int red = 255 - (int)(255 * ((chance - 0.5f) / 0.25f));
            int green = 255;
            return (red << 16) | (green << 8);
        } else {
            int red = (int)(255 * (1.0f - (chance - 0.75f) / 0.25f));
            int green = 255;
            return (red << 16) | (green << 8);
        }
    }

//    @Override
//    public void draw(@NotNull RecipeHolder<StrippingRecipe> recipe, @NotNull IRecipeSlotsView slots, GuiGraphics gui, double mouseX, double mouseY) {
//        arrowIcon.draw(gui, 30, 12);
//
//        float textScale = 0.5f;
//        int startX = 65;
//        int startY = 13;
//
//        gui.pose().pushPose();
//        gui.pose().scale(textScale, textScale, 1.0f);
//        gui.pose().translate(0,0, 200);
//        startX = startX - (recipe.value().getResults().size() - 1) * 9;
//        for (ChanceResult result : recipe.value().getResults()) {
//            float chance = result.chance();
//            String chanceText = String.format("%.1f%%", chance * 100);
//            Component customText = Component.translatable(Creator.MODID + ".jei.stripping.only.chance", chanceText).withStyle(style -> style.withFont(Minecraft.DEFAULT_FONT).withColor(getChanceColor(chance)));
//            int scaledX = (int) (startX / textScale);
//            int scaledY = (int) (startY / textScale);
//            startX += 18;
//            gui.drawString(Minecraft.getInstance().font, customText, scaledX, scaledY, getChanceColor(chance));
//        }
//        gui.pose().popPose();
//
//    }

    @Override
    public void draw(@NotNull RecipeHolder<StrippingRecipe> recipe, @NotNull IRecipeSlotsView slots, GuiGraphics gui, double mouseX, double mouseY) {
        arrowIcon.draw(gui, 30, 12);

        float textScale = 0.5f;
        int startX = 65;
        int startY = 13;

        List<ChanceResult> results = recipe.value().results();

        gui.pose().pushPose();
        gui.pose().scale(textScale, textScale, 1.0f);
        gui.pose().translate(0,0, 200);

        for (int i = 0; i < results.size(); i++) {
            float chance = results.get(i).chance();
            String chanceText = String.format("%.1f%%", chance * 100);
            Component customText = Component.translatable(Creator.MODID + ".jei.stripping.only.chance", chanceText).withStyle(style -> style.withFont(Minecraft.DEFAULT_FONT).withColor(getChanceColor(chance)));
            int[] pos = calculateGridPositions(startX, startY, i, results.size());
            startX = pos[0];
            startY = pos[1];
            int scaledX = (int) (startX / textScale);
            int scaledY = (int) (startY / textScale);
            gui.drawString(Minecraft.getInstance().font, customText, scaledX, scaledY, getChanceColor(chance));
        }
        gui.pose().popPose();
    }
}

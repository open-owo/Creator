package com.xiyue.creator.ModGUIS.Button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ItemStackButton extends Button {
    private ItemStack itemStack;
    private Ingredient ingredient;
    private ItemStack[] stacks;
    private int currentIndex;
    private long lastUpdateTime;
    private static final int SWITCH_INTERVAL = 1000;

    public ItemStackButton(int x, int y, int width, int height, ItemStack stack, OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress,Button.DEFAULT_NARRATION);
        this.itemStack = stack;
    }

    public ItemStackButton(int x, int y, int width, int height, Ingredient ingredient, OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
        this.ingredient = ingredient;
        this.stacks = ingredient.getItems();
        this.currentIndex = 0;
        this.lastUpdateTime = System.currentTimeMillis();
    }

        @Override
    public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (isHovered()) {
            graphics.fill(getX(), getY(), getX() + width, getY() + height, 0x80_888888);
        }

        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        graphics.renderItem(itemStack, getX(), getY());
        graphics.renderItemDecorations(font, itemStack, getX(), getY());

        if (isHovered()) {
            graphics.renderTooltip(font, itemStack, mouseX, mouseY);
        }
    }

//    @Override
//    public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
//        // 定时切换显示的物品
//        long currentTime = System.currentTimeMillis();
//        if (stacks.length > 1 && currentTime - lastUpdateTime > SWITCH_INTERVAL) {
//            currentIndex = (currentIndex + 1) % stacks.length;
//            lastUpdateTime = currentTime;
//        }
//        // 获取当前要显示的物品
//        ItemStack currentStack = stacks.length > 0 ? stacks[currentIndex] : ItemStack.EMPTY;
//        // 渲染背景
//        if (isHovered()) {
//            graphics.fill(getX(), getY(), getX() + width, getY() + height, 0x80_888888);
//        }
//        // 渲染物品
//        Minecraft minecraft = Minecraft.getInstance();
//        Font font = minecraft.font;
//        if (!currentStack.isEmpty()) {
//            graphics.renderItem(currentStack, getX(), getY());
//            graphics.renderItemDecorations(font, currentStack, getX(), getY());
//        }
//        // 渲染悬浮提示
//        if (isHovered()) {
//            if (!currentStack.isEmpty()) {
//                graphics.renderTooltip(font, currentStack, mouseX, mouseY);
//            } else {
//                graphics.renderTooltip(font,
//                        Component.literal("Empty Ingredient"),
//                        mouseX, mouseY);
//            }
//        }
//    }

}

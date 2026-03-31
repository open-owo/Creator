package com.xiyue.creator.ModGUIS.Screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class BlueScreenScreen extends Screen {
    private final boolean wasFullscreen;
    private int tick = 0;
    private int progress = 0;
    private static final int PROGRESS_MAX = 300; // 进度条最大宽度

    public BlueScreenScreen(boolean wasFullscreen) {
        super(Component.literal("Blue Screen"));
        this.wasFullscreen = wasFullscreen;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(0, 0, width, height, 0xFF0000AA);
        int centerX = width / 2;
        int centerY = height / 2;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(centerX, centerY - 120, 0);
        float scale = 4.0f;
        guiGraphics.pose().scale(scale, scale, 1.0f);
        guiGraphics.drawCenteredString(font, ":(", 0, 0, 0xFFFFFF);
        guiGraphics.pose().popPose();
        guiGraphics.drawCenteredString(font, Component.literal("Your PC ran into a problem and needs to restart."), centerX, centerY - 40, 0xFFFFFF);
        guiGraphics.drawCenteredString(font, Component.literal("Stop code: FAKE_SYSTEM_SERVICE_EXCEPTION"), centerX, centerY, 0xFFFFFF);
        guiGraphics.drawCenteredString(font, Component.literal("https://www.windows.com/fake-stopcode"), centerX, height - 60, 0xAAAAAA);
        guiGraphics.drawCenteredString(font, Component.literal("Collecting error information..."), centerX, height - 90, 0xFFFFFF);
        tick++;
        if (tick % 20 == 0) {
            progress++;
            if (progress > PROGRESS_MAX) {
                progress = 0;
            }
        }
        int barX = centerX - PROGRESS_MAX / 2;
        int barY = height - 70;
        guiGraphics.fill(barX, barY, barX + PROGRESS_MAX, barY + 5, 0xFFAAAAAA); // 背景
        guiGraphics.fill(barX, barY, barX + progress, barY + 5, 0xFFFFFFFF); // 前景
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // ESC
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(null);
            if (!wasFullscreen) {
                mc.getWindow().toggleFullScreen();
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
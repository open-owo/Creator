package com.xiyue.creator.ModGUIS.Screens.StrainerFrame;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xiyue.creator.ModGUIS.Menus.StrainerFrameMenu;
import com.xiyue.creator.api.ModGUIS.Screens.BaseContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class StrainerFrameGUI<M extends StrainerFrameMenu> extends BaseContainerScreen<M> {
    private int leftPos;
    private int topPos;

    public StrainerFrameGUI(M menu, Inventory playerInventory, Component title, int imageHeight, int imageWidth, String name) {
        super(menu, playerInventory, title, name);
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getParticleShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(this.getBgTexture(), leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight, 256 , 256);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}

package com.xiyue.creator.api.ModGUIS.Screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xiyue.creator.Creator;
import com.xiyue.creator.api.ModGUIS.Menus.StrainerFrameMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public abstract class StrainerFrameGUI<M extends StrainerFrameMenu> extends AbstractContainerScreen<M> {
    //图片路径
    private ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "textures/gui/strainer_frame_gui.png");

    private int leftPos;
    private int topPos;

    public StrainerFrameGUI(M menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        //图片大小
        this.imageWidth = 176;
        this.imageHeight = 193;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public StrainerFrameGUI(M menu, Inventory playerInventory, Component title, int imageHeight, int imageWidth, String name) {
        super(menu, playerInventory, title);
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.inventoryLabelY = this.imageHeight - 94;
        this.TEXTURE = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "textures/gui/" + name + ".png");
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
        //渲染图片
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight, 256 , 256);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}

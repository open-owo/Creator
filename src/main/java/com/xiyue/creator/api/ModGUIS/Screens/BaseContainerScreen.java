package com.xiyue.creator.api.ModGUIS.Screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xiyue.creator.Creator;
import com.xiyue.creator.api.ModGUIS.Menus.BaseMenu;
import com.xiyue.creator.api.ModGUIS.Menus.MenuGuiDefinition;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BaseContainerScreen <M extends BaseMenu> extends AbstractContainerScreen<M> {
    private final ResourceLocation TEXTURE;


    public BaseContainerScreen(M menu, Inventory playerInventory, Component title, MenuGuiDefinition guiDef) {
        super(menu, playerInventory, title);
        this.imageWidth = guiDef.imageWidth();
        this.imageHeight = guiDef.imageHeight();
        this.inventoryLabelY = this.imageHeight - 94;
        this.TEXTURE = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "textures/gui/" + guiDef.textureName() + ".png");;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    protected ResourceLocation getBgTexture(){
        return this.TEXTURE;
    }
}

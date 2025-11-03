package com.xiyue.creator.ModFluids;

import com.xiyue.creator.Creator;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import java.awt.Color;
import java.util.Map;

import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;

public class ModBaseFluidType extends FluidType {

    public final ResourceLocation RENDER_OVERLAY;
    public final ResourceLocation TEXTURE_STILL;
    public final ResourceLocation TEXTURE_FLOW;
    public final ResourceLocation TEXTURE_OVERLAY;
    public final Vector3f FOG_COLOR;
    public final float fogStart;
    public final float fogEnd;
    private final ModClientFluidType clientFluidType;


    public ModBaseFluidType(Properties properties, FluidInfo info) {
        super(properties);
        RENDER_OVERLAY = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/water_overlay.png");
        TEXTURE_STILL = ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_still");
        TEXTURE_FLOW =  ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_flow");
        TEXTURE_OVERLAY = ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_overlay");
        Color colorObject = new Color(info.color);
        FOG_COLOR = new Vector3f(colorObject.getRed()/255F, colorObject.getGreen()/255F, colorObject.getBlue()/255F);
        fogStart = info.fogStart;
        fogEnd = info.fogEnd;

        clientFluidType = new ModClientFluidType(RENDER_OVERLAY, TEXTURE_STILL, TEXTURE_FLOW, TEXTURE_OVERLAY, FOG_COLOR, fogStart, fogEnd);
    }

    public ModBaseFluidType(Properties properties, FluidInfo info, path render_over, path still, path flow, path overlay) {
        super(properties);
        RENDER_OVERLAY = ResourceLocation.fromNamespaceAndPath(render_over.namespace, render_over.path + ".png");
        TEXTURE_STILL = ResourceLocation.fromNamespaceAndPath(still.namespace, still.path);
        TEXTURE_FLOW =  ResourceLocation.fromNamespaceAndPath(flow.namespace, flow.path);
        TEXTURE_OVERLAY = ResourceLocation.fromNamespaceAndPath(overlay.namespace, overlay.path);
        Color colorObject = new Color(info.color);
        FOG_COLOR = new Vector3f(colorObject.getRed()/255F, colorObject.getGreen()/255F, colorObject.getBlue()/255F);
        fogStart = info.fogStart;
        fogEnd = info.fogEnd;

        clientFluidType = new ModClientFluidType(RENDER_OVERLAY, TEXTURE_STILL, TEXTURE_FLOW, TEXTURE_OVERLAY, FOG_COLOR, fogStart, fogEnd);
    }

    public record path (String namespace, String path){}

    public static class FluidInfo {

        public String name;
        public int color;
        public float fogStart;
        public float fogEnd;

        public boolean isTranslucent;

        public FluidInfo(String name, int color, float fogStart, float fogEnd,boolean isTranslucent) {
            this.name = name;
            this.color = color;
            this.fogStart = fogStart;
            this.fogEnd = fogEnd;
            this.isTranslucent  = isTranslucent;
        }
    }

    public IClientFluidTypeExtensions getClientExtensions() {
        return clientFluidType;
    }


    private static class ModClientFluidType implements  IClientFluidTypeExtensions{

        public final ResourceLocation RENDER_OVERLAY;
        public final ResourceLocation TEXTURE_STILL;
        public final ResourceLocation TEXTURE_FLOW;
        public final ResourceLocation TEXTURE_OVERLAY;
        public final Vector3f FOG_COLOR;
        public final float fogStart;
        public final float fogEnd;

        public ModClientFluidType(ResourceLocation renderOverlay, ResourceLocation textureStill, ResourceLocation textureFlow, ResourceLocation textureOverlay, Vector3f fogColor, float fogStart, float fogEnd) {
            RENDER_OVERLAY = renderOverlay;
            TEXTURE_STILL = textureStill;
            TEXTURE_FLOW = textureFlow;
            TEXTURE_OVERLAY = textureOverlay;
            FOG_COLOR = fogColor;
            this.fogStart = fogStart;
            this.fogEnd = fogEnd;
        }


        @Override
        public @NotNull ResourceLocation getStillTexture() {
            return TEXTURE_STILL;
        }

        @Override
        public @NotNull ResourceLocation getFlowingTexture() {
            return TEXTURE_FLOW;
        }

        @Override
        public ResourceLocation getOverlayTexture() {
            return TEXTURE_OVERLAY;
        }

        @Override
        public ResourceLocation getRenderOverlayTexture(@NotNull Minecraft mc) {
            return RENDER_OVERLAY;
        }

        @Override
        public @NotNull Vector3f modifyFogColor(@NotNull Camera camera, float partialTick, @NotNull ClientLevel level, int renderDistance, float darkenWorldAmount, @NotNull Vector3f fluidFogColor) {
            return FOG_COLOR;
        }

        @Override
        public void modifyFogRender(@NotNull Camera camera, FogRenderer.@NotNull FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, @NotNull FogShape shape) {
            RenderSystem.setShaderFogStart(fogStart);
            RenderSystem.setShaderFogEnd(fogEnd);
        }

        @Override
        public int getTintColor() {
            // 返回流体的基础颜色
            return new Color(FOG_COLOR.x, FOG_COLOR.y, FOG_COLOR.z).getRGB();
        }

        @Override
        public int getTintColor(@NotNull FluidStack stack) {
            return getTintColor();
        }
    }
}


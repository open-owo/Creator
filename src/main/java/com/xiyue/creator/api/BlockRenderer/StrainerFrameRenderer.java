package com.xiyue.creator.api.BlockRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.xiyue.creator.ModItems.FunctionItems.Meshes;
import com.xiyue.creator.ModBlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class StrainerFrameRenderer implements BlockEntityRenderer<StrainerFrameEntity> {
    public StrainerFrameRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(StrainerFrameEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        if (!(blockEntity.getItemHandler().getStackInSlot(0).getItem() instanceof Meshes meshStack)) return;

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(meshStack.getTexture_path());

        RenderType renderType = RenderType.cutout();
        VertexConsumer buffer = bufferSource.getBuffer(renderType);

        Matrix4f matrix = poseStack.last().pose();

        renderFace(Direction.DOWN, matrix, buffer, sprite, packedLight, packedOverlay);
        renderFace(Direction.UP, matrix, buffer, sprite, packedLight, packedOverlay);
        renderFace(Direction.NORTH, matrix, buffer, sprite, packedLight, packedOverlay);
        renderFace(Direction.SOUTH, matrix, buffer, sprite, packedLight, packedOverlay);
        renderFace(Direction.WEST, matrix, buffer, sprite, packedLight, packedOverlay);
        renderFace(Direction.EAST, matrix, buffer, sprite, packedLight, packedOverlay);
    }

    private void renderFace(Direction direction, Matrix4f matrix, VertexConsumer buffer, TextureAtlasSprite sprite, int packedLight, int packedOverlay) {
        float offset = 0.05f;
        float min = 0.05f;
        float max = 1 - offset;

        switch (direction) {
            // 底部（DOWN）面
            case DOWN -> renderQuad(matrix, buffer, sprite,
                    new Vec3(min, min, min),
                    new Vec3(min, min, max),
                    new Vec3(max, min, max),
                    new Vec3(max, min, min),
                    packedLight, packedOverlay);

            // 顶部（UP）面
            case UP -> renderQuad(matrix, buffer, sprite,
                    new Vec3(min, max, min),
                    new Vec3(max, max, min),
                    new Vec3(max, max, max),
                    new Vec3(min, max, max),
                    packedLight, packedOverlay);

            // 北面（NORTH）
            case NORTH -> renderQuad(matrix, buffer, sprite,
                    new Vec3(min, min, min),
                    new Vec3(min, max, min),
                    new Vec3(max, max, min),
                    new Vec3(max, min, min),
                    packedLight, packedOverlay);

            // 南面（SOUTH）
            case SOUTH -> renderQuad(matrix, buffer, sprite,
                    new Vec3(max, min, max),
                    new Vec3(max, max, max),
                    new Vec3(min, max, max),
                    new Vec3(min, min, max),
                    packedLight, packedOverlay);

            // 西面（WEST）
            case WEST -> renderQuad(matrix, buffer, sprite,
                    new Vec3(min, min, min),
                    new Vec3(min, min, max),
                    new Vec3(min, max, max),
                    new Vec3(min, max, min),
                    packedLight, packedOverlay);

            // 东面（EAST）
            case EAST -> renderQuad(matrix, buffer, sprite,
                    new Vec3(max, min, max),
                    new Vec3(max, min, min),
                    new Vec3(max, max, min),
                    new Vec3(max, max, max),
                    packedLight, packedOverlay);
        }
    }

    private void renderQuad(Matrix4f matrix, VertexConsumer buffer, TextureAtlasSprite sprite, Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, int packedLight, int packedOverlay) {
        // 渲染正面
        renderQuadFace(matrix, buffer, sprite, v1, v2, v3, v4, packedLight, packedOverlay);

        // 渲染背面（反转法线和顶点顺序）
        renderQuadFace(matrix, buffer, sprite,
                v4, v3, v2, v1, // 反转顶点顺序
                packedLight, packedOverlay);
    }

    private void renderQuadFace(Matrix4f matrix, VertexConsumer buffer, TextureAtlasSprite sprite, Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, int packedLight, int packedOverlay) {
        // 计算法线方向（基于前三个顶点）
        Vector3f normal = calculateNormal(v1, v2, v3);

        // 顶点1
        vertex(buffer, matrix, normal,
                (float)v1.x, (float)v1.y, (float)v1.z,
                sprite.getU0(), sprite.getV0(), packedLight, packedOverlay);

        // 顶点2
        vertex(buffer, matrix, normal,
                (float)v2.x, (float)v2.y, (float)v2.z,
                sprite.getU0(), sprite.getV1(), packedLight, packedOverlay);

        // 顶点3
        vertex(buffer, matrix, normal,
                (float)v3.x, (float)v3.y, (float)v3.z,
                sprite.getU1(), sprite.getV1(), packedLight, packedOverlay);

        // 顶点4
        vertex(buffer, matrix, normal,
                (float)v4.x, (float)v4.y, (float)v4.z,
                sprite.getU1(), sprite.getV0(), packedLight, packedOverlay);
    }

    private Vector3f calculateNormal(Vec3 v1, Vec3 v2, Vec3 v3) {
        // 计算两个边向量
        Vector3f edge1 = new Vector3f(
                (float)(v2.x - v1.x),
                (float)(v2.y - v1.y),
                (float)(v2.z - v1.z)
        );

        Vector3f edge2 = new Vector3f(
                (float)(v3.x - v1.x),
                (float)(v3.y - v1.y),
                (float)(v3.z - v1.z)
        );

        // 计算叉积得到法线
        Vector3f normal = new Vector3f();
        edge1.cross(edge2, normal);
        normal.normalize();

        return normal;
    }

    private void vertex(VertexConsumer buffer, Matrix4f matrix, Vector3f normal, float x, float y, float z, float u, float v, int packedLight, int packedOverlay) {

        buffer.addVertex(matrix, x, y, z)
                .setColor(1.0f, 1.0f, 1.0f, 1.0f)
                .setUv(u, v)
                .setUv1(packedOverlay & 0xFFFF, packedOverlay >> 16 & 0xFFFF)
                .setUv2(packedLight & 0xFFFF, packedLight >> 16 & 0xFFFF)
                .setNormal(normal.x(), normal.y(), normal.z());
    }
}
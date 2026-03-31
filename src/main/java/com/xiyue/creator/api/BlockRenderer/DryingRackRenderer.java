package com.xiyue.creator.api.BlockRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.xiyue.creator.ModBlockEntities.DryingRackEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.xiyue.creator.api.Blocks.Property.BlockStateProperties.*;

public class DryingRackRenderer implements BlockEntityRenderer<DryingRackEntity> {

    public DryingRackRenderer(BlockEntityRendererProvider.Context context) {

    }


    @Override
    public void render(@NotNull DryingRackEntity entity, float v, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        float scale = 0.4f;

        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        renderItem(entity, poseStack, 0, scale, multiBufferSource, packedLight, packedOverlay);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        renderItem(entity, poseStack, 1, scale, multiBufferSource, packedLight, packedOverlay);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        renderItem(entity, poseStack, 2 ,scale, multiBufferSource, packedLight, packedOverlay);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        renderItem(entity, poseStack, 3, scale, multiBufferSource, packedLight, packedOverlay);
        poseStack.popPose();
    }

    private static void renderItem(DryingRackEntity entity, PoseStack poseStack, int slot, float scale, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay){
        float rotationAngle45 = (float) Math.toRadians(45);
        float rotationAngle90 = (float) Math.toRadians(90);

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack itemStack = entity.getItemHandler().getStackInSlot(slot);
        if(itemStack.isEmpty()) return;
        BakedModel bakedModel = itemRenderer.getModel(itemStack, entity.getLevel(), null, slot);

        Direction direction = entity.getBlockState().getValue(DIRECTION);



        //位移
        switch (direction){
            case NORTH -> {
                switch (slot){
                    //前面
                    //右下
                    case 0 -> poseStack.translate((float)0.25/scale, (float)0.35/scale, (float)0.25/scale);
                    //左下
                    case 1 -> poseStack.translate((float)0.75/scale, (float)0.35/scale, (float)0.25/scale);
                    //后面
                    //右上
                    case 2 -> poseStack.translate((float)0.25/scale, (float)0.75/scale, (float)0.65/scale);
                    //左上
                    case 3 -> poseStack.translate((float)0.75/scale, (float)0.75/scale, (float)0.65/scale);
                }
            }
            case SOUTH -> {
                switch (slot){
                    //后面
                    //左下
                    case 0 -> poseStack.translate((float)0.75/scale, (float)0.35/scale, (float)0.75/scale);
                    //右下
                    case 1 -> poseStack.translate((float)0.25/scale, (float)0.35/scale, (float)0.75/scale);
                    //前面
                    //左上
                    case 2 -> poseStack.translate((float)0.75/scale, (float)0.75/scale, (float)0.35/scale);
                    //右上
                    case 3 -> poseStack.translate((float)0.25/scale, (float)0.75/scale, (float)0.35/scale);
                }
            }
            case WEST -> {
                switch (slot){
                    //后面
                    //右下
                    case 0 -> poseStack.translate((float)0.25/scale, (float)0.35/scale, (float)0.75/scale);
                    //前面
                    //右下
                    case 1 -> poseStack.translate((float)0.25/scale, (float)0.35/scale, (float)0.25/scale);
                    //后面
                    //左上
                    case 2 -> poseStack.translate((float)0.65/scale, (float)0.75/scale, (float)0.75/scale);
                    //前面
                    //左上
                    case 3 -> poseStack.translate((float)0.65/scale, (float)0.75/scale, (float)0.25/scale);
                }
            }
            case EAST -> {
                switch (slot){
                    //前面
                    //左下
                    case 0 -> poseStack.translate((float)0.75/scale, (float)0.35/scale, (float)0.25/scale);
                    //后面
                    //左下
                    case 1 -> poseStack.translate((float)0.75/scale, (float)0.35/scale, (float)0.75/scale);
                    //前面
                    //右上
                    case 2 -> poseStack.translate((float)0.35/scale, (float)0.75/scale, (float)0.25/scale);
                    //后面
                    //右上
                    case 3 -> poseStack.translate((float)0.35/scale, (float)0.75/scale, (float)0.75/scale);
                }
            }
        }

        //旋转
        switch (direction) {
            case NORTH -> poseStack.mulPose(Axis.XP.rotation(rotationAngle45));
            case SOUTH -> poseStack.mulPose(Axis.XP.rotation(-rotationAngle45));
            case WEST -> {
                poseStack.mulPose(Axis.ZP.rotation(-rotationAngle45));
                poseStack.mulPose(Axis.YP.rotation(-rotationAngle90));
            }
            case EAST -> {
                poseStack.mulPose(Axis.ZP.rotation(rotationAngle45));
                poseStack.mulPose(Axis.YP.rotation(-rotationAngle90));
            }
        }

        itemRenderer.render(itemStack, ItemDisplayContext.FIXED, true, poseStack, multiBufferSource, packedLight, packedOverlay, bakedModel);
    }
}

package com.xiyue.creator.api.BlockRenderer.Builder;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.xiyue.creator.BuilderSystem.BuilderSystem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class BuilderRenderer implements BlockEntityRenderer<BuilderSystem.BuilderBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public BuilderRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(BuilderSystem.@NotNull BuilderBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getRecipe() == null) return;
        BlockState resultState = blockEntity.getRecipe().getResult();

        float progress = blockEntity.getProgressPercentage();
        VertexConsumer transparencyBuffer = new AlphaModifyingBuffer(bufferSource.getBuffer(RenderType.translucent()), progress);

        poseStack.pushPose();
        BakedModel model = blockRenderer.getBlockModel(resultState);
        blockRenderer.getModelRenderer().renderModel(
                poseStack.last(),
                transparencyBuffer,
                resultState,
                model,
                1, 1, 1,
                packedLight,
                packedOverlay,
                ModelData.EMPTY,
                RenderType.translucent()
        );
        poseStack.popPose();
    }

}

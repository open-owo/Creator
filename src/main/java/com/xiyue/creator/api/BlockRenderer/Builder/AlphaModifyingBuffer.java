package com.xiyue.creator.api.BlockRenderer.Builder;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

class AlphaModifyingBuffer implements VertexConsumer {
    private final VertexConsumer inner;
    private final float progress;

    public AlphaModifyingBuffer(VertexConsumer inner, float progress) {
        this.inner = inner;
        this.progress = progress;
    }

    @Override
    public @NotNull VertexConsumer addVertex(float x, float y, float z) {
        return inner.addVertex(x, y, z).setColor(1.0f, 1.0f, 1.0f, (float) Math.max(progress, 0.2));
    }

    @Override
    public VertexConsumer setColor(int r, int g, int b, int a) {
        return inner.setColor(r, g, b, a);
    }

    @Override
    public VertexConsumer setUv(float u, float v) {
        return inner.setUv(u, v);
    }

    @Override
    public VertexConsumer setUv1(int i, int i1) {
        return inner.setUv1(i, i1);
    }

    @Override
    public VertexConsumer setUv2(int i, int i1) {
        return inner.setUv2(i, i1);
    }

    @Override
    public VertexConsumer setNormal(float v, float v1, float v2) {
        return inner.setNormal(v, v1, v2);
    }
}

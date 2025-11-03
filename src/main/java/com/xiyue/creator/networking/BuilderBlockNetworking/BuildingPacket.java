package com.xiyue.creator.networking.BuilderBlockNetworking;

import com.xiyue.creator.Creator;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record BuildingPacket(String id, Boolean BuildingStrainer) implements CustomPacketPayload {
    public static final Type<BuildingPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Creator.MODID, "builder_building_networking"));
    public static final StreamCodec<ByteBuf, BuildingPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            BuildingPacket::id,
            ByteBufCodecs.BOOL,
            BuildingPacket::BuildingStrainer,
            BuildingPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

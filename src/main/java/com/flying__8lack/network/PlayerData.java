package com.flying__8lack.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static com.flying__8lack.advancedmovementmod.MODID;

public record PlayerData(int power) implements CustomPacketPayload {


    public static final CustomPacketPayload.Type<PlayerData> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MODID, "player_data")
    );


    public static final StreamCodec<ByteBuf, PlayerData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            PlayerData::power,
            PlayerData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

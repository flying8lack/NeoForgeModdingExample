package com.flying__8lack.attachment;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModAttachment {
    public static final DeferredRegister<AttachmentType<?>> ATTCH = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES, MODID);

    public static final Supplier<AttachmentType<Integer>> NETWORK_ENERGY = ATTCH.register("network_energy",
            () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());


    public static void register(IEventBus bus){
        ATTCH.register(bus);
    }
}

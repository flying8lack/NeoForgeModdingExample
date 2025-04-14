package com.flying__8lack.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModSound {

    public static final DeferredRegister<SoundEvent> SOUND = DeferredRegister.create(Registries.SOUND_EVENT,
            MODID);


    public static final DeferredHolder<SoundEvent, SoundEvent> HUM_60HZ = SOUND.register("60hz_hum",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, "60hz_hum")));

    public static void register(IEventBus bus){
        SOUND.register(bus);
    }
}

package com.flying__8lack.effects;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModEffect {

    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT,
            MODID);

    public static final Holder<MobEffect> UNSTABLE_EFFECT = EFFECT.register("unstable_effect",
            () -> new UnstableEffect(MobEffectCategory.HARMFUL, 0x4FAE11));

    public static void register(IEventBus eventBus) {
        EFFECT.register(eventBus);
    }
}

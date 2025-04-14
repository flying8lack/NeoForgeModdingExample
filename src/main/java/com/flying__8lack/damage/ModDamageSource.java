package com.flying__8lack.damage;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModDamageSource {

    public static final ResourceKey<DamageType> LIQUID_PAIN = ResourceKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(MODID, "liquid_pain"));



    public static void bootstrap(BootstrapContext<DamageType> context){
        context.register(LIQUID_PAIN, new DamageType("pain", 1.0f));
    }
}

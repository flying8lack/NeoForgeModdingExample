package com.flying__8lack.world.noise_settings;

import com.flying__8lack.world.biomes.ModBiomes;
import com.flying__8lack.world.biomes.surface.ModSurface;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> UNSTABLE_NOISE_SETTING = ResourceKey.create(Registries.NOISE_SETTINGS,
            ResourceLocation.fromNamespaceAndPath(MODID, "unstable_noise_setting"));

    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context){
        context.register(UNSTABLE_NOISE_SETTING, unstableNoise(context));
    }

    public static NoiseGeneratorSettings unstableNoise(BootstrapContext<NoiseGeneratorSettings> context){
        HolderGetter<DensityFunction> noise = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<Biome> biome = context.lookup(Registries.BIOME);
        var ns = new NoiseSettings(0, 128, 1, 1);
        var rule = ModSurface.makeRules();

        var p = List.of(new Climate.ParameterPoint(
                Climate.Parameter.span(0.6f, 0.9f),
                Climate.Parameter.span(0.6f, 0.9f),
                Climate.Parameter.span(0.6f, 0.9f),
                Climate.Parameter.span(0.6f, 0.9f),
                Climate.Parameter.span(0.6f, 0.9f),
                Climate.Parameter.span(0.6f, 0.9f),
                1L
        ));

        var nr = new NoiseRouter(noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value(),
                noise.getOrThrow(NoiseRouterData.CONTINENTS).value()
        );


        return new NoiseGeneratorSettings(ns, Blocks.DIRT.defaultBlockState(), Blocks.WATER.defaultBlockState(),
                nr , rule, p,40,
                false, true, true, false);
    }
}

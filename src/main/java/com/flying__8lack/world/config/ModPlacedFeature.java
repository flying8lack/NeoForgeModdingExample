package com.flying__8lack.world.config;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;


import static com.flying__8lack.advancedmovementmod.MODID;

import static com.flying__8lack.world.config.ModOrePlacement.commonOrePlacement;

public class ModPlacedFeature {
    public static final ResourceKey<PlacedFeature> POWER_ORE = registerKey("power_ore");
    public static void bootstrap(BootstrapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);


        register(context, POWER_ORE,
                holdergetter.getOrThrow(ModConfigFeature.POWER_ORE),
                commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(0),
                        VerticalAnchor.absolute(50))));

    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(MODID, name));
    }

    public static void register(
            BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
            Holder<ConfiguredFeature<?, ?>> config, List<PlacementModifier> placement
    ){
        context.register(key, new PlacedFeature(config, placement));
    }
}

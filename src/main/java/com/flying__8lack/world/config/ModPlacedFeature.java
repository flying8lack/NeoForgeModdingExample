package com.flying__8lack.world.config;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.flying__8lack.advancedmovementmod.MODID;
import static com.flying__8lack.world.config.ModOrePlacement.commonOrePlacement;

public class ModPlacedFeature {
    public static final ResourceKey<PlacedFeature> POWER_ORE = registerKey("power_ore");
    public static final ResourceKey<PlacedFeature> PAIN_LAKE = registerKey("pain_lake");

    public static void bootstrap(BootstrapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);


        register(context, POWER_ORE,
                holdergetter.getOrThrow(ModConfigFeature.POWER_ORE),
                commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(0),
                        VerticalAnchor.absolute(50))));

        register(context, PAIN_LAKE,
                holdergetter.getOrThrow(ModConfigFeature.PAIN_LAKE),
                List.of(new PlacementModifier[]{RarityFilter.onAverageOnceEvery(64), InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()}));


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

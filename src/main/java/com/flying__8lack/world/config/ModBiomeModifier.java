package com.flying__8lack.world.config;

import com.flying__8lack.entity.ModEntity;
import com.flying__8lack.world.biomes.ModBiomes;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModBiomeModifier {

    public static ResourceKey<BiomeModifier> ADD_POWER_ORE = registerKey("add_titanium_ore");
    public static ResourceKey<BiomeModifier> ADD_PAIN_LAKE = registerKey("add_pain_lake");

    public static ResourceKey<BiomeModifier> ADD_GRASS_WALKER = registerKey("add_grass_walker");

    public static void bootstrap(BootstrapContext<BiomeModifier> context){
        var PlacedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_PAIN_LAKE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.FOREST),
                                biomes.getOrThrow(Biomes.BADLANDS),
                                biomes.getOrThrow(Biomes.DRIPSTONE_CAVES),
                                biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY),
                                biomes.getOrThrow(ModBiomes.UNSTABLE_BIOME)
                        ),
                        HolderSet.direct(PlacedFeatures.getOrThrow(ModPlacedFeature.PAIN_LAKE)),
                        GenerationStep.Decoration.LAKES

                ));

        context.register(ADD_POWER_ORE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(PlacedFeatures.getOrThrow(ModPlacedFeature.POWER_ORE)),
                        GenerationStep.Decoration.RAW_GENERATION

                ));

        context.register(ADD_GRASS_WALKER,
                new BiomeModifiers.AddSpawnsBiomeModifier(HolderSet.direct(
                        biomes.getOrThrow(Biomes.PLAINS),
                        biomes.getOrThrow(Biomes.MEADOW),
                        biomes.getOrThrow(Biomes.DARK_FOREST),
                        biomes.getOrThrow(Biomes.FOREST),
                        biomes.getOrThrow(ModBiomes.UNSTABLE_BIOME)),
                        List.of(new MobSpawnSettings.SpawnerData(
                                ModEntity.GRASS_WALKER_MONSTER_ENTITY.get(),16,1,3))));


    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(MODID, name));
    }
}

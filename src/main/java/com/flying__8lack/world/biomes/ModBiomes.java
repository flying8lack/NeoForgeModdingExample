package com.flying__8lack.world.biomes;

import com.flying__8lack.entity.ModEntity;
import com.flying__8lack.world.config.ModPlacedFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModBiomes {
    public static final ResourceKey<Biome> UNSTABLE_BIOME = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(MODID, "unstable_biome"));


    public static void bootStrap(BootstrapContext<Biome> context){
        context.register(UNSTABLE_BIOME, unstableBiome(context));
    }

    public static Biome unstableBiome(BootstrapContext<Biome> context){
        var bgs = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));

        bgs.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeature.POWER_ORE);

        BiomeDefaultFeatures.addDefaultSprings(bgs);
        BiomeDefaultFeatures.addLukeWarmKelp(bgs);

        BiomeDefaultFeatures.addDripstone(bgs);
        BiomeDefaultFeatures.addDefaultOres(bgs);
        BiomeDefaultFeatures.addExtraEmeralds(bgs);

        var mob = (new MobSpawnSettings.Builder());
        BiomeDefaultFeatures.caveSpawns(mob);
                mob = mob.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SNIFFER, 9, 1,
                        1))
                .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GUARDIAN, 1, 1,
                        1))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CAVE_SPIDER, 10, 1,
                        3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 70, 1,
                        2))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 100, 4,
                        8))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntity.GRASS_WALKER_MONSTER_ENTITY.get(), 10, 1,
                                2))
                .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 60, 2,
                        3))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 120, 1,
                        3))
                .addMobCharge(EntityType.ILLUSIONER, 4, 30)
                .addMobCharge(EntityType.GUARDIAN, 18, 8);






        return new Biome.BiomeBuilder()

                .generationSettings(bgs.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .fogColor(533031)
                        .skyColor(21079)
                        .waterColor(0xffcda1)
                        .waterFogColor(0xefcda1)
                        .grassColorOverride(52785)
                        .ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                .build())
                .hasPrecipitation(false)
                .temperature(0.68f)
                .downfall(0.9f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .mobSpawnSettings(mob.build())
                .build();

    }

}

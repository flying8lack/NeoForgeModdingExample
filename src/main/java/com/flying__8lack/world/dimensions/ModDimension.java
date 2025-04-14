package com.flying__8lack.world.dimensions;

import com.flying__8lack.world.biomes.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModDimension {
    public static final ResourceKey<LevelStem> INSTABLE_WORLD_STEM = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(MODID, "instable_world_stem"));
    public static final ResourceKey<Level> INSTABLE_WORLD_LEVEL = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(MODID, "instable_world_stem"));

    public static final ResourceKey<DimensionType> INSTABLE_WORLD_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(MODID, "instable_world_type"));

    public static void bootstrapType(BootstrapContext<DimensionType> context){
        context.register(INSTABLE_WORLD_TYPE, new DimensionType(
               OptionalLong.of(18000),
               false,
               false,
               false,
               false,
               1.0,
               true,
               true,
                0,
                128,
                128,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0.2f,
                new DimensionType.MonsterSettings(false,false, ConstantInt.of(0),
                        0)

        ));
    }

    public static void bootstarpStem(BootstrapContext<LevelStem> context){
        HolderGetter<Biome> biome = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimType = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noise = context.lookup(Registries.NOISE_SETTINGS);



        NoiseBasedChunkGenerator chungGen = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(
                                List.of(Pair.of(
                                        Climate.parameters(1.0f, 0.2f, 1.0f,
                                                0.3f, 0.1f, 0.1f, 0.0f), biome.getOrThrow(ModBiomes.UNSTABLE_BIOME)
                                ),
                                        Pair.of(
                                                Climate.parameters(1.0f, 0.2f, 0.9f,
                                                        0.3f, 0.05f, 0.16f, 0.1f), biome.getOrThrow(ModBiomes.UNSTABLE_BIOME)
                                        ))
                        )
                ),
                        noise.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
        );

        LevelStem stem = new LevelStem(dimType.getOrThrow(INSTABLE_WORLD_TYPE), chungGen);
        //context.register(INSTABLE_WORLD_STEM, stem);
    }


}

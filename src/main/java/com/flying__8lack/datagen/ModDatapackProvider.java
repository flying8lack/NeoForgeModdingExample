package com.flying__8lack.datagen;


import com.flying__8lack.damage.ModDamageSource;
import com.flying__8lack.world.biomes.ModBiomes;
import com.flying__8lack.world.density.ModDensityFunction;
import com.flying__8lack.world.dimensions.ModDimension;
import com.flying__8lack.world.config.ModBiomeModifier;
import com.flying__8lack.world.config.ModConfigFeature;
import com.flying__8lack.world.config.ModPlacedFeature;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, ModBiomes::bootStrap)
            .add(Registries.DAMAGE_TYPE, ModDamageSource::bootstrap)
            .add(Registries.DENSITY_FUNCTION, ModDensityFunction::bootstrap)
            .add(Registries.DIMENSION_TYPE, ModDimension::bootstrapType)
            .add(Registries.CONFIGURED_FEATURE, ModConfigFeature::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeature::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifier::bootstrap)
            .add(Registries.LEVEL_STEM, ModDimension::bootstarpStem);

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MODID));
    }
}

package com.flying__8lack.world.biomes.surface;

import com.flying__8lack.world.biomes.ModBiomes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModSurface {

    

    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource GRASS = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);


    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS), DIRT);

        return SurfaceRules.sequence(
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.UNSTABLE_BIOME),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, MUD),
                                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT))
                )),


                // Default to a grass and dirt surface
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static void register(IEventBus bus){

    }


    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}

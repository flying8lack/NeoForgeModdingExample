package com.flying__8lack.world.config;

import com.flying__8lack.ModBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModConfigFeature {


    //CF -> PF -> BM
    public static ResourceKey<ConfiguredFeature<?, ?>> POWER_ORE = registerKey("titanium_ore");
    public static ResourceKey<ConfiguredFeature<?, ?>> CHEST_CIRCLE = registerKey("chest_circle");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context){
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> world = List.of(

                OreConfiguration.target(stoneReplaceable, ModBlock.TITANIUM_ORE.get().defaultBlockState())
        );

        register(context, POWER_ORE, Feature.ORE,
                new OreConfiguration(world, 6));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(MODID, name));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature, FC config
    ){
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}

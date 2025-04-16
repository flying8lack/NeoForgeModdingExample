package com.flying__8lack.world.config;

import com.flying__8lack.ModBlock;
import com.flying__8lack.blocks.fluids.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModConfigFeature {


    //CF -> PF -> BM
    public static ResourceKey<ConfiguredFeature<?, ?>> POWER_ORE = registerKey("titanium_ore");
    public static ResourceKey<ConfiguredFeature<?, ?>> PAIN_TREE = registerKey("pain_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> PAIN_LAKE = registerKey("pain_lake");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context){
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> world = List.of(

                OreConfiguration.target(stoneReplaceable, ModBlock.TITANIUM_ORE.get().defaultBlockState())
        );

        register(context, POWER_ORE, Feature.ORE,
                new OreConfiguration(world, 6));

        register(context, PAIN_LAKE, Feature.LAKE,
                new LakeFeature.Configuration(
                        BlockStateProvider.simple(ModFluids.LIQUID_PAIN_BLOCK.get().defaultBlockState()),
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                .add(Blocks.STONE.defaultBlockState(), 2)
                                .add(Blocks.DIRT.defaultBlockState(), 1)
                                .build())
                )

        );

        //new SpringConfiguration(
        //                ModFluids.LIQUID_PAIN.get().defaultFluidState(), true,
        //                2, 4,
        //                HolderSet.direct(
        //                        Block::builtInRegistryHolder,
        //                        Blocks.STONE,
        //                        Blocks.GRANITE,
        //                        Blocks.DIORITE,
        //                        Blocks.ANDESITE,
        //                        Blocks.DEEPSLATE,
        //                        Blocks.TUFF,
        //                        Blocks.CALCITE,
        //                        Blocks.DIRT
        //                )
        //                )

        register(context, PAIN_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(Blocks.BIRCH_LOG),
                    new BendingTrunkPlacer(4, 2, 7, 5,
                            ConstantInt.of(7)),
                    BlockStateProvider.simple(Blocks.AIR),
                    new BlobFoliagePlacer(ConstantInt.of(1),ConstantInt.of(1),1),
                    new TwoLayersFeatureSize(2, 0, 2)
                ).forceDirt().build()
        );



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

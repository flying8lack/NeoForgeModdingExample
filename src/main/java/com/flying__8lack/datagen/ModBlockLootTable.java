package com.flying__8lack.datagen;

import com.flying__8lack.ModBlock;
import com.flying__8lack.ModItem;
import com.flying__8lack.blocks.fluids.ModFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.Holder;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

import java.util.Set;

public class ModBlockLootTable extends BlockLootSubProvider {
    protected ModBlockLootTable(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlock.THERMITE_DIGGER_BLOCK.get());
        dropSelf(ModBlock.TITANIUM_ORE.get());
        dropSelf(ModBlock.WARDEN_CALLER_BLOCK.get());
        dropSelf(ModBlock.COAL_PROCESSOR_BLOCK.get());
        dropSelf(ModBlock.BROKEN_BLOCK.get());
        dropSelf(ModBlock.COAL_POWER_BLOCK.get());

        dropSelf(ModFluids.LIQUID_PAIN_BLOCK.get());

        add(ModBlock.RADAR_BLOCK.get(), createSingleItemTable(ModItem.TITANIUM_INGOT));
//        add(ModBlock.BROKEN_BLOCK.get(), block -> createSingleItemTable(Items.FIRE_CORAL, new BinomialDistributionGenerator(
//                new ConstantValue(4), new ConstantValue(0.4f)
//        )));
        //add(ModBlock.TITANIUM_ORE.get(), block -> createOreDrop(ModBlock.TITANIUM_ORE.get(), Items.APPLE));

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlock.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}

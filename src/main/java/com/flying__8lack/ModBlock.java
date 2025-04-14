package com.flying__8lack;

import com.flying__8lack.blocks.*;
import com.flying__8lack.blocks.fluids.LiquidPainBlock;
import com.flying__8lack.blocks.fluids.ModFluids;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModBlock {



    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> COAL_POWER_BLOCK = BLOCKS.register("coal_power_block",
            () -> new BlockCoalPower(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> THERMITE_DIGGER_BLOCK = BLOCKS.register("thermite_digger_block",
            () -> new BlockThermiteDigger(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> BROKEN_BLOCK = BLOCKS.register("broken_block",
            () -> new BlockBroken(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore",
            () -> new BlockPowerOre(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> WARDEN_CALLER_BLOCK = BLOCKS.register("warden_caller_block",
            () -> new BlockWardenToy(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> COAL_PROCESSOR_BLOCK = BLOCKS.register("coal_processor_block",
            () -> new BlockCoalProcessor(BlockBehaviour.Properties.of().noOcclusion()));

    public static final DeferredBlock<Block> RADAR_BLOCK = BLOCKS.register("radar_block",
            () -> new BlockRadar(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> NODE_BLOCK = BLOCKS.register("node_block",
            () -> new BlockNode(BlockBehaviour.Properties.of()));





    public static void register(IEventBus event){
        BLOCKS.register(event);
    }
}

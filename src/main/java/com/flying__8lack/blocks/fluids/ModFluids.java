package com.flying__8lack.blocks.fluids;

import com.flying__8lack.ModBlock;
import com.flying__8lack.blocks.BlockLiquidPain;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModFluids {


    public static final DeferredRegister<Fluid> FLUID = DeferredRegister.create(Registries.FLUID,
            MODID);

    public static final DeferredRegister<FluidType> FT = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES,
            MODID);

    public static final DeferredHolder<FluidType, FluidType> CUSTOM_TYPE = FT.register("liquid_pain",
            () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("fluid.advancedmovementmod.custom_fluid")
                    .motionScale(0.014)
                    .viscosity(1250)
                    .canSwim(true)));

    public static final DeferredHolder<Fluid,FlowingFluid> LIQUID_PAIN = FLUID.register(
            "liquid_pain",
            () -> new LiquidPainFluid.Source(ModFluids.PAIN_PROP));

    public static final DeferredHolder<Fluid,FlowingFluid> LIQUID_PAIN_FLOWING = FLUID.register(
            "liquid_pain_flowing",
            () -> new LiquidPainFluid.Flowing(ModFluids.PAIN_PROP));

    public static final DeferredBlock<LiquidBlock> LIQUID_PAIN_BLOCK = ModBlock.BLOCKS.register("liquid_pain_block",
            () -> new BlockLiquidPain(ModFluids.LIQUID_PAIN.get(), BlockBehaviour.Properties.of()));


    public static final BaseFlowingFluid.Properties PAIN_PROP = new BaseFlowingFluid.Properties(
            CUSTOM_TYPE, LIQUID_PAIN,
            LIQUID_PAIN_FLOWING
    ).block(LIQUID_PAIN_BLOCK);



    public static void register(IEventBus bus){
        FT.register(bus);
        FLUID.register(bus);
    }
}

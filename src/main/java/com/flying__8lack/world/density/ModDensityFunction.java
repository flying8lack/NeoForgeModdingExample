package com.flying__8lack.world.density;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModDensityFunction {

    public static final DeferredRegister<MapCodec<? extends DensityFunction>> reg = DeferredRegister.create(
            Registries.DENSITY_FUNCTION_TYPE, MODID
    );

    public static final ResourceKey<DensityFunction> level_zero = ResourceKey.create(
            Registries.DENSITY_FUNCTION, ResourceLocation.fromNamespaceAndPath(MODID, "level_br_zero")
    );

    public static final ResourceKey<DensityFunction> smooth_land = ResourceKey.create(
            Registries.DENSITY_FUNCTION, ResourceLocation.fromNamespaceAndPath(MODID, "smooth_land")
    );

    public static final DeferredHolder<MapCodec<? extends DensityFunction>,
            MapCodec<Level0DensityFunction>> level_zero_type = reg.register(
            "level_br_zero_type", Level0DensityFunction.CODEC::codec);

    public static final DeferredHolder<MapCodec<? extends DensityFunction>,
            MapCodec<SmoothLandDensityFunction>> smooth_land_type = reg.register(
            "smooth_land_type", SmoothLandDensityFunction.CODEC::codec);


    public static void register(IEventBus bus){
        reg.register(bus);
    }

    public static void bootstrap(BootstrapContext<DensityFunction> context){

        context.register(level_zero, new Level0DensityFunction());
        context.register(smooth_land, new SmoothLandDensityFunction());
    }


}

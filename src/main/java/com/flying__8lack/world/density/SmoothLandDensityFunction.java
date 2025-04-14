package com.flying__8lack.world.density;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapEncoder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.List;

public class SmoothLandDensityFunction implements DensityFunction {

    public static final KeyDispatchDataCodec<SmoothLandDensityFunction> CODEC = KeyDispatchDataCodec.of(
            MapCodec.unit(SmoothLandDensityFunction::new)
            );

    private final PerlinSimplexNoise noise = new PerlinSimplexNoise(RandomSource.create(), List.of(4, 2, 1));



    @Override
    public double compute(FunctionContext context) {
        if(context.blockY() < 0 || context.blockY() > 100){
            return -2;
        }

        int X = context.blockX();
        int Z = context.blockZ();
        double land = noise.getValue(X, Z, false);
        double erosion =
                noise.getValue(X * 0.125, Z * 0.125,
                        false) * 0.5;



        return 0.5 + land - erosion;
    }

    @Override
    public void fillArray(double[] doubles, ContextProvider contextProvider) {
        for(int i = 0; i < doubles.length; i++) {
            doubles[i] = compute(contextProvider.forIndex(i));
        }
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return this;
    }

    @Override
    public double minValue() {
        return -2;
    }

    @Override
    public double maxValue() {
        return 2;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}

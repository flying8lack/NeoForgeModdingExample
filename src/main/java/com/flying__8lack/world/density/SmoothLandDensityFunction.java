package com.flying__8lack.world.density;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.List;

public class SmoothLandDensityFunction implements DensityFunction {

    public static final KeyDispatchDataCodec<SmoothLandDensityFunction> CODEC = KeyDispatchDataCodec.of(
            MapCodec.unit(SmoothLandDensityFunction::new)
            );

    private static final long seed = 0x4462F89A4L;

    private final PerlinSimplexNoise chunk_noise = new PerlinSimplexNoise(RandomSource.create(seed), List.of(2, 1, 1));

    private final PerlinSimplexNoise erosion = new PerlinSimplexNoise(RandomSource.create(seed), List.of(4,1,2));



    @Override
    public double compute(FunctionContext context) {
        if(context.blockY() < 0){
            return -2;
        }

        if(context.blockY() <= 30){
            return 2;
        }

        int X = Math.floorDiv(context.blockX(), 32);
        int Z = Math.floorDiv(context.blockZ(), 32);

        double avgChunkHeight = chunk_noise.getValue((double) X /128, (double) Z /128, true);
        double locHeight;
        double erosionValue = erosion.getValue((double) context.blockX() /96, (double) context.blockZ() /96, false);
        locHeight = chunk_noise.getValue((double) context.blockX()/64,
                (double) context.blockZ()/64, false);

        avgChunkHeight *= 0.2*(erosionValue) + 1;
        locHeight *= 0.4*(erosionValue) + 0.70;


        double actualHeight = 32 * (0.95*avgChunkHeight + 0.05*locHeight - 0.4*erosionValue) + 60;

        if(context.blockY() <= actualHeight){
            return 2;
        } else {
            return -2;
        }



    }

    @Override
    public void fillArray(double[] doubles, ContextProvider contextProvider) {
        double prev_v = 0;
        for(int i = 0; i < doubles.length; i++) {
            doubles[i] = (prev_v + compute(contextProvider.forIndex(i)))*0.5;
            prev_v = doubles[i];
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

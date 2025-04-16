package com.flying__8lack.world.density;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.DensityFunction;

public class Level0DensityFunction implements DensityFunction {

    private static final int FLOOR = 40;
    private static final int ROOF = 48;
    private static final int ROOM_SIZE = 18;
    public static final KeyDispatchDataCodec<Level0DensityFunction> CODEC = KeyDispatchDataCodec.of(
            MapCodec.unit(Level0DensityFunction::new));


    @Override
    public double compute(FunctionContext context) {
        int y = context.blockY();
        int x = context.blockX();
        int z = context.blockZ();

        if((y <= FLOOR) || (y >= ROOF)){
            return 1;
        } else {

            int roomX = Mth.floorDiv(x, ROOM_SIZE);
            int roomZ = Mth.floorDiv(z, ROOM_SIZE);
            //    public Lazy<PerlinSimplexNoise> noise = Lazy.of( () -> new PerlinSimplexNoise(RandomSource.create(), List.of(1, 2, 4, 8)));;
            int seed = Mth.murmurHash3Mixer(0xF4E5D6 + Mth.murmurHash3Mixer(roomX) + Mth.murmurHash3Mixer(roomZ));



            int roomWidth = ROOM_SIZE + (seed % 5 - 2); //-2 to +2 variations
            int roomLength = ROOM_SIZE + ((seed >> 16) % 5 - 2);
            boolean isWallX = false;
            boolean isWallZ = false;
            if(roomWidth != 0){
                isWallX = (x % roomWidth) == 0;

            }

            if (roomLength != 0) {
                isWallZ = (z % roomLength) == 0;
            }


            if(isWallX || isWallZ){
                if((seed & 0xFF) > 45){ // ~17% chance to be air
                    return 1;
                }

            }

        }


        return -1;
    }

    @Override
    public void fillArray(double[] doubles, ContextProvider context) {
        for(int i = 0; i < doubles.length; i++){
            doubles[i] = compute(context.forIndex(i));
        }
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return this;
    }

    @Override
    public double minValue() {
        return -1;
    }

    @Override
    public double maxValue() {
        return 1;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}

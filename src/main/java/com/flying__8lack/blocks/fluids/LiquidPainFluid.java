package com.flying__8lack.blocks.fluids;

import com.flying__8lack.entity.custom.GrassWalkerMonsterEntity;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;

public abstract class LiquidPainFluid extends BaseFlowingFluid{


    protected LiquidPainFluid(Properties properties) {
        super(properties);
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.LIQUID_PAIN_FLOWING.get();
    }

    @Override
    public Fluid getSource() {
        return ModFluids.LIQUID_PAIN.get();
    }

    @Override
    public float getExplosionResistance(FluidState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return 250.0f;
    }

    @Override
    protected int getSlopeDistance(LevelReader level, BlockPos spreadPos, int distance, Direction p_direction, BlockState currentSpreadState, BlockPos sourcePos, Short2ObjectMap<Pair<BlockState, FluidState>> stateCache, Short2BooleanMap waterHoleCache) {
        return level.getBrightness(LightLayer.SKY, sourcePos) > 8 ? 4 : 6;
    }

    public static class Source extends LiquidPainFluid {

        protected Source(Properties properties) {
            super(properties);
        }




        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }
    }

    public static class Flowing extends LiquidPainFluid {

        protected Flowing(Properties properties) {
            super(properties);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

    }
}

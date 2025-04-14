package com.flying__8lack.blocks.fluids;

import com.flying__8lack.damage.ModDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class LiquidPainBlock extends LiquidBlock {
    public LiquidPainBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }


    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
         Holder.Reference<DamageType> m = level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                 .getOrThrow(ModDamageSource.LIQUID_PAIN);
        entity.hurt(new DamageSource(m,
                null, null, null), 5.0f);

    }
}

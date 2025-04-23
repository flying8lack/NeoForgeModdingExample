package com.flying__8lack.blocks;

import com.flying__8lack.client.hud.BasicHUD;
import com.flying__8lack.damage.ModDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.network.PacketDistributor;

public class BlockLiquidPain extends LiquidBlock {


    public BlockLiquidPain(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);

    }

    private Holder.Reference<DamageType> getDamageSource(Level level){
        return level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                .getOrThrow(ModDamageSource.LIQUID_PAIN);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if(entity instanceof LivingEntity le) {
            entity.hurt(new DamageSource(this.getDamageSource(level),
                    null, null, null), le.getMaxHealth()*0.14f);
            if(le instanceof ServerPlayer p){
                BasicHUD.getInstance().addPower();
                BasicHUD.getInstance().SendData(p);
            }
        } else {
            entity.discard();
        }
    }
}

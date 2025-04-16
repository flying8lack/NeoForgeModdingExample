package com.flying__8lack.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class BlockPowerOre extends Block {
    public BlockPowerOre(Properties properties) {
        super(properties.sound(SoundType.STONE)
                .strength(1.2f, 16)
        );
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 2));
        player.hurt(player.damageSources().wither(), 2);
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}

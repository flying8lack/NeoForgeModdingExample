package com.flying__8lack.entity.brain.behavior;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.phys.Vec3;


public class StalkTarget extends Behavior<Mob> {

    boolean attacked = false;
    public int delay = 10;
    public StalkTarget() {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_PLAYERS, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Mob entity, long gameTime) {
        return !attacked;
    }

    @Override
    protected void stop(ServerLevel level, Mob entity, long gameTime) {
        super.stop(level, entity, gameTime);
        this.attacked = false;
    }

    @Override
    protected void tick(ServerLevel level, Mob owner, long gameTime) {
        super.tick(level, owner, gameTime);
        if(delay > 0){
            delay--;
            return;
        }
        owner.getBrain().getMemory(MemoryModuleType.NEAREST_PLAYERS).ifPresent(m -> {
            Vec3 pv = m.getFirst().getForward().reverse();
            Vec3 mv = owner.getForward();
            double r = pv.dot(mv);
            if(r < Mth.cos((float) Math.toRadians(-45)) &&
                    r > Mth.cos((float) Math.toRadians(45))){
                this.attacked = true;
            }
            if(this.attacked){
                owner.getBrain().setActiveActivityIfPossible(Activity.FIGHT);
                return;
            }
            if (owner.distanceToSqr(m.getFirst()) >= 8) {
                owner.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 30));
                owner.getNavigation().moveTo(m.getFirst(), 0.96f);
            } else {

                owner.setTarget(m.getFirst());
                owner.getBrain().setActiveActivityIfPossible(Activity.FIGHT);

            }
        });

        delay = 10;


    }
}

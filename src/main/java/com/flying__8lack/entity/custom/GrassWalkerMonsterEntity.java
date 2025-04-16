package com.flying__8lack.entity.custom;

import com.flying__8lack.util.PortalHandle;
import com.flying__8lack.world.dimensions.ModDimension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.Tags;

public class GrassWalkerMonsterEntity extends Monster {

    private static final PortalHandle portal_handle = new PortalHandle(ModDimension.INSTABLE_WORLD_LEVEL,
            40);


    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public GrassWalkerMonsterEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, -1.0f);
        this.setPathfindingMalus(PathType.TRAPDOOR, -4.0f);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0f);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, -8.0f);





    }











    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        if( damageSource.is(Tags.DamageTypes.IS_PHYSICAL) &&
                !damageSource.is(DamageTypes.EXPLOSION) &&
                !damageSource.is(DamageTypes.PLAYER_EXPLOSION) &&
                damageSource.isDirect() && recentlyHit) {
            level.addFreshEntity(new ItemEntity(level, this.getX(), this.getY() + 1, this.getZ(),
                    new ItemStack(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE)));
        }
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60d)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 48D)
                .add(Attributes.ATTACK_DAMAGE, 5.8D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.64D)
                .add(Attributes.ATTACK_SPEED, 4.0D)
                .add(Attributes.SAFE_FALL_DISTANCE, 6.5D);
    }





    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2.2, true));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));



        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false)
                .setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, Zombie.class));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllager.class,
                true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Villager.class,
                true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Sheep.class,
                true));



    }




    @Override
    public void aiStep() {
        super.aiStep();

        if((this.getTarget() != null)) {
            this.idleAnimationState.stop();
        } else {
            if (idleAnimationTimeout <= 0) {
                idleAnimationTimeout = 42;
                idleAnimationState.start(this.tickCount);
            } else {
                idleAnimationTimeout--;
            }
        }



    }





}

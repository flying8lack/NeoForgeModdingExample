package com.flying__8lack.effects;

import com.flying__8lack.util.PortalHandle;
import com.flying__8lack.world.dimensions.ModDimension;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

import static com.flying__8lack.advancedmovementmod.MODID;

public class UnstableEffect extends MobEffect{

    private static final Consumer<Player> PLAY_SOUND = p -> p.level().playSound(p,
            p.getOnPos().below().relative(Direction.EAST,2),
            SoundEvent.createVariableRangeEvent(SoundEvents.ZOMBIE_STEP.getLocation()),
            SoundSource.MASTER);

    private int tickDuration = 0;
    private PortalHandle portal = new PortalHandle(ModDimension.INSTABLE_WORLD_LEVEL, 40);

    protected UnstableEffect(MobEffectCategory category, int color) {
        super(category, color);

        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,
                ResourceLocation.fromNamespaceAndPath(MODID, "unstable"), -0.6,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player p) {
            PLAY_SOUND.accept(p);
            this.portal.teleportAsAPortal(p);
        }

    }


    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {

        if (entity instanceof Player p) {



            if(this.tickDuration % 100 == 0){
                PLAY_SOUND.accept(p);

            }

            if(this.tickDuration >= 40 && p.level().dimension() == ModDimension.INSTABLE_WORLD_LEVEL){
                this.portal.teleportAsAPortal(p);
            }



        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        this.tickDuration = duration;
        return true;
    }
}

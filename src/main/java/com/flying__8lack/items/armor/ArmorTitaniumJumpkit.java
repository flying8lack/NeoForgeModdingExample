package com.flying__8lack.items.armor;

import com.flying__8lack.advancedmovementmod;
import com.flying__8lack.client.KeyResponse;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.function.Consumer;
import java.util.function.Function;

public class ArmorTitaniumJumpkit extends ArmorItem {

    private int wall_run_time = 0;
    private boolean is_wall_run = false;
    private boolean left_wall = true;
    public ArmorTitaniumJumpkit(Properties properties) {
        super(ModArmorMaterial.TITANIUM_MATERIAL, Type.LEGGINGS, properties);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }



    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

            KeyResponse.pass();
            if (entity instanceof Player p) {



                if (p.getInventory().armor.get(1).is(this)) {

                    if(KeyResponse.isJumpkitEnabled()) {
                        p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30, 2));
                        p.addEffect(new MobEffectInstance(MobEffects.JUMP, 30, 4));

                        if (p.horizontalCollision) {

                            this.Collided(level, p);


                        } else {
                            if(this.is_wall_run) {
                                this.wall_jump(p);
                                this.is_wall_run = false;
                                this.wall_run_time = 0;
                            }
                        }
                        if (KeyResponse.isFLYING_BTN()) {
                            p.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 3));

                        }


                    }

                    p.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 30, 1));

                }
            }

    }

    private void wall_jump(Player player){
        Vec3 Face = player.getForward();
        Vec3 jump_dir;
        if(!this.left_wall){
            jump_dir = Face.yRot((float) (0.5 * Math.PI)).add(player.getEyePosition());
        } else {
            jump_dir = Face.yRot((float) -(0.5 * Math.PI)).add(player.getEyePosition());
        }

        player.setDeltaMovement(jump_dir.normalize().scale(2 + ((double) this.wall_run_time /20)));

    }

    public void Collided(Level level, Player player){

        Vec3 Face = player.getForward();
        Vec3 left = Face.yRot((float) (0.5 * Math.PI)).add(player.getEyePosition());
        Vec3 right = Face.yRot((float) -(0.5 * Math.PI)).add(player.getEyePosition());
        var bl = new BlockPos((int) left.x(), (int) left.y(), (int) left.z());
        var br = new BlockPos((int) right.x(), (int) right.y(), (int) right.z());
        var boundbox = player.getBoundingBox().inflate(0.5, 0, 0.5);
        if(boundbox.intersects(new AABB(bl))){
            this.left_wall = true;
            this.wall_run_time += 1;
            this.is_wall_run = true;
        }

        if(boundbox.intersects(new AABB(br))){
            this.left_wall = false;
            this.wall_run_time += 1;
            this.is_wall_run = true;
        }
        if(this.is_wall_run){

            player.setDeltaMovement(player.getDeltaMovement().multiply(1, 0.01, 1)
                    .normalize().scale(2));
        }


    }


}

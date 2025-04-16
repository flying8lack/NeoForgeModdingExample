package com.flying__8lack.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class PortalHandle implements Portal {

    private final ResourceKey<Level> world;
    private final int minimum_spawn_height;

    public PortalHandle(ResourceKey<Level> world, int minimum_spawn_height){
        this.world = world;
        this.minimum_spawn_height = minimum_spawn_height;

    }

    private BlockPos findSpawnLocation(ServerLevel level, BlockPos pos){
        for(int y = this.minimum_spawn_height; y < 128; y++){
            if(level.getBlockState(pos.atY(y)).isAir()){
                return new BlockPos(pos.getX(),
                        y+2,
                        pos.getZ());
            }
        }

        return new BlockPos(pos.getX(),
                130,
                pos.getZ());

    }

    public void teleport(@NotNull Entity player){

        player.teleportTo(player.getServer().getLevel(this.world),
                player.getBlockX(),
                player.getBlockY(),
                player.getBlockZ(),
                Set.of(RelativeMovement.X_ROT, RelativeMovement.Y_ROT),
                player.getYRot(),
                player.getXRot());
    }

    public void teleportAsAPortal(Entity player){

        player.setAsInsidePortal(this, player.getOnPos());
    }


    @Override
    public @Nullable DimensionTransition getPortalDestination(ServerLevel serverLevel, Entity entity, BlockPos blockPos) {
        ResourceKey<Level> resourcekey = serverLevel.dimension() == this.world ?
                Level.OVERWORLD : this.world;
        ServerLevel serverlevel = serverLevel.getServer().getLevel(resourcekey);
        if (serverlevel == null) {
            return null;
        } else {
            boolean flag = resourcekey == this.world;
            BlockPos blockpos = flag ?
                    this.findSpawnLocation(serverlevel,blockPos) : serverlevel.getSharedSpawnPos();
            Vec3 vec3 = blockpos.getBottomCenter();
            if (flag) {

                //EndPlatformFeature.createEndPlatform(serverlevel, BlockPos.containing(vec3).below(), true);
                if (entity instanceof ServerPlayer) {
                    vec3 = vec3.subtract(0.0, 1.0, 0.0);
                }
            } else {
                if (entity instanceof ServerPlayer) {
                    ServerPlayer serverplayer = (ServerPlayer)entity;
                    return serverplayer.findRespawnPositionAndUseSpawnBlock(false, DimensionTransition.DO_NOTHING);
                }

                vec3 = entity.adjustSpawnLocation(serverlevel, blockpos).getBottomCenter();
            }

            return new DimensionTransition(serverlevel, vec3, entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND);
        }
    }
}

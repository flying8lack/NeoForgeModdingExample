package com.flying__8lack.items;

import com.flying__8lack.advancedmovementmod;
import com.flying__8lack.world.dimensions.ModDimension;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PortalProcessor;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.levelgen.feature.EndPlatformFeature;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeConfig;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.jetbrains.annotations.Nullable;

public class ItemUnstableCreative extends Item implements Portal {

    private static PortalProcessor portal;
    public ItemUnstableCreative(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(!context.getLevel().isClientSide()) {
            context.getPlayer().setAsInsidePortal(this, context.getPlayer().getOnPos());
        }
        return super.useOn(context);
    }

    @Override
    public @Nullable DimensionTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos blockPos) {
        ResourceKey<Level> resourcekey = level.dimension() != Level.OVERWORLD ?
                ModDimension.INSTABLE_WORLD_LEVEL : Level.OVERWORLD;
        ServerLevel serverlevel = level.getServer().getLevel(resourcekey);
        if (serverlevel == null) {
            advancedmovementmod.getLog().error("Server is null");
            return null;
        } else {
            BlockPos blockpos = serverlevel.getSharedSpawnPos();
            Vec3 vec3;// = blockpos.getBottomCenter();
            float f = entity.getYRot();

                if (entity instanceof ServerPlayer) {
                    ServerPlayer serverplayer = (ServerPlayer)entity;
                    return serverplayer.findRespawnPositionAndUseSpawnBlock(false, DimensionTransition.DO_NOTHING);
                }

                vec3 = entity.adjustSpawnLocation(serverlevel, blockpos).getBottomCenter();


            return new DimensionTransition(serverlevel, vec3, entity.getDeltaMovement(), f, entity.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET));
        }
    }




}

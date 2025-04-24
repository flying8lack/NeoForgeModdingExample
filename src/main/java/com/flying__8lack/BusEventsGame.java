package com.flying__8lack;

import com.flying__8lack.client.hud.BasicHUD;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

import static com.flying__8lack.advancedmovementmod.MODID;
import static com.flying__8lack.advancedmovementmod.getLog;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class BusEventsGame {

    @SubscribeEvent
    private static void onPlayerJoin(EntityJoinLevelEvent event){
        if(!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer p) {
                getLog().debug("PLAYER JOINED: {}", p.getUUID());
            }

        }

    }
}

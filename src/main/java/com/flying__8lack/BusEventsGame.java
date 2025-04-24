package com.flying__8lack;

import com.flying__8lack.attachment.ModAttachment;
import com.flying__8lack.blocks.BlockPipe;
import com.flying__8lack.network.NetworkManager;
import com.flying__8lack.network.PipeNetwork;
import com.flying__8lack.network.PlayerData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.flying__8lack.advancedmovementmod.MODID;
import static com.flying__8lack.advancedmovementmod.getLog;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class BusEventsGame {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event){
        if(event.getState().getBlock() instanceof BlockPipe b){
            BlockPos pos = event.getPos();
            PipeNetwork net = NetworkManager.getInstance().networks().get(pos);
            if(net != null){
                net.removeFromNetwork(pos, (Level) event.getLevel());
            }
        }
    }

    @SubscribeEvent
    private static void onPlayerJoin(EntityJoinLevelEvent event){
        if(!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer p) {
                getLog().debug("PLAYER JOINED: {}", p.getUUID());
                //BasicHUD.PowerList.putIfAbsent(p.getGameProfile().getName(), p.getData(ModAttachment.PLAYER_POWER));

                PacketDistributor.sendToPlayer(p, new PlayerData(p.getData(ModAttachment.PLAYER_POWER)));
            }

        }

    }
}

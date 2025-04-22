package com.flying__8lack;

import com.flying__8lack.blocks.BlockPipe;
import com.flying__8lack.network.NetworkManager;
import com.flying__8lack.network.PipeNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import static com.flying__8lack.advancedmovementmod.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class GameEvents {
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
}

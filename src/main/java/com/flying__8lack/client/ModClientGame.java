package com.flying__8lack.client;

import com.flying__8lack.client.hud.BasicHUD;
import net.minecraft.client.telemetry.events.WorldLoadEvent;
import net.minecraft.util.profiling.jfr.event.WorldLoadFinishedEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static com.flying__8lack.advancedmovementmod.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModClientGame {


    //This event will render gui after the HUD was rendered (post)
    @SubscribeEvent
    public static void RenderGUIEventPost(RenderGuiEvent.Post event){
        BasicHUD.getInstance().render(event.getGuiGraphics());


    }
}

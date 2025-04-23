package com.flying__8lack.client;

import com.flying__8lack.client.hud.BasicHUD;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import static com.flying__8lack.advancedmovementmod.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModClientGame {
    @SubscribeEvent
    public static void ClientSetUp(RenderGuiEvent.Post event){
        BasicHUD.getInstance().render(event.getGuiGraphics(), 60, 60, 0);


    }
}

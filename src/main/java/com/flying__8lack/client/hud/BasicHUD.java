package com.flying__8lack.client.hud;

import com.flying__8lack.network.PlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class BasicHUD{

    private static final BasicHUD instance = new BasicHUD();
    private static int power = 0;


    //this get called on the server side only for consistency
    public void addPower(){
        power += 1;
    }

    //send a custom packet to sync the power data between logical server and logical client
    public void SendData(ServerPlayer p){
        PacketDistributor.sendToPlayer(p, new PlayerData(power));
    }

    public static BasicHUD getInstance() {
        return instance;
    }

    //method to handle server data
    public static void handleServerData(final PlayerData data, final IPayloadContext context){
        power = data.power();
    }

    public static void handleClientData(final PlayerData data, final IPayloadContext context){

    }

    //the method that get called to render the gui
    public void render(GuiGraphics guiGraphics) {
        //screen size is 256 in X axis
        //screen size is 256 in Y axis
        guiGraphics.drawString(Minecraft.getInstance().font, "Energy: %d".formatted(power), 64, 64, 0x8C8069);

    }
}

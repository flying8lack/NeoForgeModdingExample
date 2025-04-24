package com.flying__8lack.client.hud;

import com.flying__8lack.attachment.ModAttachment;
import com.flying__8lack.network.PlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;

public class BasicHUD{

    private static final BasicHUD instance = new BasicHUD();
    private static int power = -1;
    public static HashMap<String, Integer> PowerList = new HashMap<>();

    //this get called on the server side only for consistency
    public void addPower(ServerPlayer p){
        power = p.getData(ModAttachment.PLAYER_POWER) + 1;
        p.setData(ModAttachment.PLAYER_POWER, power);
    }

    //send a custom packet to sync the power data between logical server and logical client
    public void SendData(ServerPlayer p){
        PacketDistributor.sendToPlayer(p, new PlayerData(p.getData(ModAttachment.PLAYER_POWER)));
    }

    public static void RequestData(){
        PacketDistributor.sendToServer(new PlayerData(0));
    }

    public static BasicHUD getInstance() {
        return instance;
    }

    //method to handle data from server
    public static void handleServerData(final PlayerData data, final IPayloadContext context){
        power = data.power();

    }

    public static void handleClientData(final PlayerData data, final IPayloadContext context){
        context.enqueueWork(
                () -> {
                    context.reply(new PlayerData(-7274));
                }
        );
    }

    //the method that get called to render the gui (client only)
    public void render(GuiGraphics guiGraphics) {
        //screen size is 512 in X axis
        //screen size is 512 in Y axis
        guiGraphics.drawString(Minecraft.getInstance().font, "Power: %d".formatted(power), 64, 64, 0x8C8069);


    }
}

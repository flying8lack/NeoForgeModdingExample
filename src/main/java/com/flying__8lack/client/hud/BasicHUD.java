package com.flying__8lack.client.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class BasicHUD{

    private static final BasicHUD instance = new BasicHUD();


    public static BasicHUD getInstance() {
        return instance;
    }


    public void render(GuiGraphics guiGraphics) {
        //screen size is 256 in X axis
        //screen size is 256 in Y axis
        guiGraphics.drawString(Minecraft.getInstance().font, "Hello World", 64, 64, 0x8C8069);

    }
}

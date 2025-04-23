package com.flying__8lack.client.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Overlay;

public class BasicHUD extends Overlay{

    private static final BasicHUD instance = new BasicHUD();

    public static BasicHUD getInstance() {
        return instance;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int i1, float v) {
        guiGraphics.drawString(Minecraft.getInstance().font, "Hello World", 60, 60, 0xFF8000);

    }
}

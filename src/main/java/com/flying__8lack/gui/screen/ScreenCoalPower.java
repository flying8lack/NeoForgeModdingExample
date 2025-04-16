package com.flying__8lack.gui.screen;

import com.flying__8lack.gui.menus.MenuCoalPower;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ScreenCoalPower extends AbstractContainerScreen<MenuCoalPower> {

    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            MODID, "textures/screen/coal_power_screen.png"
    );

    private static final int MIN_Y = 57;
    private static final int MIN_X = 57;

    public ScreenCoalPower(MenuCoalPower menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        if(this.menu.is_active()) {
            guiGraphics.fill(MIN_X + x, MIN_Y + y,
                    (int) ((MIN_X + x) + (62 * (this.menu.get_time() * 0.1 / 25))), MIN_Y + 6 + y, 0xFFFF002F);
        }

        guiGraphics.drawString(this.font,
                String.format("Stored Energy: %d FE", this.menu.get_energy()),
                x+8,
                y+16,
                0x8000AA,
                false);


    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.renderTooltip(guiGraphics, mouseX, mouseY);

    }
}

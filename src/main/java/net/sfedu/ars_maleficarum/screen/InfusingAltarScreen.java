package net.sfedu.ars_maleficarum.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.sfedu.ars_maleficarum.ArsMaleficarum;

public class InfusingAltarScreen extends AbstractContainerScreen<InfusingAltarMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/gui/infusing_altar_gui.png");

    public InfusingAltarScreen(InfusingAltarMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelX = 100000;
        this.titleLabelX=100000;
        this.imageHeight+=16;
        this.height+=16;

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width-imageWidth)/2;
        int y = (height - imageHeight)/2;

        guiGraphics.blit(TEXTURE,x,y,0,0,imageWidth,imageHeight);

        RenderProgressArrow(guiGraphics,x,y);
    }

    private void RenderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE,x+86,y+31,176,0,8,menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics GuiGraphics, int MouseX, int MouseY, float delta) {
        renderBackground(GuiGraphics);
        super.render(GuiGraphics,MouseX,MouseY, delta);
        renderTooltip(GuiGraphics,MouseX,MouseY);
    }
}

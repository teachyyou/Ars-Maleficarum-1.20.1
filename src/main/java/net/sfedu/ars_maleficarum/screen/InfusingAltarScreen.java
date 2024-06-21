package net.sfedu.ars_maleficarum.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.sfedu.ars_maleficarum.ArsMaleficarum;

import javax.annotation.ParametersAreNonnullByDefault;

public class InfusingAltarScreen extends AbstractContainerScreen<InfusingAltarMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/gui/infusing_altar_gui.png");

    public InfusingAltarScreen(InfusingAltarMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        this.imageHeight=182;
        this.height+=16;
        super.init();
        this.inventoryLabelX = 100000;
        this.titleLabelX=100000;

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
        int k = menu.getScaledProgress();
        guiGraphics.blit(TEXTURE,x+45,y+21,0,183,100,k);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(GuiGraphics GuiGraphics, int MouseX, int MouseY, float delta) {
        renderBackground(GuiGraphics);
        super.render(GuiGraphics,MouseX,MouseY, delta);
        renderTooltip(GuiGraphics,MouseX,MouseY);
    }
}

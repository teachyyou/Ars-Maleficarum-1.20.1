package net.sfedu.ars_maleficarum.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.sfedu.ars_maleficarum.ArsMaleficarum;

public class OdourExtractorFurnaceScreen extends AbstractContainerScreen<OdourExtractorFurnaceMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ArsMaleficarum.MOD_ID, "textures/gui/odour_extracting_furnace_gui.png");
    public OdourExtractorFurnaceScreen(OdourExtractorFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelX = 100000;
        this.titleLabelX+=10;
        this.titleLabelY-=1;

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
        int i = menu.getLitScaledProgress();
        //pGuiGraphics.blit(this.texture, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        guiGraphics.blit(TEXTURE,x+56,y+36+12-i,176,12-i,14,i+1);
        guiGraphics.blit(TEXTURE,x+79,y+20,176,14,k,17);
    }

    /*private void RenderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        //int k = this.menu.getScaledProgress();
        int i = menu.getScaledProgress();
        //pGuiGraphics.blit(this.texture, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE,x+56,y+36+12-i,176,12-i,14,i+1);
        }
    }*/
    @Override
    public void render(GuiGraphics GuiGraphics, int MouseX, int MouseY, float delta) {
        renderBackground(GuiGraphics);
        super.render(GuiGraphics,MouseX,MouseY, delta);
        renderTooltip(GuiGraphics,MouseX,MouseY);
    }
}

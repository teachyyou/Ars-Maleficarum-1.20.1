package net.sfedu.ars_maleficarum.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.entity.custom.TraderWitchEntity;
import net.sfedu.ars_maleficarum.entity.layers.ModModelLayers;

public class TraderWitchRender extends MobRenderer<TraderWitchEntity, TraderWitchModel<TraderWitchEntity>> {
    private static final ResourceLocation TRADER_WITCH_LOCATION = new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/entity/trader_witch_texture.png");
    public TraderWitchRender(EntityRendererProvider.Context pContext) {
        super(pContext, new TraderWitchModel<>(pContext.bakeLayer(ModModelLayers.TRADER_WITCH_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(TraderWitchEntity pEntity) {
        return TRADER_WITCH_LOCATION;
    }
}

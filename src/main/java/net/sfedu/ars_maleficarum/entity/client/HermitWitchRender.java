package net.sfedu.ars_maleficarum.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.entity.custom.HermitWitchEntity;
import net.sfedu.ars_maleficarum.entity.layers.ModModelLayers;

public class HermitWitchRender extends MobRenderer<HermitWitchEntity, HermitWitchModel<HermitWitchEntity>> {
    private static final ResourceLocation HERMIT_WITCH_LOCATION = new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/entity/hermit_witch_texture.png");
    public HermitWitchRender(EntityRendererProvider.Context pContext) {
        super(pContext, new HermitWitchModel<>(pContext.bakeLayer(ModModelLayers.HERMIT_WITCH_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(HermitWitchEntity pEntity) {
        return HERMIT_WITCH_LOCATION;
    }
}

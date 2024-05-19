package net.sfedu.ars_maleficarum.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;
import net.sfedu.ars_maleficarum.entity.layers.ModModelLayers;

public class MandrakeRender extends MobRenderer<MandrakeEntity,MandrakeModel<MandrakeEntity>> {
    private static final ResourceLocation MANDRAKE_LOCATION = new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/entity/mandrake.png");
    public MandrakeRender(EntityRendererProvider.Context pContext) {
        super(pContext, new MandrakeModel<>(pContext.bakeLayer(ModModelLayers.MANDRAKE_LAYER)), 0.2f);
    }
    @Override
    public ResourceLocation getTextureLocation(MandrakeEntity pEntity) {
        return MANDRAKE_LOCATION;
    }
}

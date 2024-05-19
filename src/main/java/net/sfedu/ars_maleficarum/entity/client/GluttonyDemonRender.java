package net.sfedu.ars_maleficarum.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.entity.custom.GluttonyDemonEntity;
import net.sfedu.ars_maleficarum.entity.layers.ModModelLayers;

public class GluttonyDemonRender extends MobRenderer<GluttonyDemonEntity,GluttonyDemonModel<GluttonyDemonEntity>> {
    private static final ResourceLocation GLUTTONY_DEMON_LOCATION = new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/entity/gluttony_demon_texture.png");
    public GluttonyDemonRender(EntityRendererProvider.Context pContext) {
        super(pContext, new GluttonyDemonModel<>(pContext.bakeLayer(ModModelLayers.GLUTTONY_DEMON_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(GluttonyDemonEntity pEntity) {
        return GLUTTONY_DEMON_LOCATION;
    }
}

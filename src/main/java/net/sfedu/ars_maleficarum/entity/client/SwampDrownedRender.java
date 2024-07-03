package net.sfedu.ars_maleficarum.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;
import net.sfedu.ars_maleficarum.entity.custom.SwampDrownedEntity;
import net.sfedu.ars_maleficarum.entity.layers.ModModelLayers;

public class SwampDrownedRender extends MobRenderer<SwampDrownedEntity, SwampDrownedModel<SwampDrownedEntity>> {
    private static final ResourceLocation SWAMP_DROWNED_LOCATION = new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/entity/swamp_drowned_texture.png");
    public SwampDrownedRender(EntityRendererProvider.Context pContext) {
        super(pContext, new SwampDrownedModel<>(pContext.bakeLayer(ModModelLayers.SWAMP_DROWNED_LAYER)), 1f);
    }
    @Override
    public ResourceLocation getTextureLocation(SwampDrownedEntity pEntity) {
        return SWAMP_DROWNED_LOCATION;
    }

}

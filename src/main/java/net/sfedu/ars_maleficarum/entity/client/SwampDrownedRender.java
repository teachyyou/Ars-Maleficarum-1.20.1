package net.sfedu.ars_maleficarum.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;
import net.sfedu.ars_maleficarum.entity.custom.SwampDrownedEntity;
import net.sfedu.ars_maleficarum.entity.layers.ModModelLayers;
import net.sfedu.ars_maleficarum.entity.variant.SwampDrownedVariants;

import java.util.Map;

public class SwampDrownedRender extends MobRenderer<SwampDrownedEntity, SwampDrownedModel<SwampDrownedEntity>> {
    private static final Map<SwampDrownedVariants, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(SwampDrownedVariants.class), map -> {
        map.put(SwampDrownedVariants.EVIL, new ResourceLocation(ArsMaleficarum.MOD_ID, "textures/entity/swamp_drowned_evil_texture.png"));
        map.put(SwampDrownedVariants.PEACEFUL, new ResourceLocation(ArsMaleficarum.MOD_ID, "textures/entity/swamp_drowned_peaceful_texture.png"));
    });
    public SwampDrownedRender(EntityRendererProvider.Context pContext) {
        super(pContext, new SwampDrownedModel<>(pContext.bakeLayer(ModModelLayers.SWAMP_DROWNED_LAYER)), 1f);
    }
    @Override
    public ResourceLocation getTextureLocation(SwampDrownedEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
    }

}

package net.sfedu.ars_maleficarum.entity.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.entity.custom.PoisonousEssenceEntity;
import net.sfedu.ars_maleficarum.entity.layers.ModModelLayers;

public class PoisonousEssenceRender extends EntityRenderer<PoisonousEssenceEntity> {
    private static final ResourceLocation POISONOUS_ESSENCE_LOCATION = new ResourceLocation(ArsMaleficarum.MOD_ID, "textures/entity/poisonous_essence_texture.png");
    private final PoisonousEssenceModel<PoisonousEssenceEntity> model;
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(POISONOUS_ESSENCE_LOCATION);
    public PoisonousEssenceRender(EntityRendererProvider.Context pContext) {

        super(pContext);
        this.model = new PoisonousEssenceModel<>(pContext.bakeLayer(ModModelLayers.POISONOUS_ESSENCE_LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(PoisonousEssenceEntity pEntity) {
        return POISONOUS_ESSENCE_LOCATION;
    }

}

package net.sfedu.ars_maleficarum.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.entity.custom.FireEssenceEntity;
import net.sfedu.ars_maleficarum.entity.custom.PoisonousEssenceEntity;
import net.sfedu.ars_maleficarum.entity.layers.ModModelLayers;

public class FireEssenceRender extends EntityRenderer<FireEssenceEntity> {
    private static final ResourceLocation FIRE_ESSENCE_LOCATION = new ResourceLocation(ArsMaleficarum.MOD_ID, "textures/entity/fire_essence_texture.png");
    protected FireEssenceModel model;
    public FireEssenceRender(EntityRendererProvider.Context pContext) {

        super(pContext);
        model = new FireEssenceModel(pContext.bakeLayer(ModModelLayers.FIRE_ESSENCE_LAYER));

    }

    public void render(FireEssenceEntity entity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTick, entity.xRotO, entity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.model.renderType(this.getTextureLocation(entity)), false, false);

        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 0f, 1f, 0f, 1f);
        pPoseStack.popPose();
        super.render(entity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
    @Override
    public ResourceLocation getTextureLocation(FireEssenceEntity pEntity) {
        return FIRE_ESSENCE_LOCATION;
    }
}

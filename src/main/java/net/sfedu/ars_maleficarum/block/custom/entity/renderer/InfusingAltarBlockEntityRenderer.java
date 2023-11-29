package net.sfedu.ars_maleficarum.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.sfedu.ars_maleficarum.block.custom.InfusingAltarBlock;
import net.sfedu.ars_maleficarum.block.custom.entity.InfusingAltarBlockEntity;

import java.util.List;

public class InfusingAltarBlockEntityRenderer implements BlockEntityRenderer<InfusingAltarBlockEntity> {

    public InfusingAltarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(InfusingAltarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
                       int pPackedLight, int pPackedOverlay) {


        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        //List<ItemStack> items = List.of(ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY, ItemStack.EMPTY,ItemStack.EMPTY);
        List<ItemStack> items = pBlockEntity.getRenderStack();
        pPoseStack.pushPose();
        pPoseStack.translate(0.25f,0.575f,0.75f);
        pPoseStack.scale(0.3f,0.3f,0.3f);
        pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(InfusingAltarBlock.FACING).toYRot()));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(items.get(0), ItemDisplayContext.FIXED,getLightLevel(pBlockEntity.getLevel(),
                pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,pPoseStack,pBuffer,pBlockEntity.getLevel(),1);
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(0.25f,0.575f,0.25f);
        pPoseStack.scale(0.3f,0.3f,0.3f);
        pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(InfusingAltarBlock.FACING).toYRot()));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(items.get(1), ItemDisplayContext.FIXED,getLightLevel(pBlockEntity.getLevel(),
                pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,pPoseStack,pBuffer,pBlockEntity.getLevel(),1);
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(0.65f,0.575f,0.225f);
        pPoseStack.scale(0.3f,0.3f,0.3f);
        pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(InfusingAltarBlock.FACING).toYRot()));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(items.get(2), ItemDisplayContext.FIXED,getLightLevel(pBlockEntity.getLevel(),
                pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,pPoseStack,pBuffer,pBlockEntity.getLevel(),1);
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(0.85f,0.575f,0.5f);
        pPoseStack.scale(0.3f,0.3f,0.3f);
        pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(InfusingAltarBlock.FACING).toYRot()));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(items.get(3), ItemDisplayContext.FIXED,getLightLevel(pBlockEntity.getLevel(),
                pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,pPoseStack,pBuffer,pBlockEntity.getLevel(),1);
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(0.65f,0.575f,0.775f);
        pPoseStack.scale(0.3f,0.3f,0.3f);
        pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(InfusingAltarBlock.FACING).toYRot()));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(items.get(4), ItemDisplayContext.FIXED,getLightLevel(pBlockEntity.getLevel(),
                pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,pPoseStack,pBuffer,pBlockEntity.getLevel(),1);
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(0.5f,0.575f,0.5f);
        pPoseStack.scale(0.3f,0.3f,0.3f);
        pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(InfusingAltarBlock.FACING).toYRot()));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(items.get(5), ItemDisplayContext.FIXED,getLightLevel(pBlockEntity.getLevel(),
                pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,pPoseStack,pBuffer,pBlockEntity.getLevel(),1);
        pPoseStack.popPose();


    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK,pos);
        int sLight = level.getBrightness(LightLayer.SKY,pos);
        return LightTexture.pack(bLight,sLight);
    }
}

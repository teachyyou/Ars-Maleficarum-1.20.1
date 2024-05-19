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
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.sfedu.ars_maleficarum.block.custom.InfusingAltarBlock;
import net.sfedu.ars_maleficarum.block.custom.entity.InfusingAltarBlockEntity;

import java.util.List;

public class InfusingAltarBlockEntityRenderer implements BlockEntityRenderer<InfusingAltarBlockEntity> {

    public InfusingAltarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(InfusingAltarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
                       int pPackedLight, int pPackedOverlay) {

        Direction d = pBlockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        List<ItemStack> items = pBlockEntity.getRenderStack();
        List<Float> xCords = List.of(0.25f,0.25f,0.65f,0.85f,0.65f,0.5f);
        List<Float> zCords = List.of(0.75f,0.25f,0.225f,0.5f,0.775f,0.5f);
        if (d==Direction.EAST || d==Direction.WEST) {
            for (int i = 0; i < 6; i++) {
                float x = d==Direction.WEST ? (1-xCords.get(i)) : (xCords.get(i));
                float z = d==Direction.WEST ? (1-zCords.get(i)) : (zCords.get(i));
                pPoseStack.pushPose();
                pPoseStack.translate(x,0.575f,z);
                pPoseStack.scale(0.3f,0.3f,0.3f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(InfusingAltarBlock.FACING).toYRot()));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

                itemRenderer.renderStatic(items.get(i), ItemDisplayContext.FIXED,getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,pPoseStack,pBuffer,pBlockEntity.getLevel(),1);
                pPoseStack.popPose();
            }
        }
        else if (d==Direction.NORTH || d== Direction.SOUTH) {
            for (int i = 0; i < 6; i++) {
        /*z*/   float z = d==Direction.SOUTH ? (xCords.get(i)) : (1-xCords.get(i));
        /*x*/   float x = d==Direction.SOUTH ? (1-zCords.get(i)) : (zCords.get(i));
                pPoseStack.pushPose();
                pPoseStack.translate(x,0.575f,z);
                pPoseStack.scale(0.3f,0.3f,0.3f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(InfusingAltarBlock.FACING).toYRot()));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

                itemRenderer.renderStatic(items.get(i), ItemDisplayContext.FIXED,getLightLevel(pBlockEntity.getLevel(),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,pPoseStack,pBuffer,pBlockEntity.getLevel(),1);
                pPoseStack.popPose();
            }
        }



    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK,pos);
        int sLight = level.getBrightness(LightLayer.SKY,pos);
        return LightTexture.pack(bLight,sLight);
    }
}

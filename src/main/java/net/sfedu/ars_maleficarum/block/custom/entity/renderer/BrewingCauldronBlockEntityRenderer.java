package net.sfedu.ars_maleficarum.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.sfedu.ars_maleficarum.block.custom.BrewingCauldronBlock;
import net.sfedu.ars_maleficarum.block.custom.InfusingAltarBlock;
import net.sfedu.ars_maleficarum.block.custom.entity.BrewingCauldronBlockEntity;

public class BrewingCauldronBlockEntityRenderer implements BlockEntityRenderer<BrewingCauldronBlockEntity> {

    public BrewingCauldronBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }
    @Override
    public void render(BrewingCauldronBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Level level = pBlockEntity.getLevel();
        if (level == null) return;
        if (pBlockEntity.getBlockState().getValue(BrewingCauldronBlock.WATER) == 0) return;
        FluidStack fluidStack = new FluidStack(Fluids.WATER, 1);
        BlockPos pos = pBlockEntity.getBlockPos();
        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);

        FluidState state = fluidStack.getFluid().defaultFluidState();

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = fluidTypeExtensions.getTintColor(state, level, pos);

        VertexConsumer builder = pBuffer.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

        float height = 0.188f+0.125f*pBlockEntity.getBlockState().getValue(BrewingCauldronBlock.WATER);
        drawQuad(builder, pPoseStack, 0.125f, height, 0.125f, 0.875f, height, 0.875f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
    }

    private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color)
    {
        builder.vertex(poseStack.last().pose(), x, y, z)
                .color(color)
                .uv(u, v)
                .uv2(packedLight)
                .normal(1,0,0)
                .endVertex();
    }
    private static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
    }
}

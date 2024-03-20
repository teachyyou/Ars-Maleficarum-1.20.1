package net.sfedu.ars_maleficarum.block.custom.entity.renderer.rendertypes;


import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.RenderType;

public class CustomRenderTypes extends RenderType {
    public CustomRenderTypes(String name, VertexFormat format, Mode mode, int bufferSize, boolean affectsCrumbling, boolean sort, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sort, setupState, clearState);
    }

    public static final RenderType CAULDRON_WATER = RenderType.create(
            "cauldron_water", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true,
            CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setCullState(NO_CULL)
                    .createCompositeState(false));
}
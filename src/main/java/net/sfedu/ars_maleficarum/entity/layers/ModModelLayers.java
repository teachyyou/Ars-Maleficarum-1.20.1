package net.sfedu.ars_maleficarum.entity.layers;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.sfedu.ars_maleficarum.ArsMaleficarum;

public class ModModelLayers {
    public static final ModelLayerLocation MANDRAKE_LAYER = new ModelLayerLocation(
            new ResourceLocation(ArsMaleficarum.MOD_ID, "mandrake_layer"), "mandrake_layer");
    public static final ModelLayerLocation GLUTTONY_DEMON_LAYER = new ModelLayerLocation(
            new ResourceLocation(ArsMaleficarum.MOD_ID, "gluttony_demon_layer"), "gluttony_demon_layer");
    public static final ModelLayerLocation POISONOUS_ESSENCE_LAYER = new ModelLayerLocation(
            new ResourceLocation(ArsMaleficarum.MOD_ID, "poisonous_essence_layer"), "poisonous_essence_layer");
    public static final ModelLayerLocation FIRE_ESSENCE_LAYER = new ModelLayerLocation(
            new ResourceLocation(ArsMaleficarum.MOD_ID, "fire_essence_layer"), "fire_essence_layer");
}

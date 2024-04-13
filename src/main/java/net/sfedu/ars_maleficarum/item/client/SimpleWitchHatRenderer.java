package net.sfedu.ars_maleficarum.item.client;

import net.minecraft.resources.ResourceLocation;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.item.custom.clothes.SimpleWitchHat;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SimpleWitchHatRenderer extends GeoArmorRenderer<SimpleWitchHat> {
    public SimpleWitchHatRenderer() {
        //super(new SimpleWitchHatModel());
        super(new DefaultedItemGeoModel<>(new ResourceLocation(ArsMaleficarum.MOD_ID, "clothes/simple_witch_hat")));
    }
}

package net.sfedu.ars_maleficarum.event;

import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.client.MandrakeModel;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;
import net.sfedu.ars_maleficarum.entity.layers.ModModelLayers;

@Mod.EventBusSubscriber(modid = ArsMaleficarum.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.MANDRAKE_LAYER, MandrakeModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MANDRAKE.get(), MandrakeEntity.createAttributes().build());
    }
}

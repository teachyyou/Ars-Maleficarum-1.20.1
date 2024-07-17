package net.sfedu.ars_maleficarum.event;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.client.*;
import net.sfedu.ars_maleficarum.entity.custom.GluttonyDemonEntity;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;
import net.sfedu.ars_maleficarum.entity.custom.HermitWitchEntity;
import net.sfedu.ars_maleficarum.entity.custom.SwampDrownedEntity;
import net.sfedu.ars_maleficarum.entity.layers.ModModelLayers;

@Mod.EventBusSubscriber(modid = ArsMaleficarum.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.MANDRAKE_LAYER, MandrakeModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.POISONOUS_ESSENCE_LAYER, PoisonousEssenceModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.FIRE_ESSENCE_LAYER, FireEssenceModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.GLUTTONY_DEMON_LAYER, GluttonyDemonModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.HERMIT_WITCH_LAYER, HermitWitchModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SWAMP_DROWNED_LAYER, SwampDrownedModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MANDRAKE.get(), MandrakeEntity.createAttributes().build());
        event.put(ModEntities.GLUTTONY_DEMON.get(), GluttonyDemonEntity.createAttributes().build());
        event.put(ModEntities.HERMIT_WITCH.get(), HermitWitchEntity.createAttributes().build());
        event.put(ModEntities.SWAMP_DROWNED.get(), SwampDrownedEntity.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerSpawnPlacement(SpawnPlacementRegisterEvent event)
    {
        event.register(ModEntities.SWAMP_DROWNED.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SwampDrownedEntity::checkSwampDrownedSpawnRules,SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}

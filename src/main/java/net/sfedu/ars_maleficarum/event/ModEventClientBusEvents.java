package net.sfedu.ars_maleficarum.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.custom.entity.ModBlockEntities;
import net.sfedu.ars_maleficarum.block.custom.entity.renderer.BrewingCauldronBlockEntityRenderer;
import net.sfedu.ars_maleficarum.block.custom.entity.renderer.InfusingAltarBlockEntityRenderer;

@Mod.EventBusSubscriber(modid = ArsMaleficarum.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,value= Dist.CLIENT)
public class ModEventClientBusEvents {


    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.INFUSING_ALTAR_BE.get(),
                InfusingAltarBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.BREWING_CAULDRON_BE.get(),
                BrewingCauldronBlockEntityRenderer::new);
    }
}

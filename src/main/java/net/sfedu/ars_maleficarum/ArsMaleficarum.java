package net.sfedu.ars_maleficarum;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.entity.ModBlockEntities;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.client.*;
import net.sfedu.ars_maleficarum.item.ModCreativeModTabs;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.loot.ModLootModifiers;
import net.sfedu.ars_maleficarum.recipe.ModRecipes;
import net.sfedu.ars_maleficarum.screen.InfusingAltarScreen;
import net.sfedu.ars_maleficarum.screen.ModMenuTypes;
import net.sfedu.ars_maleficarum.screen.OdourExtractorFurnaceScreen;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import net.sfedu.ars_maleficarum.world.decorator.ModTreeDecoratorTypes;
import net.sfedu.ars_maleficarum.world.tree.ModFoliagePlacerTypes;
import net.sfedu.ars_maleficarum.world.tree.ModTrunkPlacerTypes;

@Mod(ArsMaleficarum.MOD_ID)
public class ArsMaleficarum
{
    public static final String MOD_ID = "ars_maleficarum";
    public ArsMaleficarum()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        registerAllContent(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerAllContent(IEventBus modEventBus) {
        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModTrunkPlacerTypes.register(modEventBus);
        ModFoliagePlacerTypes.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModTreeDecoratorTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModEntities.register(modEventBus);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {

                MenuScreens.register(ModMenuTypes.ODOUR_EXTRACTING_MENU.get(), OdourExtractorFurnaceScreen::new);
                MenuScreens.register(ModMenuTypes.INFUSING_ALTAR_MENU.get(), InfusingAltarScreen::new);
                EntityRenderers.register(ModEntities.MANDRAKE.get(), MandrakeRender::new);

                EntityRenderers.register(ModEntities.GLUTTONY_DEMON.get(), GluttonyDemonRender::new);
                EntityRenderers.register(ModEntities.POISONOUS_ESSENCE.get(), PoisonousEssenceRender::new);
                EntityRenderers.register(ModEntities.FIRE_ESSENCE.get(), FireEssenceRender::new);
                EntityRenderers.register(ModEntities.HERMIT_WITCH.get(), HermitWitchRender::new);
                EntityRenderers.register(ModEntities.SWAMP_DROWNED.get(), SwampDrownedRender::new);
            });
        }
    }
}

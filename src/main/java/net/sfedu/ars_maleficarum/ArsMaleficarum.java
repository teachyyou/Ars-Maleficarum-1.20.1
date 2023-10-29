package net.sfedu.ars_maleficarum;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.datagen.ModBlockStateProvider;
import net.sfedu.ars_maleficarum.datagen.ModItemModelProvider;
import net.sfedu.ars_maleficarum.item.ModCreativeModTabs;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.loot.ModLootModifiers;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArsMaleficarum.MOD_ID)
public class ArsMaleficarum
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "ars_maleficarum";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ArsMaleficarum()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Регистрация вкладки в творческом режиме
        ModCreativeModTabs.register(modEventBus);

        //Регистрация предметов
        ModItems.register(modEventBus);

        //Регистрация блоков
        ModBlocks.register(modEventBus);

        //Регистрация расширения стандартного дропа
        ModLootModifiers.register(modEventBus);


        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}

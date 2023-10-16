package net.sfedu.ars_maleficarum.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;

import java.awt.*;

public class ModCreativeModTabs {

    //DefferedRegister для вкладки в творческом режиме
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArsMaleficarum.MOD_ID);

    //Создание творческой вкладки
    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("ars_maleficarum_tab",
            ()->CreativeModeTab.builder().icon(()->new ItemStack(ModItems.SAGE_FLOWER.get()))
                    .title(Component.translatable("creativetab.ars_maleficarum_tab"))
                    .displayItems(((pParameters, pOutput) -> {
                        //Здесь перечисление всех предметов, находящихся во вкладке
                        pOutput.accept(ModItems.SAGE_FLOWER.get());
                        pOutput.accept(ModItems.MARIGOLD_FLOWER.get());
                    }))
                    .build());

    //Регистрация вкладки
    public static void register(IEventBus eventBus) {
       CREATIVE_MODE_TABS.register(eventBus);
    }
}

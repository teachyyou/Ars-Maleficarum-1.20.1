package net.sfedu.ars_maleficarum.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;

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
                        pOutput.accept(ModItems.SAGE_SEED.get());
                        pOutput.accept(ModItems.SAGE_FLOWER.get());
                        pOutput.accept(ModItems.SAGE_LEAF.get());
                        pOutput.accept(ModItems.MARIGOLD_SEED.get());
                        pOutput.accept(ModItems.MARIGOLD_FLOWER.get());
                        pOutput.accept(ModBlocks.CURSED_GOLD_BLOCK.get());
                        pOutput.accept(ModBlocks.SILVER_BLOCK.get());
                        pOutput.accept(ModBlocks.CURSED_GOLD_ORE_BLOCK.get());
                        pOutput.accept(ModBlocks.SILVER_ORE_BLOCK.get());
                        pOutput.accept(ModItems.SILVER_NUGGET.get());
                        pOutput.accept(ModItems.CURSED_GOLD_NUGGET.get());
                        pOutput.accept(ModItems.CURSED_GOLD.get());
                        pOutput.accept(ModItems.SILVER.get());
                    }))
                    .build());

    //Регистрация вкладки
    public static void register(IEventBus eventBus) {
       CREATIVE_MODE_TABS.register(eventBus);
    }
}

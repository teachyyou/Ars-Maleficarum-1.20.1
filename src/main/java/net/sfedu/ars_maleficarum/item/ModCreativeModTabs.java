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
import net.sfedu.ars_maleficarum.item.custom.CarbonDetectorItem;

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
                        pOutput.accept(ModItems.CARBON_DETECTOR.get());
                        pOutput.accept(ModItems.METAL_DETECTOR.get());
                        pOutput.accept(ModItems.VALUABLE_DETECTOR.get());
                        pOutput.accept(ModBlocks.ROWAN_LOG.get());
                        pOutput.accept(ModBlocks.ROWAN_WOOD.get());
                        pOutput.accept(ModBlocks.ROWAN_LEAVES.get());
                        pOutput.accept(ModBlocks.ROWAN_BERRIES_LEAVES.get());
                        pOutput.accept(ModBlocks.ROWAN_PLANKS.get());
                        pOutput.accept(ModBlocks.ROWAN_SAPLING.get());
                        pOutput.accept(ModItems.ROWAN_BERRIES.get());
                        pOutput.accept(ModItems.ROWAN_BARK.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_LOG.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_WOOD.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_LEAVES.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_PLANKS.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_SAPLING.get());
                        pOutput.accept(ModItems.STONE_PESTLE.get());
                        pOutput.accept(ModItems.FLINT_KNIFE.get());
                        pOutput.accept(ModItems.EMPTY_SEAL.get());
                        pOutput.accept(ModItems.PERCEPTION_CORE.get());
                        pOutput.accept(ModItems.SALT.get());
                        pOutput.accept(ModItems.STONE_MORTAR.get());
                        pOutput.accept(ModItems.STONE_MORTAR_AND_PESTLE.get());
                        pOutput.accept(ModItems.WOODEN_MORTAR_AND_PESTLE.get());
                        pOutput.accept(ModItems.BAT_WING.get());
                        pOutput.accept(ModBlocks.SALT_BLOCK.get());
                        pOutput.accept(ModItems.SUNLIGHT_FLOWER_SEED.get());
                        pOutput.accept(ModItems.SUNLIGHT_FLOWER.get());
                        pOutput.accept(ModItems.MOONLIGHT_FLOWER_SEED.get());
                        pOutput.accept(ModItems.MOONLIGHT_FLOWER.get());
                        pOutput.accept(ModBlocks.DEAD_TREE_LOG.get());
                        pOutput.accept(ModItems.DEAD_TREE_BARK.get());
                        pOutput.accept(ModItems.DEAD_TREE_LARVA.get());
                        pOutput.accept(ModItems.FERMENTED_TREE_LARVA.get());
                        pOutput.accept(ModItems.TREE_LARVA.get());
                        pOutput.accept(ModItems.SILVER_DAGGER.get().getDefaultInstance());
                        pOutput.accept(ModBlocks.ODOUR_EXTRACTING_FURNACE.get());



                    }))
                    .build());

    //Регистрация вкладки
    public static void register(IEventBus eventBus) {
       CREATIVE_MODE_TABS.register(eventBus);
    }
}

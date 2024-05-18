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

    public static final RegistryObject<CreativeModeTab> ARS_MALEFICARUM_INGREDIENTS_TAB = CREATIVE_MODE_TABS.register("ars_maleficarum_ingredients_tab",
            ()->CreativeModeTab.builder().icon(()->new ItemStack(ModItems.PETRICHOR.get()))
                    .title(Component.translatable("creativetab.ars_maleficarum_ingredients_tab"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.PETRICHOR.get());
                        pOutput.accept(ModItems.SMELL_OF_HOME.get());
                        pOutput.accept(ModItems.DESERT_SPIRIT.get());
                        pOutput.accept(ModItems.SWEET_DREAM.get());
                        pOutput.accept(ModItems.SOARING_LIGHTNESS.get());
                        pOutput.accept(ModItems.RING_OF_MORNING_DEW.get());
                        pOutput.accept(ModItems.WASTELAND_WIND.get());
                        pOutput.accept(ModItems.CONIFEROUS_OIL.get());
                        pOutput.accept(ModItems.STINK_OF_SWAMP.get());
                        pOutput.accept(ModItems.ABSOLUTE_ORDER.get());
                        pOutput.accept(ModItems.TROPICAL_MONSOON.get());
                        pOutput.accept(ModItems.WHIFF_OF_TIME.get());
                        pOutput.accept(ModItems.CHERRY_ETUDE.get());
                        pOutput.accept(ModItems.TUNE_OF_HARMONY.get());
                        pOutput.accept(ModItems.SCENT_OF_UNCERTAINTY.get());
                        pOutput.accept(ModItems.SCENT_OF_UNCERTAINTY.get());
                        pOutput.accept(ModItems.EMPTY_VIAL.get());
                        pOutput.accept(ModItems.GROUND_MARIGOLD_FLOWERS.get());
                        pOutput.accept(ModItems.GROUND_SAGE_FLOWERS.get());
                        pOutput.accept(ModItems.ASH.get());
                        pOutput.accept(ModItems.SWAMP_ROTFIEND_INGREDIENT.get());


                    }))
                    .build());

    //Создание творческой вкладки
    public static final RegistryObject<CreativeModeTab> ARS_MALEFICARUM_MAIN_TAB = CREATIVE_MODE_TABS.register("ars_maleficarum_main_tab",
            ()->CreativeModeTab.builder().icon(()->new ItemStack(ModItems.SAGE_FLOWER.get()))
                    .title(Component.translatable("creativetab.ars_maleficarum_main_tab"))
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
                        pOutput.accept(ModBlocks.SILVER_DEEPSLATE_ORE_BLOCK.get());
                        pOutput.accept(ModBlocks.CURSED_GOLD_NETHER_ORE_BLOCK.get());
                        pOutput.accept(ModBlocks.CURSED_GOLD_DEEPSLATE_ORE_BLOCK.get());
                        pOutput.accept(ModItems.SILVER_NUGGET.get());
                        pOutput.accept(ModItems.CURSED_GOLD_NUGGET.get());
                        pOutput.accept(ModItems.CURSED_GOLD_CHUNK.get());
                        pOutput.accept(ModItems.SILVER_CHUNK.get());
                        pOutput.accept(ModItems.CURSED_GOLD.get());
                        pOutput.accept(ModItems.SILVER.get());
                        pOutput.accept(ModItems.CARBON_DETECTOR.get());
                        pOutput.accept(ModItems.METAL_DETECTOR.get());
                        pOutput.accept(ModItems.VALUABLE_DETECTOR.get());
                        pOutput.accept(ModBlocks.ROWAN_LOG.get());
                        pOutput.accept(ModBlocks.ROWAN_WOOD.get());
                        pOutput.accept(ModBlocks.STRIPPED_ROWAN_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_ROWAN_WOOD.get());
                        pOutput.accept(ModBlocks.ROWAN_LEAVES.get());
                        pOutput.accept(ModBlocks.ROWAN_BERRIES_LEAVES.get());
                        pOutput.accept(ModBlocks.ROWAN_PLANKS.get());
                        pOutput.accept(ModBlocks.ROWAN_SAPLING.get());
                        pOutput.accept(ModBlocks.ROWAN_STAIRS.get());
                        pOutput.accept(ModBlocks.ROWAN_SLAB.get());
                        pOutput.accept(ModBlocks.ROWAN_FENCE.get());
                        pOutput.accept(ModBlocks.ROWAN_FENCE_GATE.get());
                        pOutput.accept(ModItems.ROWAN_BERRIES.get());
                        pOutput.accept(ModItems.ROWAN_BARK.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_LOG.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_WOOD.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_LEAVES.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_PLANKS.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_SAPLING.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_STAIRS.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_SLAB.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_FENCE.get());
                        pOutput.accept(ModBlocks.NAMELESS_TREE_FENCE_GATE.get());
                        pOutput.accept(ModItems.NAMELESS_CHARCOAL.get());
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
                        pOutput.accept(ModBlocks.INFUSING_ALTAR.get());
                        pOutput.accept(ModItems.WOODEN_FIGURE.get());
                        pOutput.accept(ModBlocks.WOODEN_CAT_FIGURE.get());
                        pOutput.accept(ModItems.POPPET.get());
                        pOutput.accept(ModItems.MANDRAKE_SEED.get());
                        pOutput.accept(ModItems.MANDRAKE_ROOT.get());
                        pOutput.accept(ModBlocks.INFUSING_ALTAR_STONE_BLOCK.get());
                        pOutput.accept(ModBlocks.CHANDELIER.get());
                        pOutput.accept(ModBlocks.SKULL_ON_STICK.get());
                        pOutput.accept(ModBlocks.CRYSTAL_BALL.get());

                        pOutput.accept(ModItems.MANDRAKE_SPAWN_EGG.get());
                        pOutput.accept(ModItems.GLUTTONY_DEMON_SPAWN_EGG.get());

                        pOutput.accept(ModBlocks.BREWING_CAULDRON.get());


                        pOutput.accept(ModItems.POISON_STAFF.get());
                        pOutput.accept(ModItems.DRY_WOOD.get());
                        pOutput.accept(ModItems.INERT_POISON_STAFF.get());
                        pOutput.accept(ModItems.WHITE_CHALK.get());
                        pOutput.accept(ModItems.GOLDEN_CHALK.get());
                        pOutput.accept(ModItems.CRIMSON_CHALK.get());
                        pOutput.accept(ModItems.GREEN_CHALK.get());
                        pOutput.accept(ModItems.BLACK_CHALK.get());
                        pOutput.accept(ModItems.WHITE_CIRCLE_CORE_DRAWING_KIT.get());
                        pOutput.accept(ModItems.GREEN_CIRCLE_CORE_DRAWING_KIT.get());
                        pOutput.accept(ModItems.CRIMSON_CIRCLE_CORE_DRAWING_KIT.get());
                        pOutput.accept(ModItems.CHALK_BRUSH.get());

                        pOutput.accept(ModBlocks.KRAMER_TREE_LOG.get());
                        pOutput.accept(ModBlocks.KRAMER_TREE_WOOD.get());
                        pOutput.accept(ModBlocks.KRAMER_TREE_LEAVES.get());
                        pOutput.accept(ModBlocks.KRAMER_TREE_PLANKS.get());
                        pOutput.accept(ModBlocks.KRAMER_SAPLING.get());
                        

                        pOutput.accept(ModItems.WET_ENCHANTED_LEATHER.get());
                        pOutput.accept(ModItems.DRIED_ENCHANTED_LEATHER.get());

                        pOutput.accept(ModItems.SIMPLE_WITCH_HAT.get());

                        pOutput.accept(ModItems.FIRE_STAFF.get());
                        pOutput.accept(ModItems.INERT_FIRE_STAFF.get());
                        pOutput.accept(ModBlocks.SITE_OF_SUMMONING_CORE_BLOCK.get());
                        pOutput.accept(ModItems.TRADE_WITCH_SPAWN_EGG.get());

                        pOutput.accept(ModItems.EXHAUSTED_SWALLOW_POTION.get());
                        pOutput.accept(ModItems.SPURIOUS_THUNDERBOLT_POTION.get());
                        pOutput.accept(ModItems.MAGMACUBE_GALL_POTION.get());

                    }))
                    .build());


    //Регистрация вкладки
    public static void register(IEventBus eventBus) {
       CREATIVE_MODE_TABS.register(eventBus);
    }
}

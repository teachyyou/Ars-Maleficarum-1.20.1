package net.sfedu.ars_maleficarum.item;

import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.item.custom.*;
import net.sfedu.ars_maleficarum.item.custom.ritualCircleItems.*;

public class ModItems {


    //DefferedRegister для предметов
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ArsMaleficarum.MOD_ID);

    //Регистрация цветка шалфея
    public static final RegistryObject<Item> SAGE_FLOWER = ITEMS.register("sage_flower",
            ()->new Item(new Item.Properties()));
    //Регистрация листьев шалфея
    public static final RegistryObject<Item> SAGE_LEAF = ITEMS.register("sage_leaf",
            ()->new Item(new Item.Properties()));

    //Регистрация семян шалфея
    public static final RegistryObject<Item> SAGE_SEED = ITEMS.register("sage_seeds",
            ()->new ItemNameBlockItem(ModBlocks.SAGE_CROP.get(), new Item.Properties()));

    //Регистрация цветка календулы
    public static final RegistryObject<Item> MARIGOLD_FLOWER = ITEMS.register("marigold_flower",
            ()->new Item(new Item.Properties()));

    //Регистрация семян календулы
    public static final RegistryObject<Item> MARIGOLD_SEED = ITEMS.register("marigold_seeds",
            ()->new ItemNameBlockItem(ModBlocks.MARIGOLD_CROP.get(), new Item.Properties()));
    //Регистрация семян цветка солнечного света
    public static final RegistryObject<Item> SUNLIGHT_FLOWER_SEED = ITEMS.register("sunlight_flower_seeds",
            ()->new ItemNameBlockItem(ModBlocks.SUNLIGHT_FLOWER_CROP.get(), new Item.Properties()));
    //Регистрация цветка солнечного света
    public static final RegistryObject<Item> SUNLIGHT_FLOWER = ITEMS.register("sunlight_flower",
            ()->new Item(new Item.Properties()));
    //Регистрация семян цветка лунного света
    public static final RegistryObject<Item> MOONLIGHT_FLOWER_SEED = ITEMS.register("moonlight_flower_seeds",
            ()->new ItemNameBlockItem(ModBlocks.MOONLIGHT_FLOWER_CROP.get(), new Item.Properties()));
    //Регистрация цветка лунного света
    public static final RegistryObject<Item> MOONLIGHT_FLOWER = ITEMS.register("moonlight_flower",
            ()->new Item(new Item.Properties()));
    //Регистрация слитка проклятого золота
    public static final RegistryObject<Item> CURSED_GOLD = ITEMS.register("cursed_gold",
            ()->new Item(new Item.Properties()));

    //Регистрация слитка проклятого серебра
    public static final RegistryObject<Item> SILVER = ITEMS.register("silver",
            ()->new Item(new Item.Properties()));

    //Регистрация  самородка проклятого серебра
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_CHUNK = ITEMS.register("silver_chunk",
            ()->new Item(new Item.Properties()));

    //Регистрация  самородка проклятого золота
    public static final RegistryObject<Item> CURSED_GOLD_NUGGET = ITEMS.register("cursed_gold_nugget",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> CURSED_GOLD_CHUNK = ITEMS.register("cursed_gold_chunk",
            ()->new Item(new Item.Properties().durability(1024)));

    public static final RegistryObject<Item> POISON_STAFF = ITEMS.register("poison_staff",
            ()->new PoisonStaff(new Item.Properties().durability(1024)));
    //Регистрация детектора углеродной руды
    public static final RegistryObject<Item> CARBON_DETECTOR = ITEMS.register("carbon_detector",
            ()->new CarbonDetectorItem(new Item.Properties().durability(100)));
    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            ()->new MetalDetectorItem(new Item.Properties().durability(80)));
    public static final RegistryObject<Item> VALUABLE_DETECTOR = ITEMS.register("valuable_detector",
            ()->new ValuableDetectorItem(new Item.Properties().durability(65)));
    //Регистрация каменного пестика
    public static final RegistryObject<Item> STONE_PESTLE = ITEMS.register("stone_pestle",
            ()->new Item(new Item.Properties()));

    public static final RegistryObject<Item> ROWAN_BERRIES = ITEMS.register("rowan_berries",
            ()->new Item(new Item.Properties().food(ModFoods.ROWAN_BERRIES)));

    public static final RegistryObject<Item> ROWAN_BARK = ITEMS.register("rowan_bark",
            ()->new Item(new Item.Properties()));
    //public static final RegistryObject<Item> SWAMP_ROTFIEND = ITEMS.register("swamp_rotfiend_item",
            //()->new ItemNameBlockItem(ModBlocks.SWAMP_ROTFIEND.get(), new Item.Properties()));

    public static final RegistryObject<Item> SWAMP_ROTFIEND_INGREDIENT = ITEMS.register("swamp_rotfiend_ingredient_item",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> NAMELESS_CHARCOAL = ITEMS.register("nameless_charcoal",
            ()->new FuelItem(new Item.Properties(),400));

    public static final RegistryObject<Item> WOODEN_FIGURE = ITEMS.register("wooden_figure",
            ()->new BlankMagicalFocus(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> POPPET = ITEMS.register("poppet",
            ()->new BlankMagicalFocus(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FLINT_KNIFE = ITEMS.register("flint_knife",
            ()->new FlintKnife(new Item.Properties().durability(16)));

    public static final RegistryObject<Item> SILVER_DAGGER = ITEMS.register("silver_dagger",
            ()->new SilverDagger(Tiers.GOLD,5,-1F,new Item.Properties().durability(96)));

    public static final RegistryObject<Item> EMPTY_SEAL = ITEMS.register("empty_seal",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> PERCEPTION_CORE = ITEMS.register("perception_core",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> SALT = ITEMS.register("salt",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> STONE_MORTAR = ITEMS.register("stone_mortar",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> STONE_MORTAR_AND_PESTLE = ITEMS.register("stone_mortar_and_pestle",
            ()->new StoneMortarAndPestle(new Item.Properties().durability(40)));
    public static final RegistryObject<Item> WOODEN_MORTAR_AND_PESTLE = ITEMS.register("wooden_mortar_and_pestle",
            ()->new WoodenMortarAndPestle(new Item.Properties().durability(18)));
    public static final RegistryObject<Item> BAT_WING = ITEMS.register("bat_wing",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEAD_TREE_BARK = ITEMS.register("dead_tree_bark",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEAD_TREE_LARVA = ITEMS.register("dead_tree_larva",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> FERMENTED_TREE_LARVA = ITEMS.register("fermented_tree_larva",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> TREE_LARVA = ITEMS.register("tree_larva",
            ()->new TreeLarva(new Item.Properties()));

    public static final RegistryObject<Item> EMPTY_VIAL = ITEMS.register("empty_vial",
            ()->new Item(new Item.Properties()));

    public static final RegistryObject<Item> SMELL_OF_HOME = ITEMS.register("smell_of_home",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> RING_OF_MORNING_DEW = ITEMS.register("ring_of_morning_dew",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> CONIFEROUS_OIL = ITEMS.register("coniferous_oil",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> TROPICAL_MONSOON = ITEMS.register("tropical_monsoon",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> PETRICHOR = ITEMS.register("petrichor",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> WASTELAND_WIND = ITEMS.register("wasteland_wind",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> ABSOLUTE_ORDER = ITEMS.register("absolute_order",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> SCENT_OF_UNCERTAINTY = ITEMS.register("scent_of_uncertainty",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> WHIFF_OF_TIME = ITEMS.register("whiff_of_time",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> CHERRY_ETUDE = ITEMS.register("cherry_etude",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> SOARING_LIGHTNESS = ITEMS.register("soaring_lightness",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> STINK_OF_SWAMP = ITEMS.register("stink_of_swamp",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> DESERT_SPIRIT = ITEMS.register("desert_spirit",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> SWEET_DREAM = ITEMS.register("sweet_dream",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> TUNE_OF_HARMONY = ITEMS.register("tune_of_harmony",
            ()->new IngredientItem(new Item.Properties()));

    public static final RegistryObject<Item> MANDRAKE_SEED = ITEMS.register("mandrake_seed",
            ()->new ItemNameBlockItem(ModBlocks.MANDRAKE_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> MANDRAKE_ROOT = ITEMS.register("mandrake_root",
            ()->new IngredientItem(new Item.Properties()));

    public static final RegistryObject<Item> GROUND_SAGE_FLOWERS = ITEMS.register("ground_sage_flowers",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_MARIGOLD_FLOWERS = ITEMS.register("ground_marigold_flowers",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> ASH = ITEMS.register("ash",
            ()->new IngredientItem(new Item.Properties()));
    public static final RegistryObject<Item> MANDRAKE_SPAWN_EGG = ITEMS.register("mandrake_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MANDRAKE,0x7e9680,0xc5d1c5,new Item.Properties()));

    public static final RegistryObject<Item> GLUTTONY_DEMON_SPAWN_EGG = ITEMS.register("gluttony_demon_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GLUTTONY_DEMON,0x2e9980,0xc6d1c5,new Item.Properties()));

    public static final RegistryObject<Item> WHITE_CHALK = ITEMS.register("white_chalk",
            ()->new WhiteChalk(new Item.Properties().durability(44)));

    public static final RegistryObject<Item> GREEN_CHALK = ITEMS.register("green_chalk",
            ()->new GreenChalk(new Item.Properties().durability(44)));

    public static final RegistryObject<Item> GOLDEN_CHALK = ITEMS.register("golden_chalk",
            ()->new GoldenChalk(new Item.Properties().durability(1)));

    public static final RegistryObject<Item> CRIMSON_CHALK = ITEMS.register("crimson_chalk",
            ()->new CrimsonChalk(new Item.Properties().durability(44)));



    public static final RegistryObject<Item> WHITE_CIRCLE_CORE_DRAWING_KIT = ITEMS.register("white_circle_core_drawing_kit",
            ()->new WhiteCoreDrawingKit(new Item.Properties().durability(1)));

    public static final RegistryObject<Item> GREEN_CIRCLE_CORE_DRAWING_KIT = ITEMS.register("green_circle_core_drawing_kit",
            ()->new GreenCoreDrawingKit(new Item.Properties().durability(1)));

    public static final RegistryObject<Item> CRIMSON_CIRCLE_CORE_DRAWING_KIT = ITEMS.register("crimson_circle_core_drawing_kit",
            ()->new NetherCoreDrawingKit(new Item.Properties().durability(1)));

    public static final RegistryObject<Item> CHALK_BRUSH = ITEMS.register("chalk_brush",
            ()->new Item(new Item.Properties().stacksTo(1).durability(128)));

    //Регистрация предметов
    public static void register(IEventBus eventBus) {

        ITEMS.register(eventBus);
    }


}

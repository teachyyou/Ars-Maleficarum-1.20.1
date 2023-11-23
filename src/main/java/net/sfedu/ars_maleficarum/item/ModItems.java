package net.sfedu.ars_maleficarum.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.item.custom.*;

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

    //Регистрация  самородка проклятого золота
    public static final RegistryObject<Item> CURSED_GOLD_NUGGET = ITEMS.register("cursed_gold_nugget",
            ()->new Item(new Item.Properties()));
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




    //Регистрация предметов
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}

package net.sfedu.ars_maleficarum.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.item.custom.CarbonDetectorItem;
import net.sfedu.ars_maleficarum.item.custom.MetalDetectorItem;
import net.sfedu.ars_maleficarum.item.custom.ValuableDetectorItem;

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

    //Регистрация предметов
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}

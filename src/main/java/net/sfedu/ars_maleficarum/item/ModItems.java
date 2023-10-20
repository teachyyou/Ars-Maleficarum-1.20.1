package net.sfedu.ars_maleficarum.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;

public class ModItems {

    //DefferedRegister для предметов
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ArsMaleficarum.MOD_ID);
    //Регистрация слитка "Проклятое золото"
    public static final RegistryObject<Item> CURSED_GOLD = ITEMS.register("cursed_gold",
            () -> new Item(new Item.Properties()));
    //Регистрация слитка "Проклятое серебро"
    public static final RegistryObject<Item> CURSED_SILVER = ITEMS.register("cursed_silver",
            () -> new Item(new Item.Properties()));
    //Регистрация самородка "Проклятого серебра"
    public static final RegistryObject<Item> CURSED_SILVER_NUGGET = ITEMS.register("cursed_silver_nugget",
            () -> new Item(new Item.Properties()));
    //Регистрация самородка "Проклятого золота"
    public static final RegistryObject<Item> CURSED_GOLD_NUGGET = ITEMS.register("cursed_gold_nugget",
            () -> new Item(new Item.Properties()));

    //Регистрация цветка шалфея
    public static final RegistryObject<Item> SAGE_FLOWER = ITEMS.register("sage_flower",
            ()->new Item(new Item.Properties()));

    //Регистрация семян шалфея
    public static final RegistryObject<Item> SAGE_SEED = ITEMS.register("sage_seeds",
            ()->new ItemNameBlockItem(ModBlocks.SAGE_CROP.get(), new Item.Properties()));

    //Регистрация цветка календулы
    public static final RegistryObject<Item> MARIGOLD_FLOWER = ITEMS.register("marigold_flower",
            ()->new Item(new Item.Properties()));

    //Регистрация предметов
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}

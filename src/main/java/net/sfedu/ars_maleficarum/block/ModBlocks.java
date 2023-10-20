package net.sfedu.ars_maleficarum.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.custom.SageCropBlock;
import net.sfedu.ars_maleficarum.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    //DefferedRegister для блоков
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ArsMaleficarum.MOD_ID);



    public static final RegistryObject<Block> SAGE_CROP = BLOCKS.register("sage_crop",
            ()->new SageCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    //Регистрация блока проклятого золота
    public static final RegistryObject<Block> CURSED_GOLD_BLOCK = registerBlock("cursed_gold_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    //Регистрация блока проклятого серебра
    public  static final RegistryObject<Block> CURSED_SILVER_BLOCK = registerBlock("cursed_silver_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    //Регистрация блока руды проклятого серебра
    public  static final RegistryObject<Block> RAW_CURSED_SILVER_BLOCK = registerBlock("raw_cursed_silver_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    //Регистрация блока руды проклятого золота
    public  static final RegistryObject<Block> RAW_CURSED_GOLD_BLOCK = registerBlock("raw_cursed_gold_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));


    //Регистрация блока и предмета, привязанного к нему
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    //Регистрация предмета, Привязанного к блоку
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    //Регистрация блоков
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);}
}

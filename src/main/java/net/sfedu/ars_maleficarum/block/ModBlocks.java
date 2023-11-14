package net.sfedu.ars_maleficarum.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.custom.*;
import net.sfedu.ars_maleficarum.block.custom.entity.OdourExtractingFurnaceBlock;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.world.tree.DeadTreeGrower;
import net.sfedu.ars_maleficarum.world.tree.RowanTreeGrower;

import java.util.function.Supplier;

public class ModBlocks {

    //DefferedRegister для блоков
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS,ArsMaleficarum.MOD_ID);



    //Регистрация посевов шалфея
    public static final RegistryObject<Block> SAGE_CROP = BLOCKS.register("sage_crop",
            ()->new SageCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));

    //Регистрация посевов календулы
    public static final RegistryObject<Block> MARIGOLD_CROP = BLOCKS.register("marigold_crop",
            ()->new MarigoldCropBlock(BlockBehaviour.Properties.copy(ModBlocks.SAGE_CROP.get())));

    //Регистрация блока проклятого золота
    public static  final RegistryObject<Block> CURSED_GOLD_BLOCK = registerBlock("cursed_gold_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

    //Регистрация блока серебра
    public static  final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    //Регистрация блока руды серебра
    public static  final RegistryObject<Block> SILVER_ORE_BLOCK = registerBlock("silver_ore_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
    //Регистрация блока руды проклятого золота
    public static  final RegistryObject<Block> CURSED_GOLD_ORE_BLOCK = registerBlock("cursed_gold_ore_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK)));

    public static final RegistryObject<Block> ROWAN_LOG = registerBlock("rowan_log",
            ()-> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));

    public static final RegistryObject<Block> ROWAN_WOOD = registerBlock("rowan_wood",
            ()-> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)));
    //Регистрация цветка солнечного света
    public static final RegistryObject<Block> SUNLIGHT_FLOWER_CROP = BLOCKS.register("sunlight_flower_crop",
            ()->new SunlightFlower(BlockBehaviour.Properties.copy(Blocks.WHEAT).lightLevel((p_50874_) -> {return 15;}).noOcclusion().noCollission()));
    //Регистрация цветка лунного света
    public static final RegistryObject<Block> MOONLIGHT_FLOWER_CROP = BLOCKS.register("moonlight_flower_crop",
            ()->new MoonlightFlower(BlockBehaviour.Properties.copy(Blocks.WHEAT).lightLevel((p_50874_) -> {return 15;}).noOcclusion().noCollission()));
    public static final RegistryObject<Block> ROWAN_PLANKS = registerBlock("rowan_planks",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<Block> ROWAN_LEAVES = registerBlock("rowan_leaves",
            ()-> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });
    public static final RegistryObject<Block> SALT_BLOCK = registerBlock("salt_block",
            ()-> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND)));

    public static final RegistryObject<Block> ROWAN_SAPLING = registerBlock("rowan_sapling",
            ()->new SaplingBlock(new RowanTreeGrower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> DEAD_TREE_SAPLING = registerBlock("dead_tree_sapling",
            ()->new SaplingBlock(new DeadTreeGrower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> ODOUR_EXTRACTING_FURNACE = registerBlock("odour_extracting_furnace",
            () -> new OdourExtractingFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> DEAD_TREE_LOG = registerBlock("dead_tree_log",
            ()-> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(0.3f))
            {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }
                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 70;
                }
                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 40;
                }
            });


    //Регистрация блока и предмета, привязанного к нему
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    //Регистрация предмета, Привязанного к блоку
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name,()->new BlockItem(block.get(),new Item.Properties()));
    }
    //Регистрация блоков
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

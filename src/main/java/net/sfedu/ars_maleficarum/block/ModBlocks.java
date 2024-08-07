package net.sfedu.ars_maleficarum.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.custom.*;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ChalkSymbol;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.RitualCircleCore;
import net.sfedu.ars_maleficarum.block.custom.decorative.CrystalBall;
import net.sfedu.ars_maleficarum.block.custom.decorative.SkullOnAStick;
import net.sfedu.ars_maleficarum.block.custom.decorative.Chandelier;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.world.tree.DeadTreeGrower;
import net.sfedu.ars_maleficarum.world.tree.KramerTreeGrower;
import net.sfedu.ars_maleficarum.world.tree.NamelessTreeGrower;
import net.sfedu.ars_maleficarum.world.tree.RowanTreeGrower;

import java.util.function.Supplier;

import static net.sfedu.ars_maleficarum.block.custom.decorative.Chandelier.LIT;

public class ModBlocks {

    //DefferedRegister для блоков
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ArsMaleficarum.MOD_ID);

    //Регистрация посевов шалфея
    public static final RegistryObject<Block> SAGE_CROP = BLOCKS.register("sage_crop",
            () -> new HerbCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission(), HerbCropBlock.CropType.SAGE));

    //Регистрация посевов календулы
    public static final RegistryObject<Block> MARIGOLD_CROP = BLOCKS.register("marigold_crop",
            () -> new HerbCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission(), HerbCropBlock.CropType.MARIGOLD));

    //Регистрация блока проклятого золота
    public static final RegistryObject<Block> CURSED_GOLD_BLOCK = registerBlock("cursed_gold_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

    //Регистрация блока серебра
    public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    //Регистрация блока руды серебра
    public static final RegistryObject<Block> SILVER_ORE_BLOCK = registerBlock("silver_ore_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
    public static final RegistryObject<Block> CONSIMING_FLAME = registerBlock("custom_fire",
            () -> new ConsumingFlameBlock(BlockBehaviour.Properties.copy(Blocks.FIRE).noLootTable().noOcclusion().noCollission()));

    public static final RegistryObject<Block> SILVER_DEEPSLATE_ORE_BLOCK = registerBlock("silver_deepslate_ore_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));
    //Регистрация блока руды проклятого золота
    public static final RegistryObject<Block> CURSED_GOLD_ORE_BLOCK = registerBlock("cursed_gold_ore_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK)));
    public static final RegistryObject<Block> SITE_OF_SUMMONING_CORE_BLOCK = registerBlock("site_of_summoning_core_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_DEEPSLATE).noLootTable().strength(70F)));

    public static final RegistryObject<Block> CURSED_GOLD_DEEPSLATE_ORE_BLOCK = registerBlock("cursed_gold_deepslate_ore_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE)));



    public static final RegistryObject<Block> CURSED_GOLD_NETHER_ORE_BLOCK = registerBlock("cursed_gold_nether_ore_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_GOLD_ORE)));

    //Регистрация цветка солнечного света
    public static final RegistryObject<Block> SUNLIGHT_FLOWER_CROP = BLOCKS.register("sunlight_flower_crop",
            () -> new SunlightFlower(BlockBehaviour.Properties.copy(Blocks.WHEAT).lightLevel(state -> state.getValue(SunlightFlower.AGE) > 3 ? 10 : 0).noOcclusion().noCollission()));
    //Регистрация цветка лунного света
    public static final RegistryObject<Block> MOONLIGHT_FLOWER_CROP = BLOCKS.register("moonlight_flower_crop",
            () -> new MoonlightFlower(BlockBehaviour.Properties.copy(Blocks.WHEAT).lightLevel(state -> state.getValue(MoonlightFlower.AGE) > 3 ? 10 : 0).noOcclusion().noCollission()));

    public static final RegistryObject<Block> ROWAN_LOG = registerBlock("rowan_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> ROWAN_WOOD = registerBlock("rowan_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)));
    public static final RegistryObject<Block> STRIPPED_ROWAN_LOG = registerBlock("stripped_rowan_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> STRIPPED_ROWAN_WOOD = registerBlock("stripped_rowan_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)));
    public static final RegistryObject<Block> ROWAN_PLANKS = registerBlock("rowan_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
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
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
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
    public static final RegistryObject<Block> ROWAN_BERRIES_LEAVES = registerBlock("rowan_berries_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
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

    public static final RegistryObject<Block> NAMELESS_TREE_LOG = registerBlock("nameless_tree_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> NAMELESS_TREE_WOOD = registerBlock("nameless_tree_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)));
    public static final RegistryObject<Block> NAMELESS_TREE_PLANKS = registerBlock("nameless_tree_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
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
    public static final RegistryObject<Block> NAMELESS_TREE_LEAVES = registerBlock("nameless_tree_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
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

    public static final RegistryObject<Block> KRAMER_TREE_LOG= registerBlock("kramer_tree_log",
            () -> new ModRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> KRAMER_TREE_WOOD = registerBlock("kramer_tree_wood",
            () -> new ModRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)));
    public static final RegistryObject<Block> KRAMER_TREE_PLANKS = registerBlock("kramer_tree_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }

            });
    public static final RegistryObject<Block> KRAMER_TREE_LEAVES = registerBlock("kramer_tree_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });


    public static final RegistryObject<Block> KRAMER_SAPLING = registerBlock("kramer_sapling",
            () -> new SaplingBlock(new KramerTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> SALT_BLOCK = registerBlock("salt_block",
            () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND)));

    //Саженец рябины
    public static final RegistryObject<Block> ROWAN_SAPLING = registerBlock("rowan_sapling",
            () -> new SaplingBlock(new RowanTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    //Саженец безымянного древа
    public static final RegistryObject<Block> NAMELESS_TREE_SAPLING = registerBlock("nameless_tree_sapling",
            () -> new SaplingBlock(new NamelessTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> DEAD_TREE_SAPLING = registerBlock("dead_tree_sapling",
            () -> new SaplingBlock(new DeadTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> ODOUR_EXTRACTING_FURNACE = registerBlock("odour_extracting_furnace",
            () -> new OdourExtractingFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().lightLevel(x -> x.getValue(BlockStateProperties.LIT) ? 14 : 0)));
    public static final RegistryObject<Block> WOODEN_CAT_FIGURE = registerBlock("wooden_cat_figure",
            () -> new WoodenCatFigure(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).noOcclusion()) {
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


    public static final RegistryObject<Block> DEAD_TREE_LOG = registerBlock("dead_tree_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(0.3f)) {
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

    public static final RegistryObject<Block> ROWAN_STAIRS = registerBlock("rowan_stairs",
            () -> new StairBlock(() -> ROWAN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(ROWAN_PLANKS.get())) {
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

    public static final RegistryObject<Block> ROWAN_SLAB = registerBlock("rowan_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ROWAN_PLANKS.get())) {
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

    public static final RegistryObject<Block> ROWAN_FENCE = registerBlock("rowan_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(ROWAN_PLANKS.get())) {
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

    public static final RegistryObject<Block> ROWAN_FENCE_GATE = registerBlock("rowan_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(ROWAN_PLANKS.get()), SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE) {
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
    public static final RegistryObject<Block> SWAMP_ROTFIEND = registerBlock("swamp_rotfiend",
            () -> new SwampRotfiendMushroom(
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().strength(0.2F, 3.0F).sound(SoundType.WOOD).noOcclusion().pushReaction(PushReaction.DESTROY)));


    public static final RegistryObject<Block> NAMELESS_TREE_STAIRS = registerBlock("nameless_tree_stairs",
            () -> new StairBlock(() -> NAMELESS_TREE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(NAMELESS_TREE_PLANKS.get())) {
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

    public static final RegistryObject<Block> NAMELESS_TREE_SLAB = registerBlock("nameless_tree_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(NAMELESS_TREE_PLANKS.get())) {
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

    public static final RegistryObject<Block> NAMELESS_TREE_FENCE = registerBlock("nameless_tree_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(NAMELESS_TREE_PLANKS.get())) {
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
    public static final RegistryObject<Block> NAMELESS_TREE_FENCE_GATE = registerBlock("nameless_tree_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(NAMELESS_TREE_PLANKS.get()), SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE) {
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

    public static final RegistryObject<Block> MANDRAKE_CROP = BLOCKS.register("mandrake_crop",
            () -> new MandrakeCropBlock(BlockBehaviour.Properties.copy(ModBlocks.SAGE_CROP.get()).noLootTable()));

    public static final RegistryObject<Block> WHITE_CHALK_SYMBOL = BLOCKS.register("white_chalk_symbol",
            ChalkSymbol::new);

    public static final RegistryObject<Block> GREEN_CHALK_SYMBOL = BLOCKS.register("green_chalk_symbol",
            ChalkSymbol::new);

    public static final RegistryObject<Block> CRIMSON_CHALK_SYMBOL = BLOCKS.register("crimson_chalk_symbol",
            ChalkSymbol::new);

    public static final RegistryObject<Block> BREWING_CAULDRON = registerBlock("brewing_cauldron",
            ()->new BrewingCauldronBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().lightLevel(state -> state.getValue(BrewingCauldronBlock.LIT) ? 8 : 0)));


    public static final RegistryObject<Block> RITUAL_CIRCLE_CORE = BLOCKS.register("ritual_circle_core",
            ()->new RitualCircleCore(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE).noOcclusion().noLootTable().explosionResistance(1200.0F)));

    public static final RegistryObject<Block> CHANDELIER = registerBlock("chandelier",
            ()->new Chandelier(BlockBehaviour.Properties.copy(Blocks.LANTERN).noOcclusion().lightLevel(x->x.getValue(LIT) ? 14 : 0)));

    public static final RegistryObject<Block> SKULL_ON_STICK = registerBlock("skull_on_a_stick",
            ()->new SkullOnAStick(BlockBehaviour.Properties.copy(Blocks.TORCH).noOcclusion().lightLevel(x->x.getValue(LIT) ? 14 : 0)));

    public static final RegistryObject<Block> CRYSTAL_BALL = registerBlock("crystal_ball",
            ()->new CrystalBall(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().lightLevel(x->6).noLootTable()));

    public static final RegistryObject<Block> INFUSING_ALTAR = registerBlock("infusing_altar",
            () -> new InfusingAltarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).lightLevel(x -> x.getValue(InfusingAltarBlock.STAGE) > 2 ? 14 : 0).noOcclusion()));


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
        BLOCKS.register(eventBus);
    }
}

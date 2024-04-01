package net.sfedu.ars_maleficarum.datagen.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.*;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;
import net.sfedu.ars_maleficarum.item.ModItems;


import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    //Генерация кастомного лута с блоков
    protected void generate() {
        generatedSunlight_flower_Drop();
        generatedMoonlight_flower_Drop();
        generatedSwampRotfiend_Drop();
        generateSageCropDrop();
        generateMarigoldCropDrop();
        //generateMandrakeCropDrop();
        //Блоки, которые при ломании дропают сами себя
        this.dropSelf(ModBlocks.CURSED_GOLD_BLOCK.get());
        this.dropSelf(ModBlocks.SILVER_BLOCK.get());

        this.dropSelf(ModBlocks.ROWAN_LOG.get());
        this.dropSelf(ModBlocks.ROWAN_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ROWAN_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ROWAN_WOOD.get());
        this.dropSelf(ModBlocks.ROWAN_PLANKS.get());
        this.dropSelf(ModBlocks.ROWAN_SAPLING.get());

        this.add(ModBlocks.ROWAN_BERRIES_LEAVES.get(), (block) ->
                createLeavesDropsWithAdditionalItem(block, ModBlocks.ROWAN_SAPLING.get(), ModItems.ROWAN_BERRIES.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(ModBlocks.ROWAN_LEAVES.get(), (block) ->
                createLeavesDrops(block, ModBlocks.ROWAN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(ModBlocks.NAMELESS_TREE_LOG.get());
        this.dropSelf(ModBlocks.NAMELESS_TREE_WOOD.get());
        this.dropSelf(ModBlocks.NAMELESS_TREE_PLANKS.get());
        this.dropSelf(ModBlocks.NAMELESS_TREE_SAPLING.get());
        this.dropSelf(ModBlocks.NAMELESS_TREE_STAIRS.get());
        this.dropSelf(ModBlocks.NAMELESS_TREE_FENCE.get());
        this.dropSelf(ModBlocks.NAMELESS_TREE_FENCE_GATE.get());
        this.add(ModBlocks.NAMELESS_TREE_SLAB.get(), block -> createSlabItemTable(ModBlocks.NAMELESS_TREE_SLAB.get()));

        this.add(ModBlocks.NAMELESS_TREE_LEAVES.get(), (block) ->
                createLeavesDrops(block, ModBlocks.NAMELESS_TREE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.add(ModBlocks.SALT_BLOCK.get(), block -> createSaltDrops(ModBlocks.SALT_BLOCK.get()));
        this.add(ModBlocks.DEAD_TREE_LOG.get(), block -> createDeadTreeDrops(ModBlocks.DEAD_TREE_LOG.get()));

        this.dropSelf(ModBlocks.DEAD_TREE_SAPLING.get());

        this.dropSelf(ModBlocks.ODOUR_EXTRACTING_FURNACE.get());
        this.dropSelf(ModBlocks.INFUSING_ALTAR_STONE_BLOCK.get());

        this.dropSelf(ModBlocks.ROWAN_STAIRS.get());
        this.add(ModBlocks.ROWAN_SLAB.get(), block -> createSlabItemTable(ModBlocks.ROWAN_SLAB.get()));
        this.dropSelf(ModBlocks.ROWAN_FENCE.get());
        this.dropSelf(ModBlocks.ROWAN_FENCE_GATE.get());

        this.dropOther(ModBlocks.INFUSING_ALTAR_CARPET_BLOCK.get(), ModBlocks.INFUSING_ALTAR_STONE_BLOCK.get().asItem());
        this.dropOther(ModBlocks.INFUSING_ALTAR_PENTA_BLOCK.get(), ModBlocks.INFUSING_ALTAR_STONE_BLOCK.get().asItem());
        this.dropOther(ModBlocks.INFUSING_ALTAR.get(), ModBlocks.INFUSING_ALTAR_STONE_BLOCK.get().asItem());
        this.dropOther(ModBlocks.CURSED_GOLD_ORE_BLOCK.get(), ModItems.CURSED_GOLD_CHUNK.get());
        this.dropOther(ModBlocks.CURSED_GOLD_DEEPSLATE_ORE_BLOCK.get(), ModItems.CURSED_GOLD_CHUNK.get());
        this.dropOther(ModBlocks.CURSED_GOLD_NETHER_ORE_BLOCK.get(), ModItems.CURSED_GOLD_NUGGET.get());
        this.dropOther(ModBlocks.SILVER_ORE_BLOCK.get(), ModItems.SILVER_CHUNK.get());
        this.dropOther(ModBlocks.SILVER_DEEPSLATE_ORE_BLOCK.get(), ModItems.SILVER_CHUNK.get());
        this.dropOther(ModBlocks.WOODEN_CAT_FIGURE.get(), ModItems.WOODEN_FIGURE.get().asItem());

        this.dropSelf(ModBlocks.KRAMER_TREE_LOG.get());
        this.dropSelf(ModBlocks.KRAMER_TREE_WOOD.get());
        this.dropSelf(ModBlocks.KRAMER_TREE_PLANKS.get());
        this.dropSelf(ModBlocks.KRAMER_SAPLING.get());
        this.add(ModBlocks.KRAMER_TREE_LEAVES.get(), (block) ->
                createLeavesDrops(block, ModBlocks.KRAMER_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(ModBlocks.CHANDELIER.get());
        this.dropSelf(ModBlocks.SKULL_ON_STICK.get());

    }

    //Вынесенная отдельно процедура регистрации выпадения предметов при сборе культуры солнечный свет
    protected void generatedSunlight_flower_Drop() {
        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.SUNLIGHT_FLOWER_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SunlightFlower.AGE, 6));
        //Выпадение предметов при сборе созревшей культуры
        this.add(ModBlocks.SUNLIGHT_FLOWER_CROP.get(), createCropDropsWith2Items_SecondItemRare(ModBlocks.SUNLIGHT_FLOWER_CROP.get(), ModItems.SUNLIGHT_FLOWER.get(), Items.GLOWSTONE_DUST,
                ModItems.SUNLIGHT_FLOWER_SEED.get(), lootitemcondition$builder2));
    }

    //Вынесенная отдельно процедура регистрации выпадения предметов при сборе культуры лунный свет
    protected void generatedMoonlight_flower_Drop() {
        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.MOONLIGHT_FLOWER_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SunlightFlower.AGE, 6));
        //Выпадение предметов при сборе созревшей культуры
        this.add(ModBlocks.MOONLIGHT_FLOWER_CROP.get(), createCropDropsWith2Items(ModBlocks.MOONLIGHT_FLOWER_CROP.get(), ModItems.MOONLIGHT_FLOWER.get(), ModItems.MOONLIGHT_FLOWER_SEED.get(),
                ModItems.MOONLIGHT_FLOWER_SEED.get(), lootitemcondition$builder2, 0.08F, 1));
    }

    protected void generatedSwampRotfiend_Drop() {
        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.SWAMP_ROTFIEND.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SwampRotfiendMushroom.AGE, 3));
        this.add(ModBlocks.SWAMP_ROTFIEND.get(), this.applyExplosionDecay(ModBlocks.SWAMP_ROTFIEND.get(),
                LootTable.lootTable().withPool(LootPool.lootPool().when(lootitemcondition$builder2)
                        .add(LootItem.lootTableItem(ModItems.SWAMP_ROTFIEND_INGREDIENT.get())))));
    }

    //Реализация возможности пройтись циклом по всем блокам (вроде бы??)
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    //Вынесенная отдельно процедура регистрации выпадения предметов при сборе культуры шалфея
    protected void generateSageCropDrop() {
        //Условие - предмет выпадет только тогда, когда культура полностью созрела
        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.SAGE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SageCropBlock.AGE, 3));

        //Выпадение предметов при сборе созревшей культуры
        this.add(ModBlocks.SAGE_CROP.get(), createCropDropsWith2Items(ModBlocks.SAGE_CROP.get(), ModItems.SAGE_FLOWER.get(), ModItems.SAGE_LEAF.get(),
                ModItems.SAGE_SEED.get(), lootitemcondition$builder, 0.53247F, 3));

    }


    protected void generateMarigoldCropDrop() {
        //Вынесенная отдельно процедура регистрации выпадения предметов при сборе культуры календулы
        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.MARIGOLD_CROP.get())
                .setProperties((StatePropertiesPredicate.Builder.properties().hasProperty(MarigoldCropBlock.AGE, 3)));

        //Выпадение предметов при сборе созревшей культуры
        this.add(ModBlocks.MARIGOLD_CROP.get(), createCropDrops(ModBlocks.MARIGOLD_CROP.get(), ModItems.MARIGOLD_FLOWER.get(),
                ModItems.MARIGOLD_SEED.get(), lootitemcondition$builder));
    }

    //protected void generateMandrakeCropDrop() {
    //LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
    //.hasBlockStateProperties(ModBlocks.MANDRAKE_CROP.get())
    //.setProperties((StatePropertiesPredicate.Builder.properties().hasProperty(MandrakeCropBlock.AGE,3).hasProperty(MandrakeCropBlock.IS_SPAWNED,true)));

    //this.add(ModBlocks.MANDRAKE_CROP.get(), createSimpleCropDrop(ModBlocks.MANDRAKE_CROP.get(),ModItems.MANDRAKE_ROOT.get(),
    //ModItems.MANDRAKE_SEED.get(), lootitemcondition$builder));
    // }
    protected LootTable.Builder createSimpleCropDrop(Block pCropBlock, Item pGrownCropItem1, Item pSeedsItem, LootItemCondition.Builder pDropGrownCropCondition) {
        return this.applyExplosionDecay(pCropBlock, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem1).when(pDropGrownCropCondition))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(pSeedsItem).when(pDropGrownCropCondition))));
    }

    protected LootTable.Builder createSimpleDrop(Block pCropBlock, Item pGrownCropItem1, LootItemCondition.Builder pDropGrownCropCondition) {
        return this.applyExplosionDecay(pCropBlock, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem1))));
    }

    //Дополнительная функция дропа, которой можно пользоваться, если нужно, чтобы с созревшей культуры падало 2 предмета, помимо семян.
    protected LootTable.Builder createCropDropsWith2Items(Block pCropBlock, Item pGrownCropItem1, Item pGrownCropItem2, Item pSeedsItem, LootItemCondition.Builder pDropGrownCropCondition, float probability, int extra_items) {
        return this.applyExplosionDecay(pCropBlock, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem1).when(pDropGrownCropCondition).otherwise(LootItem.lootTableItem(pSeedsItem)))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem2).when(pDropGrownCropCondition).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))))).withPool(LootPool.lootPool().when(pDropGrownCropCondition).add(LootItem.lootTableItem(pSeedsItem).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, probability, extra_items)))));
    }

    //Дополнительная функция дропа, которой можно пользоваться, если нужно, чтобы какой-то предмет дропался редко
    //Первый предмет - обычный, второй предмет - редкий, третий - семена
    protected LootTable.Builder createCropDropsWith2Items_SecondItemRare(Block pCropBlock, Item pGrownCropItem1, Item pGrownCropItem2, Item pSeedsItem, LootItemCondition.Builder pDropGrownCropCondition) {
        return this.applyExplosionDecay(pCropBlock, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem1).when(pDropGrownCropCondition).otherwise(LootItem.lootTableItem(pSeedsItem)))).withPool(LootPool.lootPool().when(pDropGrownCropCondition).add(LootItem.lootTableItem(pGrownCropItem2).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 0F))).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.1234F, 1)))).withPool(LootPool.lootPool().when(pDropGrownCropCondition).add(LootItem.lootTableItem(pSeedsItem).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5261F, 3)))));
    }

    //Дополнительная функция выпадения с листвы при ломании ещё одного предмета
    protected LootTable.Builder createLeavesDropsWithAdditionalItem(Block pLeavesBlock, Block pSaplingBlock, Item item, float... pChances) {
        //return this.createLeavesDrops(pLeavesBlock, pSaplingBlock, pChances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(this.applyExplosionCondition(pLeavesBlock, LootItem.lootTableItem(item)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.01F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
        return this.createLeavesDrops(pLeavesBlock, pSaplingBlock, pChances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(this.applyExplosionCondition(pLeavesBlock, LootItem.lootTableItem(item)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.5F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    //Для выпадения соли из блока соли
    protected LootTable.Builder createSaltDrops(Block pBlock) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(ModItems.SALT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))));
    }

    //Дроп с мертвого дерева
    protected LootTable.Builder createDeadTreeDrops(Block pBlock) {
        return this.applyExplosionDecay(pBlock, LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ModBlocks.DEAD_TREE_LOG.get())
                                        .when(HAS_SILK_TOUCH)))
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ModItems.DEAD_TREE_BARK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                .when(HAS_NO_SILK_TOUCH)))
                .withPool(LootPool.lootPool()
                        .when(HAS_NO_SILK_TOUCH)
                        .add(LootItem.lootTableItem(ModItems.TREE_LARVA.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .when(LootItemRandomChanceCondition.randomChance(0.12f))
                        ));
    }


}

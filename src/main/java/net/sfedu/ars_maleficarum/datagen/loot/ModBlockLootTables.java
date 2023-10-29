package net.sfedu.ars_maleficarum.datagen.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.MarigoldCropBlock;
import net.sfedu.ars_maleficarum.block.custom.SageCropBlock;
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

        generateSageCropDrop();
        generateMarigoldCropDrop();
        //Блоки, которые при ломании дропают сами себя
        this.dropSelf(ModBlocks.CURSED_GOLD_BLOCK.get());
        this.dropSelf(ModBlocks.SILVER_BLOCK.get());
        this.dropSelf(ModBlocks.SILVER_ORE_BLOCK.get());
        this.dropSelf(ModBlocks.CURSED_GOLD_ORE_BLOCK.get());

        this.dropSelf(ModBlocks.ROWAN_LOG.get());
        this.dropSelf(ModBlocks.ROWAN_WOOD.get());
        this.dropSelf(ModBlocks.ROWAN_PLANKS.get());
        this.dropSelf(ModBlocks.ROWAN_SAPLING .get());

        this.add(ModBlocks.ROWAN_LEAVES.get(),(block)->
                createLeavesDropsWithAdditionalItem(block,ModBlocks.ROWAN_LEAVES.get(),ModItems.SAGE_FLOWER.get(),NORMAL_LEAVES_SAPLING_CHANCES));
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
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SageCropBlock.AGE,3));

        //Выпадение предметов при сборе созревшей культуры
        this.add(ModBlocks.SAGE_CROP.get(),createCropDropsWith2Items(ModBlocks.SAGE_CROP.get(), ModItems.SAGE_FLOWER.get(), ModItems.SAGE_LEAF.get(),
                ModItems.SAGE_SEED.get(), lootitemcondition$builder));

    }


    protected void generateMarigoldCropDrop() {
        //Вынесенная отдельно процедура регистрации выпадения предметов при сборе культуры календулы
        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.MARIGOLD_CROP.get())
                .setProperties((StatePropertiesPredicate.Builder.properties().hasProperty(MarigoldCropBlock.AGE,3)));

        //Выпадение предметов при сборе созревшей культуры
        this.add(ModBlocks.MARIGOLD_CROP.get(),createCropDrops(ModBlocks.MARIGOLD_CROP.get(),ModItems.MARIGOLD_FLOWER.get(),
                ModItems.MARIGOLD_SEED.get(),lootitemcondition$builder));
    }

    //Дополнительная функция дропа, которой можно пользоваться, если нужно, чтобы с созревшей культуры падало 2 предмета, помимо семян.
    protected LootTable.Builder createCropDropsWith2Items(Block pCropBlock, Item pGrownCropItem1,Item pGrownCropItem2, Item pSeedsItem, LootItemCondition.Builder pDropGrownCropCondition) {
        return this.applyExplosionDecay(pCropBlock, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem1).when(pDropGrownCropCondition).otherwise(LootItem.lootTableItem(pSeedsItem)))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem2).when(pDropGrownCropCondition))).withPool(LootPool.lootPool().when(pDropGrownCropCondition).add(LootItem.lootTableItem(pSeedsItem).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    //Дополнительная функция выпадения с листвы при ломании ещё одного предмета
    protected LootTable.Builder createLeavesDropsWithAdditionalItem(Block pOakLeavesBlock, Block pSaplingBlock, Item item, float... pChances) {
        return this.createLeavesDrops(pOakLeavesBlock, pSaplingBlock, pChances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(this.applyExplosionCondition(pOakLeavesBlock, LootItem.lootTableItem(item)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }
}

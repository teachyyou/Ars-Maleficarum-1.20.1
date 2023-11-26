package net.sfedu.ars_maleficarum.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.loot.AddItemModifier;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, ArsMaleficarum.MOD_ID);
    }


    @Override
    protected void start() {
        //Выпадение семян шалфея из травы
        add("sage_seeds_from_grass",new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.2f).build(),
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build()
                }, ModItems.SAGE_SEED.get()));
        //Выпадение семян шалфея из высокой травы
        add("sage_seeds_from_grass",new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.2f).build(),
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build()
        }, ModItems.SAGE_SEED.get()));
        //Выпадение семян календулы из травы
        add("marigold_seeds_from_grass",new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.2f).build(),
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build()
        }, ModItems.MARIGOLD_SEED.get()));
        //Выпадение семян календулы из высокой травы
        add("marigold_seeds_from_grass",new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.2f).build(),
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build()
        }, ModItems.MARIGOLD_SEED.get()));

        //Выпадение крыла из летучей мыши
        add("bat_wing_from_bat",new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/bat")).build(),
                LootItemRandomChanceCondition.randomChance(0.4f).build()}, ModItems.BAT_WING.get()));

        //Выпадение семян солнечного цветка
        add("sunlight_flower_seeds_from_dead_bush",new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DEAD_BUSH).build(),
                LootItemRandomChanceCondition.randomChance(0.2f).build(),
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build()
        }, ModItems.SUNLIGHT_FLOWER_SEED.get()));
    }
}

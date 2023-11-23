package net.sfedu.ars_maleficarum.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.datagen.custom.InfusingAltarRecipeBuilder;
import net.sfedu.ars_maleficarum.datagen.custom.OdourExtractorRecipeBuilder;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.recipe.ModRecipes;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    //Списки "Проклятых" блоков, которые могут переплавляться
    private static final List<ItemLike> CURSED_GOLD_ORE_BLOCKS = List.of(ModBlocks.CURSED_GOLD_ORE_BLOCK.get());
    private static final List<ItemLike> SILVER_ORE_BLOCKS = List.of(ModBlocks.SILVER_ORE_BLOCK.get());

    private static final List<ItemLike> NAMELESS_TREE_BLOCKS = List.of(ModBlocks.NAMELESS_TREE_LOG.get(),ModBlocks.NAMELESS_TREE_WOOD.get());
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    //Генерация .json файлов крафтов (создания) предметов
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        //Переплавка руд
        oreBlasting(pWriter, CURSED_GOLD_ORE_BLOCKS, RecipeCategory.MISC, ModItems.CURSED_GOLD.get(), 0.25f, 100, "cursed_gold");
        oreBlasting(pWriter, SILVER_ORE_BLOCKS, RecipeCategory.MISC, ModItems.SILVER.get(), 0.25f, 100, "silver");
        oreSmelting(pWriter, CURSED_GOLD_ORE_BLOCKS, RecipeCategory.MISC, ModItems.CURSED_GOLD.get(), 0.25f, 200, "cursed_gold");
        oreSmelting(pWriter, SILVER_ORE_BLOCKS, RecipeCategory.MISC, ModItems.SILVER.get(), 0.25f, 200, "silver");

        oreSmelting(pWriter, List.of(Items.POTION), RecipeCategory.MISC, ModItems.SALT.get(), 0.1f, 150, "salt");

        simpleCooking(pWriter,NAMELESS_TREE_BLOCKS,RecipeCategory.MISC,ModItems.NAMELESS_CHARCOAL.get(),0,400,"nameless_charcoal");


        //Крафт блока проклятого золота
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CURSED_GOLD_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',ModItems.CURSED_GOLD.get())
                .unlockedBy(getHasName(ModItems.CURSED_GOLD.get()),has(ModItems.CURSED_GOLD.get()))
                .save(pWriter);
        //Крафт блока серебра
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SILVER_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',ModItems.SILVER.get())
                .unlockedBy(getHasName(ModItems.SILVER.get()),has(ModItems.SILVER.get()))
                .save(pWriter);
        //Крафт слитка проклятого золота из самородков проклятого золота
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CURSED_GOLD.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',ModItems.CURSED_GOLD_NUGGET.get())
                .unlockedBy(getHasName(ModItems.CURSED_GOLD_NUGGET.get()),has(ModItems.CURSED_GOLD_NUGGET.get()))
                .save(pWriter);
        //Крафт слитка серебра из самородков серебра
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SILVER.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',ModItems.SILVER_NUGGET.get())
                .unlockedBy(getHasName(ModItems.SILVER_NUGGET.get()),has(ModItems.SILVER_NUGGET.get()))
                .save(pWriter);
        //Крафт-разбиение блока  серебра на 9 слитков серебра
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SILVER.get(), 9)
                .requires(ModBlocks.SILVER_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SILVER_BLOCK.get()),has(ModBlocks.SILVER_BLOCK.get()))
                .save(pWriter, new ResourceLocation("silver_block_from_cursed_silver"));
        //Крафт-разбиение блока проклятого золота на 9 слитков проклятого золота
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CURSED_GOLD.get(), 9)
                .requires(ModBlocks.CURSED_GOLD_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.CURSED_GOLD_BLOCK.get()),has(ModBlocks.CURSED_GOLD_BLOCK.get()))
                .save(pWriter, new ResourceLocation("cursed_gold_block_from_cursed_gold"));
        //Крафт-разбиение слитка  серебра на 9 самородков  серебра
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SILVER_NUGGET.get(), 9)
                .requires(ModItems.SILVER.get())
                .unlockedBy(getHasName(ModItems.SILVER.get()),has(ModItems.SILVER.get()))
                .save(pWriter);
        //Крафт-разбиение слитка проклятого золота на 9 самородков проклятого золота
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CURSED_GOLD_NUGGET.get(), 9)
                .requires(ModItems.CURSED_GOLD.get())
                .unlockedBy(getHasName(ModItems.CURSED_GOLD.get()),has(ModItems.CURSED_GOLD.get()))
                .save(pWriter);
        //Крафт каменного пестика из камня
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_PESTLE.get())
                .pattern("   ")
                .pattern("  #")
                .pattern(" # ")
                .define('#', Blocks.STONE)
                .unlockedBy(getHasName(Blocks.STONE),has(Blocks.STONE))
                .save(pWriter);
        //Крафт рябиновых досок из древесины
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,ModBlocks.ROWAN_PLANKS.get(),4)
                .requires(ModBlocks.ROWAN_LOG.get())
                .unlockedBy(getHasName(ModBlocks.ROWAN_LOG.get()),has(ModBlocks.ROWAN_LOG.get()))
                .save(pWriter, new ResourceLocation("rowan_planks_from_log"));
        //Крафт рябиновых досок из древесины
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,ModBlocks.ROWAN_PLANKS.get(),4)
                .requires(ModBlocks.ROWAN_WOOD.get())
                .unlockedBy(getHasName(ModBlocks.ROWAN_WOOD.get()),has(ModBlocks.ROWAN_WOOD.get()))
                .save(pWriter, new ResourceLocation("rowan_planks_from_wood"));
        //Крафт рябины из рябиновых бревен
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROWAN_WOOD.get(),3)
                .pattern("   ")
                .pattern(" ##")
                .pattern(" ##")
                .define('#', ModBlocks.ROWAN_LOG.get())
                .unlockedBy(getHasName(ModBlocks.ROWAN_LOG.get()),has(ModBlocks.ROWAN_LOG.get()))
                .save(pWriter);
        //Крафт рябиновой коры из рябины
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,ModItems.ROWAN_BARK.get(),2)
                .requires(ModBlocks.ROWAN_LOG.get())
                .requires(ModItems.FLINT_KNIFE.get())
                .unlockedBy(getHasName(ModBlocks.ROWAN_LOG.get()),has(ModBlocks.ROWAN_LOG.get()))
                .save(pWriter,new ResourceLocation("rowan_bark_from_oak"));
        //Крафт рябиновой коры из рябины
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.ROWAN_BARK.get(),2)
                .requires(ModBlocks.ROWAN_WOOD.get())
                .requires(ModItems.FLINT_KNIFE.get())
                .unlockedBy(getHasName(ModBlocks.ROWAN_WOOD.get()),has(ModBlocks.ROWAN_WOOD.get()))
                .save(pWriter,new ResourceLocation("rowan_bark_from_wood"));
        //Крафт досок безымянного дерева из древесины
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,ModBlocks.NAMELESS_TREE_PLANKS.get(),4)
                .requires(ModBlocks.NAMELESS_TREE_LOG.get())
                .unlockedBy(getHasName(ModBlocks.NAMELESS_TREE_LOG.get()),has(ModBlocks.NAMELESS_TREE_LOG.get()))
                .save(pWriter, new ResourceLocation("nameless_tree_planks_from_log"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,ModBlocks.NAMELESS_TREE_PLANKS.get(),4)
                .requires(ModBlocks.NAMELESS_TREE_WOOD.get())
                .unlockedBy(getHasName(ModBlocks.NAMELESS_TREE_WOOD.get()),has(ModBlocks.NAMELESS_TREE_WOOD.get()))
                .save(pWriter, new ResourceLocation("nameless_tree_planks_from_wood"));
        //Крафт безымянного дерева из бревен
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NAMELESS_TREE_WOOD.get(),3)
                .pattern("   ")
                .pattern(" ##")
                .pattern(" ##")
                .define('#', ModBlocks.NAMELESS_TREE_LOG.get())
                .unlockedBy(getHasName(ModBlocks.NAMELESS_TREE_LOG.get()),has(ModBlocks.NAMELESS_TREE_LOG.get()))
                .save(pWriter);
        //Крафт примитивного ножика
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.FLINT_KNIFE.get(),1)
                .pattern("  C")
                .pattern(" B ")
                .pattern("A  ")
                .define('A', Items.STICK)
                .define('B', Items.STRING)
                .define('C', Items.FLINT)
                .unlockedBy(getHasName(Items.FLINT),has(Items.FLINT))
                .save(pWriter);
        //Крафт пустой печати
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMPTY_SEAL.get(),1)
                .pattern("@#@")
                .pattern("###")
                .pattern("@#@")
                .define('#', Items.IRON_INGOT)
                .define('@', ModItems.SILVER_NUGGET.get())
                .unlockedBy(getHasName(Items.IRON_INGOT),has(Items.IRON_INGOT))
                .save(pWriter);
        //Крафт ядра восприятия
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PERCEPTION_CORE.get(),1)
                .pattern("@!@")
                .pattern("#$%")
                .pattern("@^@")
                .define('@', Items.QUARTZ)
                .define('!', Items.GOLD_INGOT)
                .define('#', Items.DIAMOND)
                .define('$', ModItems.EMPTY_SEAL.get())
                .define('%', Items.COAL)
                .define('^', Items.LAPIS_LAZULI)
                .unlockedBy(getHasName(ModItems.EMPTY_SEAL.get()),has(ModItems.EMPTY_SEAL.get()))
                .save(pWriter);
        //Крафт дара нищего рудокопа
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CARBON_DETECTOR.get(),1)
                .pattern("GCG")
                .pattern("CRC")
                .pattern("GCG")
                .define('G', Blocks.GLASS)
                .define('C', Items.COAL)
                .define('R', ModItems.PERCEPTION_CORE.get())
                .unlockedBy(getHasName(ModItems.PERCEPTION_CORE.get()),has(ModItems.PERCEPTION_CORE.get()))
                .save(pWriter);
        //Крафт дара смуглого металлурга
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.METAL_DETECTOR.get(),1)
                .pattern("ABA")
                .pattern("CDE")
                .pattern("AFA")
                .define('A', Items.COPPER_INGOT)
                .define('B', ModItems.CURSED_GOLD.get())
                .define('C', Items.GOLD_INGOT)
                .define('E', Items.IRON_INGOT)
                .define('F', ModItems.SILVER.get())
                .define('D', ModItems.PERCEPTION_CORE.get())
                .unlockedBy(getHasName(ModItems.PERCEPTION_CORE.get()),has(ModItems.PERCEPTION_CORE.get()))
                .save(pWriter);
        //Крафт дара краснощёкого мудреца
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VALUABLE_DETECTOR.get(),1)
                .pattern("ABA")
                .pattern("CDE")
                .pattern("AFA")
                .define('A', Items.QUARTZ)
                .define('B', Items.AMETHYST_SHARD)
                .define('C', Items.EMERALD)
                .define('E', Items.LAPIS_LAZULI)
                .define('F', Items.REDSTONE)
                .define('D', ModItems.PERCEPTION_CORE.get())
                .unlockedBy(getHasName(ModItems.PERCEPTION_CORE.get()),has(ModItems.PERCEPTION_CORE.get()))
                .save(pWriter);
        //Крафт деревянной ступки и пестика
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.WOODEN_MORTAR_AND_PESTLE.get(),1)
                .requires(Items.BOWL)
                .requires(Items.STICK)
                .unlockedBy(getHasName(Items.BOWL),has(Items.BOWL))
                .save(pWriter,new ResourceLocation("wooden_mortar_and_pestle_craft"));
        //Крафт каменной ступки
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_MORTAR.get(),1)
                .pattern("   ")
                .pattern("# #")
                .pattern("$#$")
                .define('#', Blocks.STONE)
                .define('$', Blocks.STONE_SLAB)
                .unlockedBy(getHasName(Blocks.STONE),has(Blocks.STONE))
                .save(pWriter);
        //Крафт каменной ступки и пестика
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.STONE_MORTAR_AND_PESTLE.get(),1)
                .requires(ModItems.STONE_MORTAR.get())
                .requires(ModItems.STONE_PESTLE.get())
                .unlockedBy(getHasName(ModItems.STONE_MORTAR.get()),has(ModItems.STONE_MORTAR.get()))
                .save(pWriter,new ResourceLocation("stone_mortar_and_pestle_craft"));
        //Крафт сахара при помощи деревянной ступки и пестика
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.SUGAR,2)
                .requires(Items.SUGAR_CANE)
                .requires(ModItems.WOODEN_MORTAR_AND_PESTLE.get())
                .unlockedBy(getHasName(ModItems.WOODEN_MORTAR_AND_PESTLE.get()),has(ModItems.WOODEN_MORTAR_AND_PESTLE.get()))
                .save(pWriter,new ResourceLocation("sugar_from_wooden_mortar_and_pestle"));
        //Крафт сахара при помощи каменной ступки и пестика
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.SUGAR,2)
                .requires(Items.SUGAR_CANE)
                .requires(ModItems.STONE_MORTAR_AND_PESTLE.get())
                .unlockedBy(getHasName(ModItems.STONE_MORTAR_AND_PESTLE.get()),has(ModItems.STONE_MORTAR_AND_PESTLE.get()))
                .save(pWriter,new ResourceLocation("sugar_from_stone_mortar_and_pestle"));
        //Крафт блока соли
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SALT_BLOCK.get())
                .pattern("   ")
                .pattern("## ")
                .pattern("## ")
                .define('#',ModItems.SALT.get())
                .unlockedBy(getHasName(ModItems.SALT.get()),has(ModItems.SALT.get()))
                .save(pWriter);
        //Крафт маринованной личинки
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.FERMENTED_TREE_LARVA.get(),1)
                .requires(Items.SUGAR)
                .requires(ModItems.SALT.get())
                .requires(ModItems.TREE_LARVA.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy(getHasName(ModItems.TREE_LARVA.get()),has(ModItems.TREE_LARVA.get()))
                .save(pWriter,new ResourceLocation("fermented_tree_larva_craft"));
        //Крафт серебряного стилета
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SILVER_DAGGER.get())
                .pattern(" SS")
                .pattern(" RS")
                .pattern("N  ")
                .define('S',ModItems.SILVER.get())
                .define('R',Items.STICK)
                .define('N',ModItems.CURSED_GOLD_NUGGET.get())
                .unlockedBy(getHasName(ModItems.SILVER.get()),has(ModItems.SILVER.get()))
                .save(pWriter);

        //Генерация крафтов в новой печке
        new OdourExtractorRecipeBuilder(ModItems.SAGE_LEAF.get(),Items.GHAST_TEAR,Items.DIAMOND,true,0.5F,1)
                .unlockedBy("has_sage_leaf",has(Items.DIAMOND)).save(pWriter);
        new OdourExtractorRecipeBuilder(ModItems.SAGE_FLOWER.get(),Items.DIAMOND,Items.STICK,false,1,1)
                .unlockedBy("has_sage_flower",has(Items.DIAMOND)).save(pWriter);

        //Генерация крафтов на новом алтаре
        new InfusingAltarRecipeBuilder(List.of(Items.STICK,Items.STICK,Items.STICK,Items.STICK,Items.STICK,ModItems.WOODEN_FIGURE.get()),Items.NETHER_STAR,"overworld")
                .unlockedBy("has_something",has(Items.STICK)).save(pWriter);
        new InfusingAltarRecipeBuilder(List.of(Items.WHITE_WOOL,Items.WHITE_WOOL,Items.WHITE_WOOL,Items.WHITE_WOOL,Items.WHITE_WOOL,ModItems.POPPET.get()),Items.DRAGON_EGG,"overworld")
                .unlockedBy("has_something",has(Items.WHITE_WOOL)).save(pWriter);



    }
    //Генерация .json файлов для блоков, которые могут быть переплавлены
    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");

    }

    protected static void simpleCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory,
                                        ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        simpleCookingRecipeBuilder(pFinishedRecipeConsumer,RecipeSerializer.SMELTING_RECIPE,pIngredients,pCategory,pResult,pExperience,pCookingTime,pGroup,"_from_smelting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients,
                                     RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, ArsMaleficarum.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

    protected static void simpleCookingRecipeBuilder(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients,
                                        RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike),pCategory,pResult,
                    pExperience,pCookingTime,pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike),has(itemlike))
                    .save(pFinishedRecipeConsumer,ArsMaleficarum.MOD_ID+":"+getItemName(pResult)+pRecipeName+"_"+getItemName(itemlike));
        }
    }


}

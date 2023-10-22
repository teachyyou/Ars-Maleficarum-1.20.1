package net.sfedu.ars_maleficarum.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    //Списки "Проклятых" блоков, которые могут переплавляться
    private static final List<ItemLike> CURSED_GOLD_ORE_BLOCKS = List.of(ModBlocks.CURSED_GOLD_ORE_BLOCK.get());
    private static final List<ItemLike> CURSED_SILVER_ORE_BLOCKS = List.of(ModBlocks.CURSED_SILVER_ORE_BLOCK.get());
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    //Генерация .json файлов крафтов (создания) предметов
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        //Переплавка руд
        oreBlasting(pWriter, CURSED_GOLD_ORE_BLOCKS, RecipeCategory.MISC, ModItems.CURSED_GOLD.get(), 0.35f, 100, "cursed_gold");
        oreBlasting(pWriter, CURSED_SILVER_ORE_BLOCKS, RecipeCategory.MISC, ModItems.CURSED_SILVER.get(), 0.25f, 100, "cursed_silver");
        oreSmelting(pWriter, CURSED_GOLD_ORE_BLOCKS, RecipeCategory.MISC, ModItems.CURSED_GOLD.get(), 0.35f, 100, "cursed_gold");
        oreSmelting(pWriter, CURSED_SILVER_ORE_BLOCKS, RecipeCategory.MISC, ModItems.CURSED_SILVER.get(), 0.25f, 100, "cursed_silver");
        //Крафт блока проклятого золота
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CURSED_GOLD_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',ModItems.CURSED_GOLD.get())
                .unlockedBy(getHasName(ModItems.CURSED_GOLD.get()),has(ModItems.CURSED_GOLD.get()))
                .save(pWriter);
        //Крафт блока проклятого серебра
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CURSED_SILVER_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',ModItems.CURSED_SILVER.get())
                .unlockedBy(getHasName(ModItems.CURSED_SILVER.get()),has(ModItems.CURSED_SILVER.get()))
                .save(pWriter);
        //Крафт слитка проклятого золота из самородков проклятого золота
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CURSED_GOLD.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',ModItems.CURSED_GOLD_NUGGET.get())
                .unlockedBy(getHasName(ModItems.CURSED_GOLD_NUGGET.get()),has(ModItems.CURSED_GOLD_NUGGET.get()))
                .save(pWriter);
        //Крафт слитка проклятого серебра из самородков проклятого серебра
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CURSED_SILVER.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',ModItems.CURSED_SILVER_NUGGET.get())
                .unlockedBy(getHasName(ModItems.CURSED_SILVER_NUGGET.get()),has(ModItems.CURSED_SILVER_NUGGET.get()))
                .save(pWriter);
        //Крафт-разбиение блока проклятого серебра на 9 слитков проклятого серебра
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CURSED_SILVER.get(), 9)
                .requires(ModBlocks.CURSED_SILVER_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.CURSED_SILVER_BLOCK.get()),has(ModBlocks.CURSED_SILVER_BLOCK.get()))
                .save(pWriter, new ResourceLocation("cursed_silver_block_from_cursed_silver"));
        //Крафт-разбиение блока проклятого золота на 9 слитков проклятого золота
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CURSED_GOLD.get(), 9)
                .requires(ModBlocks.CURSED_GOLD_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.CURSED_GOLD_BLOCK.get()),has(ModBlocks.CURSED_GOLD_BLOCK.get()))
                .save(pWriter, new ResourceLocation("cursed_gold_block_from_cursed_gold"));
        //Крафт-разбиение слитка проклятого серебра на 9 самородков проклятого серебра
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CURSED_SILVER_NUGGET.get(), 9)
                .requires(ModItems.CURSED_SILVER.get())
                .unlockedBy(getHasName(ModItems.CURSED_SILVER.get()),has(ModItems.CURSED_SILVER.get()))
                .save(pWriter);
        //Крафт-разбиение слитка проклятого золота на 9 самородков проклятого золота
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CURSED_GOLD_NUGGET.get(), 9)
                .requires(ModItems.CURSED_GOLD.get())
                .unlockedBy(getHasName(ModItems.CURSED_GOLD.get()),has(ModItems.CURSED_GOLD.get()))
                .save(pWriter);
    }
    //Генерация .json файлов для блоков, которые могут быть переплавлены
    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, ArsMaleficarum.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}

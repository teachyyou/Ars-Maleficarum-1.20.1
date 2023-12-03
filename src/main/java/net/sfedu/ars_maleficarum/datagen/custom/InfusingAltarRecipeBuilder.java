package net.sfedu.ars_maleficarum.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.recipe.InfusingAltarRecipe;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class InfusingAltarRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final List<Ingredient> components=new ArrayList<Ingredient>();
    private final String dimension;

    public InfusingAltarRecipeBuilder(List<ItemLike> ingredient, ItemLike result, String dimension) {
        for (ItemLike ingr : ingredient) {
            components.add(Ingredient.of(ingr));
        }
        this.result = result.asItem();
        this.dimension=dimension;

    }
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new InfusingAltarRecipeBuilder.Result(pRecipeId, this.result, this.components, this.dimension,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final List<Ingredient> components;
        private final String dimension;

        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, List<Ingredient> components, String dimension, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.components=components;
            this.id = pId;
            this.result = pResult;
            this.dimension=dimension;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray jsonarray = new JsonArray();
            for (Ingredient ingr : components) {
                jsonarray.add(ingr.toJson());
            }
            pJson.add("ingredients", jsonarray);

            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());


            pJson.add("output", jsonobject);
            pJson.addProperty("dimension",this.dimension);

        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(ArsMaleficarum.MOD_ID,
                    ForgeRegistries.ITEMS.getKey(this.result).getPath() + "_in_infusing_altar");
        }

        @Override
        public RecipeSerializer<?> getType() {
            return InfusingAltarRecipe.Serializer.INSTANCE;
        }

        @javax.annotation.Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @javax.annotation.Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}

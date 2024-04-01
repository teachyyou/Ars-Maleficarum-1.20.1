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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.recipe.BrewingCauldronRecipe;
import net.sfedu.ars_maleficarum.recipe.InfusingAltarRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BrewingCauldronRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final List<Ingredient> components=new ArrayList<Ingredient>();

    public BrewingCauldronRecipeBuilder(List<ItemLike> ingredient, ItemLike result) {
        for (ItemLike ingr : ingredient) {
            components.add(Ingredient.of(ingr));
        }
        while (components.size() < 10) components.add(Ingredient.EMPTY);
        this.result = result.asItem();

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

        pFinishedRecipeConsumer.accept(new BrewingCauldronRecipeBuilder.Result(pRecipeId, this.result, this.components,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final List<Ingredient> components;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, List<Ingredient> components, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.components=components;
            this.id = pId;
            this.result = pResult;
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

        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(ArsMaleficarum.MOD_ID,
                    ForgeRegistries.ITEMS.getKey(this.result).getPath() + "_in_brewing_cauldron");
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BrewingCauldronRecipe.Serializer.INSTANCE;
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

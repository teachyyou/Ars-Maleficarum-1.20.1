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
import net.sfedu.ars_maleficarum.recipe.OdourExtractingRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.function.Consumer;

public class OdourExtractorRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final int count;
    private final Item additional;
    private final boolean isBottleRequired;
    private final float chance;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public OdourExtractorRecipeBuilder(ItemLike ingredient, ItemLike result, ItemLike additional, boolean isBottleRequired, float chance, int count) {
        this.ingredient = Ingredient.of(ingredient);
        this.result = result.asItem();
        this.additional = additional.asItem();
        this.isBottleRequired = isBottleRequired;
        this.chance = chance;
        this.count = count;

    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    @NotNull
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    @NotNull
    public Item getResult() {
        return result;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.result, this.count, this.ingredient, this.additional, this.isBottleRequired, this.chance,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient ingredient;
        private final int count;

        private final Item additional;
        private final boolean isBottleRequired;
        private final float chance;

        private final Advancement.Builder advancement;
        //доброе утро, кисоньки
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, int pCount, Ingredient ingredient, Item additional, boolean isBottleRequired, float chance, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.additional = additional.asItem();
            this.isBottleRequired = isBottleRequired;
            this.chance = chance;
            this.count = pCount;
            this.ingredient = ingredient;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray jsonarray = new JsonArray();
            jsonarray.add(ingredient.toJson());
            pJson.add("ingredients", jsonarray);

            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }


            JsonObject addit = new JsonObject();
            addit.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.additional)).toString());


            pJson.add("output", jsonobject);
            pJson.add("additional", addit);
            pJson.addProperty("chance",this.chance);
            pJson.addProperty("isBottleRequired",this.isBottleRequired);

        }

        @Override
        @NotNull
        public ResourceLocation getId() {
            return new ResourceLocation(ArsMaleficarum.MOD_ID, this.id.getPath());
        }

        @Override
        @NotNull
        public RecipeSerializer<?> getType() {
            return OdourExtractingRecipe.Serializer.INSTANCE;
        }

        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}

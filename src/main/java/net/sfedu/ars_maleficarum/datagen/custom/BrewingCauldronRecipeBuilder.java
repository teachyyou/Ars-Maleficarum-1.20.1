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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class BrewingCauldronRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final List<Ingredient> components=new ArrayList<>();
    private final boolean inOrder;
    private final int craftType;

    public BrewingCauldronRecipeBuilder(List<ItemLike> ingredient, ItemLike result, boolean inOrder, int craftType) {
        for (ItemLike ingr : ingredient) {
            components.add(Ingredient.of(ingr));
        }
        while (components.size() < 10) components.add(Ingredient.EMPTY);
        this.result = result.asItem();
        this.inOrder = inOrder;
        this.craftType = craftType;

    }
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
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

        pFinishedRecipeConsumer.accept(new BrewingCauldronRecipeBuilder.Result(this.result, this.components, this.inOrder, this.craftType,
                this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/"
                + pRecipeId.getPath())));

    }

    public static class Result implements FinishedRecipe {
        private final Item result;
        private final List<Ingredient> components;
        private final boolean inOrder;
        private final int craftType;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(Item pResult, List<Ingredient> components, boolean inOrder, int craftType, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.components=components;
            this.result = pResult;
            this.inOrder = inOrder;
            this.craftType = craftType;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
            JsonArray jsonarray = new JsonArray();
            for (Ingredient ingr : components) {
                jsonarray.add(ingr.toJson());
            }
            pJson.add("ingredients", jsonarray);

            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());


            pJson.add("output", jsonobject);

            jsonobject = new JsonObject();
            jsonobject.addProperty("order", inOrder);
            pJson.add("inOrder", jsonobject);

            JsonObject jsonobject2 = new JsonObject();
            jsonobject2.addProperty("craft", craftType);
            pJson.add("craftType", jsonobject2);

        }

        @Override
        @NotNull
        public ResourceLocation getId() {
            return new ResourceLocation(ArsMaleficarum.MOD_ID,
                    Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).getPath() + "_in_brewing_cauldron");
        }

        @Override
        @NotNull
        public RecipeSerializer<?> getType() {
            return BrewingCauldronRecipe.Serializer.INSTANCE;
        }

        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}

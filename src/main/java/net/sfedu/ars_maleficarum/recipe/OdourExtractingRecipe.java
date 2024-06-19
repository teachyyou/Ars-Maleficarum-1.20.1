package net.sfedu.ars_maleficarum.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class OdourExtractingRecipe implements Recipe<Container>{

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private ItemStack additional = ItemStack.EMPTY;

    private final boolean isBottleRequired;
    private final float chance;
    private final ResourceLocation id;

    public OdourExtractingRecipe(ResourceLocation id, ItemStack output, ItemStack additional, boolean isBottleRequired, float chance, NonNullList<Ingredient> inputItems) {
        this.inputItems = inputItems;
        this.output = output;
        this.additional = additional;
        this.isBottleRequired = isBottleRequired;
        this.chance=chance;
        this.id = id;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        if (pLevel.isClientSide) {
            return false;
        }

        return inputItems.get(0).test(pContainer.getItem(0));
    }

    @Override
    @NotNull
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    public boolean getIsBottleRequired() {
        return isBottleRequired;
    }

    public float getChance() {
        return chance;
    }

    @Override
    @NotNull
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    //for patchouli
    @NotNull
    public ItemStack getIngredient() {
        return inputItems.get(0).getItems()[0];
    }

    public ItemStack getAdditionalItem() {
        return additional.copy();
    }
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<OdourExtractingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "odour_extracting";
    }

    public static class Serializer implements RecipeSerializer<OdourExtractingRecipe> {
        public static final OdourExtractingRecipe.Serializer INSTANCE = new OdourExtractingRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ArsMaleficarum.MOD_ID,"odour_extracting");

        @Override
        public OdourExtractingRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"output"));
            ItemStack additional = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"additional"));

            boolean isBottleRequired = GsonHelper.getAsBoolean(json,"isBottleRequired");
            float chance = GsonHelper.getAsFloat(json, "chance");

            JsonArray ingredients = GsonHelper.getAsJsonArray(json,"ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }
            return new OdourExtractingRecipe(id,output,additional,isBottleRequired,chance,inputs);
        }

        @Override
        public @Nullable OdourExtractingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(),Ingredient.EMPTY);

            for (int i = 0; i < inputs.size();i++) {
                inputs.set(i,Ingredient.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            ItemStack additional = buf.readItem();
            boolean isBottleRequired = buf.readBoolean();
            float chance = buf.readFloat();
            return new OdourExtractingRecipe(id,output,additional,isBottleRequired,chance,inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, OdourExtractingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing: recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null),false);
            buf.writeItemStack(recipe.getAdditionalItem(),false);
            buf.writeBoolean(recipe.getIsBottleRequired());
            buf.writeFloat(recipe.getChance());
        }
    }
}

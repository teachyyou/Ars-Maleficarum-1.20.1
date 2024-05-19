package net.sfedu.ars_maleficarum.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import org.jetbrains.annotations.Nullable;

public class InfusingAltarRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final String dimension;
    private final ResourceLocation id;

    InfusingAltarRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems, String dimension) {
        this.id=id;
        this.inputItems=inputItems;
        this.output=output;
        this.dimension=dimension;
    }



    //Очень костыльно(
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {

        if (pLevel.isClientSide()) {
            return false;
        }
        for (int i = 0; i<6; i++) {
            if (!(inputItems.get(i).test(pContainer.getItem(i)))) return false;
        }
        return true;
    }


    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }
    public String getDimension(RegistryAccess pRegistryAccess) {
        return dimension;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<InfusingAltarRecipe> {
        private  Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "altar_infusing";
    }

    public static class Serializer implements RecipeSerializer<InfusingAltarRecipe> {
        public static final InfusingAltarRecipe.Serializer INSTANCE = new InfusingAltarRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ArsMaleficarum.MOD_ID,"altar_infusing");

        @Override
        public InfusingAltarRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"output"));

            String dimension = GsonHelper.getAsString(json,"dimension");

            JsonArray ingredients = GsonHelper.getAsJsonArray(json,"ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(6, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }
            return new InfusingAltarRecipe(id,output,inputs,dimension);
        }

        @Override
        public @Nullable InfusingAltarRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(),Ingredient.EMPTY);
            for (int i = 0; i < inputs.size();i++) {
                inputs.set(i,Ingredient.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            String dimension = buf.readUtf();
            return new InfusingAltarRecipe(id,output,inputs,dimension);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, InfusingAltarRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing: recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null),false);
            buf.writeUtf(recipe.getDimension(null));
        }
    }
}

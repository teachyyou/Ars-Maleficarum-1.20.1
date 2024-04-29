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
import org.apache.http.client.utils.CloneUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BrewingCauldronRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final boolean inOrder;
    public final int craftType;

    BrewingCauldronRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems, boolean inOrder, int craftType) {
        this.id=id;
        this.inputItems=inputItems;
        this.output=output;
        this.inOrder = inOrder;
        this.craftType = craftType;
    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {

        if (pLevel.isClientSide()) {
            return false;
        }
        if (inOrder)
        {
            for (int i = 0; i<10; i++) {
                if (!(inputItems.get(i).test(pContainer.getItem(i)))) return false;
            }
        }
        else
        {
            for (int i = 0; i<10; i++) {
                if (containerCount(pContainer) - inputCount() > 2) return false;
                if (!containerContains(inputItems.get(i), pContainer)) return false;
            }
        }
        return true;
    }

    private int containerCount(SimpleContainer cont)
    {
        int res = 0;
        for ( int i = 0; i < 10; i++)
        {
            if (!cont.getItem(i).isEmpty()) res++;
        }
        return res;
    }

    private int inputCount()
    {
        int res = 0;
        for ( int i = 0; i < 10; i++)
        {
            if (!inputItems.get(i).isEmpty()) res++;
        }
        return res;
    }

    private boolean containerContains(Ingredient input, SimpleContainer cont)
    {
        for ( int i = 0; i < 10; i++)
        {
            if (input.test(cont.getItem(i))) return true;
        }
        return false;
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

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BrewingCauldronRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "cauldron_brewing";
    }

    public static class Serializer implements RecipeSerializer<BrewingCauldronRecipe> {
        public static final BrewingCauldronRecipe.Serializer INSTANCE = new BrewingCauldronRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ArsMaleficarum.MOD_ID,"cauldron_brewing");

        @Override
        public BrewingCauldronRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json,"ingredients");
            boolean inOrder = GsonHelper.getAsBoolean(GsonHelper.getAsJsonObject(json, "inOrder"), "order");
            int craftType = GsonHelper.getAsInt(GsonHelper.getAsJsonObject(json, "craftType"), "craft");
            NonNullList<Ingredient> inputs = NonNullList.withSize(10, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }
            return new BrewingCauldronRecipe(id,output,inputs, inOrder, craftType);
        }

        @Override
        public @Nullable BrewingCauldronRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(),Ingredient.EMPTY);
            for (int i = 0; i < inputs.size();i++) {
                inputs.set(i,Ingredient.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            boolean inOrder = buf.readBoolean();
            int craftType = buf.readInt();
            return new BrewingCauldronRecipe(id,output,inputs, inOrder, craftType);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, BrewingCauldronRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing: recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null),false);
            buf.writeBoolean(recipe.inOrder);
            buf.writeInt(recipe.craftType);
        }
    }
}

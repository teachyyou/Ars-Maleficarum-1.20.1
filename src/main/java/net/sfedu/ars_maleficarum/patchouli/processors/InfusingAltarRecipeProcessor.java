package net.sfedu.ars_maleficarum.patchouli.processors;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.recipe.InfusingAltarRecipe;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.List;

public class InfusingAltarRecipeProcessor implements IComponentProcessor {
    private List<ItemStack> input;
    private ItemStack resultItem;
    private String dimension;

    @Override
    public void setup(Level level, IVariableProvider iVariableProvider) {

        ResourceLocation recipeId = new ResourceLocation(iVariableProvider.get("recipe").asString());

        RecipeManager recipeManager = level.getRecipeManager();
        InfusingAltarRecipe recipe = (InfusingAltarRecipe) recipeManager.byKey(recipeId).orElseThrow(() -> new IllegalArgumentException("Could not find recipe for: " + recipeId));
        input = recipe.getIngredients().stream().filter(x->!x.isEmpty()).map(x-> x.getItems()[0]).toList();
        resultItem = recipe.getResultItem(level.registryAccess());
        dimension = recipe.getDimension();

    }


    @Override
    @NotNull
    public IVariable process(Level level, String key) {
        if(key.startsWith("input")) {
            int index = key.charAt(key.length()-1) - '0';
            return IVariable.from(input.get(index));
        }
        else if(key.startsWith("dimension_tooltip"))
            return IVariable.wrap(Component.translatable("book.shadow_grimoire.dimension."+dimension).getString());
        else if(key.startsWith("result"))
            return IVariable.from(resultItem);
        else if(key.startsWith("dimension_flag"))
            return IVariable.wrap(!dimension.equals("any"));
        else if(key.startsWith("dimension_icon"))
            return IVariable.wrap("ars_maleficarum:textures/books/shadow_grimoire/util_textures/" + dimension + ".png");
        return IVariable.empty();
    }
}

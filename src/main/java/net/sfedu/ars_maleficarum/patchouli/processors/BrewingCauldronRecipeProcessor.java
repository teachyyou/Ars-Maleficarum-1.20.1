package net.sfedu.ars_maleficarum.patchouli.processors;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.recipe.BrewingCauldronRecipe;
import net.sfedu.ars_maleficarum.recipe.OdourExtractingRecipe;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BrewingCauldronRecipeProcessor implements IComponentProcessor {
    private BrewingCauldronRecipe recipe;

    private List<ItemStack> input;
    private ItemStack resultItem;
    private ItemStack collectItem;

    @Override
    public void setup(Level level, IVariableProvider iVariableProvider) {
        ResourceLocation recipeId = new ResourceLocation(iVariableProvider.get("recipe").asString());

        RecipeManager recipeManager = level.getRecipeManager();
        recipe = (BrewingCauldronRecipe) recipeManager.byKey(recipeId).orElseThrow(()->new IllegalArgumentException("Could not find recipe for: " + recipeId));
        input = recipe.getIngredients().stream().map(x-> x.getItems().length>0 ? x.getItems()[0] : ItemStack.EMPTY).toList();
        resultItem = recipe.getResultItem(null);

        collectItem = switch (recipe.craftType) {
            default -> ItemStack.EMPTY;
            case 1 -> new ItemStack(Items.GLASS_BOTTLE);
            case 2 -> new ItemStack(Items.BOWL);
        };

    }


    //todo придумать что-то с симметричным отображением предметов, если их меньше 10.
    @Override
    public IVariable process(Level level, String key) {
        if(key.startsWith("input")) {
            int index = key.charAt(key.length()-1) - '0';
            if (index < input.size()) {
                return IVariable.from(input.get(index));
            }
            return IVariable.empty();
        }
        else if(key.startsWith("collectItem"))
            return IVariable.from(collectItem);
        else if(key.startsWith("result"))
            return IVariable.from(resultItem);
        return null;
    }
}

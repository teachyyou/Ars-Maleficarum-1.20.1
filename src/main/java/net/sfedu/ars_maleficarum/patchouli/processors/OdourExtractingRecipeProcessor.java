package net.sfedu.ars_maleficarum.patchouli.processors;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.recipe.OdourExtractingRecipe;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class OdourExtractingRecipeProcessor implements IComponentProcessor {
    private OdourExtractingRecipe recipe;
    private ItemStack inputItem;
    private ItemStack resultItem;
    private ItemStack additionalResult;
    private boolean isBottleRequired;

    @Override
    public void setup(Level level, IVariableProvider iVariableProvider) {
        ResourceLocation recipeId = new ResourceLocation(iVariableProvider.get("recipe").asString());

        RecipeManager recipeManager = level.getRecipeManager();
        recipe = (OdourExtractingRecipe) recipeManager.byKey(recipeId).orElseThrow(()->new IllegalArgumentException("Could not find recipe for: " + recipeId));
        inputItem = recipe.getIngredient();
        resultItem = recipe.getResultItem(null);
        additionalResult = recipe.getAdditionalItem(null);
        isBottleRequired = recipe.getIsBottleRequired(null);

    }

    @Override
    public IVariable process(Level level, String key) {
        if(key.startsWith("input"))
            return IVariable.from(inputItem);
        else if(key.startsWith("vial"))
            return IVariable.from(isBottleRequired ? new ItemStack(ModItems.EMPTY_VIAL.get()) : new ItemStack(Items.AIR));
        else if(key.startsWith("result"))
            return IVariable.from(resultItem);
        else if(key.startsWith("additionalResult"))
            return IVariable.from(additionalResult);

        return null;
    }
}

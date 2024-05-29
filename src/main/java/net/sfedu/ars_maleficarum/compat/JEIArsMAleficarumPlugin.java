package net.sfedu.ars_maleficarum.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.recipe.InfusingAltarRecipe;
import net.sfedu.ars_maleficarum.recipe.OdourExtractingRecipe;
import net.sfedu.ars_maleficarum.screen.InfusingAltarScreen;
import net.sfedu.ars_maleficarum.screen.OdourExtractorFurnaceScreen;

import java.util.List;

@JeiPlugin
public class JEIArsMAleficarumPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ArsMaleficarum.MOD_ID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new InfusingAltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new OdourExtractingFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<InfusingAltarRecipe> infusing_altar_recipes = recipeManager.getAllRecipesFor(InfusingAltarRecipe.Type.INSTANCE);
        registration.addRecipes(InfusingAltarRecipeCategory.INFUSING_ALTAR_TYPE, infusing_altar_recipes);

        List<OdourExtractingRecipe> odour_extracting_furnace_recipes = recipeManager.getAllRecipesFor(OdourExtractingRecipe.Type.INSTANCE);
        registration.addRecipes(OdourExtractingFurnaceRecipeCategory.ODOUR_EXTRACTING_TYPE,odour_extracting_furnace_recipes);

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //Either place it correctly or remove completely - looks weird at that point
        //registration.addRecipeClickArea(InfusingAltarScreen.class,60,30,20,30,InfusingAltarRecipeCategory.INFUSING_ALTAR_TYPE);
        //registration.addRecipeClickArea(OdourExtractorFurnaceScreen.class,20,30,20,30,OdourExtractingFurnaceRecipeCategory.ODOUR_EXTRACTING_TYPE);
    }
}

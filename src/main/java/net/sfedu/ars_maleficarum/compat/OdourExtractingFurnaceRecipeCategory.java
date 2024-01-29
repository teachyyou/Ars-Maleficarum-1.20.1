package net.sfedu.ars_maleficarum.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.recipe.OdourExtractingRecipe;

public class OdourExtractingFurnaceRecipeCategory implements IRecipeCategory<OdourExtractingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ArsMaleficarum.MOD_ID,"odour_extracting");
    public static final ResourceLocation TEXTURES = new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/gui/odour_extracting_furnace_gui.png");
    public static final RecipeType<OdourExtractingRecipe> ODOUR_EXTRACTING_TYPE = new RecipeType<>(UID, OdourExtractingRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public OdourExtractingFurnaceRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURES,0,0,176,98);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ODOUR_EXTRACTING_FURNACE.get()));
    }

    @Override
    public RecipeType<OdourExtractingRecipe> getRecipeType() {
        return ODOUR_EXTRACTING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Odour Extracting Furnace");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, OdourExtractingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,56,17).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,56,53).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT,83,53).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT,118,21).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.OUTPUT,118,53).addItemStack(recipe.getResultItem(null));

    }
}

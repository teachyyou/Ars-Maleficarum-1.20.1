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
import net.sfedu.ars_maleficarum.recipe.InfusingAltarRecipe;


public class InfusingAltarRecipeCategory implements IRecipeCategory<InfusingAltarRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ArsMaleficarum.MOD_ID,"altar_infusing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/gui/infusing_altar_gui1.png");
    public static final RecipeType<InfusingAltarRecipe> INFUSING_ALTAR_TYPE = new RecipeType<>(UID, InfusingAltarRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public InfusingAltarRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,176,98);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ModBlocks.INFUSING_ALTAR.get()));
    }

    @Override
    public RecipeType<InfusingAltarRecipe> getRecipeType() {
        return INFUSING_ALTAR_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("infusing_altar_crafts");
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
    public void setRecipe(IRecipeLayoutBuilder builder, InfusingAltarRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,44,8).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,116,8).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT,121,53).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT,80,77).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT,39,53).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.OUTPUT,80,40).addItemStack(recipe.getResultItem(null));
    }
}

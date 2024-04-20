package net.sfedu.ars_maleficarum.compat;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.recipe.InfusingAltarRecipe;


public class InfusingAltarRecipeCategory implements IRecipeCategory<InfusingAltarRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ArsMaleficarum.MOD_ID,"altar_infusing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/gui/infusing_altar_gui_jei.png");
    public static final RecipeType<InfusingAltarRecipe> INFUSING_ALTAR_TYPE = new RecipeType<>(UID, InfusingAltarRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> cachedPentagramProgress;

    public InfusingAltarRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,176,98);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ModBlocks.INFUSING_ALTAR.get()));
        this.cachedPentagramProgress = CacheBuilder.newBuilder()
                .maximumSize(300)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer burnTime) { //тут менял на -20 пробовал
                        return helper.drawableBuilder(TEXTURE, 183, 182, 210, 70)
                                .buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, false);
                    }
                });
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
        builder.addSlot(RecipeIngredientRole.INPUT,24,8).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,96,8).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT,101,53).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT,60,77).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT,19,53).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT,60,40).addIngredients(recipe.getIngredients().get(5));
        builder.addSlot(RecipeIngredientRole.OUTPUT,140,40).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void draw(InfusingAltarRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        int AltarProgressTime = 160;
        IDrawableAnimated AltarProgress = cachedPentagramProgress.getUnchecked(AltarProgressTime);
        AltarProgress.draw(guiGraphics,-48,20);
    }
}

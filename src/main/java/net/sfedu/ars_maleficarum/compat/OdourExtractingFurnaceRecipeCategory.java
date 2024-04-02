package net.sfedu.ars_maleficarum.compat;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.math.Constants;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.recipe.OdourExtractingRecipe;

import java.util.List;
public class OdourExtractingFurnaceRecipeCategory implements IRecipeCategory<OdourExtractingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ArsMaleficarum.MOD_ID,"odour_extracting");
    public static final ResourceLocation TEXTURES = new ResourceLocation(ArsMaleficarum.MOD_ID,"textures/gui/odour_extracting_furnace_gui.png");
    public static final RecipeType<OdourExtractingRecipe> ODOUR_EXTRACTING_TYPE = new RecipeType<>(UID, OdourExtractingRecipe.class);
    private final IDrawable background;
    List<ItemStack> list = List.of(ModItems.NAMELESS_CHARCOAL.get().getDefaultInstance(), Items.CHARCOAL.getDefaultInstance());
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> cachedFlames;
    private final LoadingCache<Integer, IDrawableAnimated> cachedProgressArrow;

    public OdourExtractingFurnaceRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURES,0,0,176,80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ODOUR_EXTRACTING_FURNACE.get()));
        this.cachedFlames = CacheBuilder.newBuilder()
                .maximumSize(27)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer burnTime) {
                        return helper.drawableBuilder(TEXTURES, 176, 0, 14, 12)
                                .buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, true);
                    }
                });
        this.cachedProgressArrow = CacheBuilder.newBuilder()
                .maximumSize(30)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer burnTime) {
                        return helper.drawableBuilder(TEXTURES, 176, 14, 26, 16)
                                .buildAnimated(burnTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    @Override
    public RecipeType<OdourExtractingRecipe> getRecipeType() {
        return ODOUR_EXTRACTING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("odour_extracting_furnace_crafts");
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
        builder.addSlot(RecipeIngredientRole.INPUT,56,53).addItemStacks(list);
        builder.addSlot(RecipeIngredientRole.CATALYST,83,53).addItemStack(recipe.getIsBottleRequired(null) ?
                ModItems.EMPTY_VIAL.get().getDefaultInstance() : ItemStack.EMPTY);
        builder.addSlot(RecipeIngredientRole.OUTPUT,118,53).addItemStack(recipe.getAdditionalItem(null));
        builder.addSlot(RecipeIngredientRole.OUTPUT,118,21).addItemStack(recipe.getResultItem(null));

    }

    @Override
    public void draw(OdourExtractingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        int burnTime = 200;
        int ProgressArrowTime = 100;
        IDrawableAnimated flame = cachedFlames.getUnchecked(burnTime);
        flame.draw(guiGraphics, 56, 37);
        IDrawableAnimated progress_arrow = cachedProgressArrow.getUnchecked(ProgressArrowTime);
        progress_arrow.draw(guiGraphics,79,20);
    }
}

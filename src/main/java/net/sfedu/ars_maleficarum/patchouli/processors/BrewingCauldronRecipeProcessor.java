package net.sfedu.ars_maleficarum.patchouli.processors;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.recipe.BrewingCauldronRecipe;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.List;

public class BrewingCauldronRecipeProcessor implements IComponentProcessor {
    private BrewingCauldronRecipe recipe;
    private List<ItemStack> input;
    private ItemStack resultItem;
    private ItemStack collectItem;

    private boolean isInOrder;
    int[] itemsOrder;
    private int itemIndex = 0;

    private void setupOrderArray(int count) {
        itemsOrder = switch (count) {
            case 1 -> new int[] {0,0,0,0,0,0,0,1,0,0};
            case 2 -> new int[] {0,0,0,0,0,0,1,0,1,0};
            case 3 -> new int[] {0,0,0,0,0,0,1,1,1,0};
            case 4 -> new int[] {0,0,0,0,0,1,1,0,1,1};
            case 5 -> new int[] {0,0,0,0,0,1,1,1,1,1};
            case 6 -> new int[] {0,1,1,1,0,0,1,1,1,0};
            case 7 -> new int[] {0,1,0,1,0,1,1,1,1,1};
            case 8 -> new int[] {0,1,1,1,0,1,1,1,1,1};
            case 9 -> new int[] {1,1,0,1,1,1,1,1,1,1};
            case 10 -> new int[] {1,1,1,1,1,1,1,1,1,1};
            default -> new int[]{};
        };
    }

    @Override
    public void setup(Level level, IVariableProvider iVariableProvider) {

        ResourceLocation recipeId = new ResourceLocation(iVariableProvider.get("recipe").asString());

        RecipeManager recipeManager = level.getRecipeManager();
        recipe = (BrewingCauldronRecipe) recipeManager.byKey(recipeId).orElseThrow(()->new IllegalArgumentException("Could not find recipe for: " + recipeId));
        input = recipe.getIngredients().stream().filter(x->!x.isEmpty()).map(x-> x.getItems()[0]).toList();
        int count = input.size();
        setupOrderArray(count);
        resultItem = recipe.getResultItem(null);
        isInOrder = recipe.isInOrder(null);

        collectItem = switch (recipe.craftType) {
            default -> ItemStack.EMPTY;
            case 1 -> new ItemStack(ModItems.EMPTY_VIAL.get());
            case 2 -> new ItemStack(Items.BOWL);
        };

    }

    @Override
    public IVariable process(Level level, String key) {
        if(key.startsWith("input")) {
            int index = key.charAt(key.length()-1) - '0';
            if (itemsOrder[index]==1) {
                return IVariable.from(input.get(itemIndex++));
            }
            return IVariable.empty();
        }
        else if(key.startsWith("collectItem"))
            return IVariable.from(collectItem);
        else if (key.startsWith("flagCollectItem"))
            return IVariable.wrap(!collectItem.isEmpty());
        else if(key.startsWith("result"))
            return IVariable.from(resultItem);
        else if (key.startsWith("orderTooltip"))
            return IVariable.wrap(Component.translatable("book.shadow_grimoire.cauldron.order").getString());
        else if (key.startsWith("inOrder?"))
            return IVariable.wrap(isInOrder);
        return null;
    }
}

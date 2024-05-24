package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import javax.annotation.Nullable;

public class MortarAndPestleItem extends Item {

    public MortarAndPestleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    @Nullable
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack copy = itemStack.copy();
        if (copy.hurt(1, RandomSource.create(), null)) {
            return ItemStack.EMPTY;
        }
        return copy;
    }

}

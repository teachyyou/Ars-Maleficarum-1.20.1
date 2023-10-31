package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class WoodenMortarAndPestle extends Item {

    public WoodenMortarAndPestle(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 18;
    }

    @Override
    @Nullable
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        final ItemStack copy = itemStack.copy();
        copy.setDamageValue(copy.getDamageValue()+1);
        if (copy.getDamageValue()==copy.getMaxDamage()) return ItemStack.EMPTY;
        return copy;
    }

}

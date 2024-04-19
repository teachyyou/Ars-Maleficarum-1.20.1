package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CommonInfusedItem extends Item {
    public CommonInfusedItem(Properties pProperties) {
        super(pProperties);
    }

    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}

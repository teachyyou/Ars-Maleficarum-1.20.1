package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.item.ModItems;

public class TreeLarva extends Item {
    public TreeLarva(Properties pProperties) {
        super(pProperties);

    }
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level level, Entity entity, int pSlotId, boolean pIsSelected) {
        var tag = pStack.getOrCreateTag();
        int liveTime = tag.getInt("liveTime");
        tag.putInt("liveTime", liveTime+1);
        if (liveTime >= 12000)
        {
            ((Player) entity).getInventory().setItem(pSlotId, new ItemStack(ModItems.DEAD_TREE_LARVA.get(), pStack.getCount()));
        }
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {

        var tag = stack.getOrCreateTag();
        if (!tag.contains("liveTime")) {tag.putInt("liveTime", 0);}
        int liveTime = tag.getInt("liveTime");
        tag.putInt("liveTime", liveTime+1);
        if (liveTime >= 400)
        {
            entity.setItem(new ItemStack(ModItems.DEAD_TREE_LARVA.get(), entity.getItem().getCount()));
        }
        return super.onEntityItemUpdate(stack, entity);
    }

}

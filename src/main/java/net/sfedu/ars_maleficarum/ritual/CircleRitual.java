package net.sfedu.ars_maleficarum.ritual;

import it.unimi.dsi.fastutil.ints.IntLists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;

import javax.naming.Context;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class CircleRitual {


    protected enum Dimension {NETHER, OVERWORLD, END, ANY};

    protected RitualCoreEntity.CircleType smallCircleType;
    protected RitualCoreEntity.CircleType mediumCircleType;
    protected RitualCoreEntity.CircleType largeCircleType;
    protected RitualCoreEntity.CircleType coreType;

    protected int ticks;
    protected Entity sacrificeEntity;
    protected Map<Item, Integer> components = new HashMap<Item,Integer>();
    protected String ritualName;

    protected List<ItemEntity> items = new LinkedList<ItemEntity>();
    protected Dimension dimension;
    abstract public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore);
    public boolean doesMatch(SimpleContainer container) {
        //TODO: добавить проверку и на количество

        for (Item item : components.keySet()) {
            boolean flag = false;
            foundItem: for (int j = 0; j < container.getContainerSize(); j++) {
                if (container.getItem(j).is(item) && container.getItem(j).getCount()>=(components.get(item))) {
                    flag = true;
                    break foundItem;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }


    public RitualCoreEntity.CircleType getSmallCircleType() {
        return smallCircleType;
    }
    public RitualCoreEntity.CircleType getMediumCircleType() {
        return mediumCircleType;
    }
    public RitualCoreEntity.CircleType getLargeCircleType() {
        return largeCircleType;
    }
    public RitualCoreEntity.CircleType getCoreType() {
        return coreType;
    }

    public void addItemEntities(List<ItemEntity> input) {
        items.addAll(input);
    }

}

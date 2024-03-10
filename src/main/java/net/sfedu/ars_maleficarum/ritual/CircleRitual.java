package net.sfedu.ars_maleficarum.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;

import javax.naming.Context;

public abstract class CircleRitual {


    protected enum Dimension {NETHER, OVERWORLD, END, ANY};

    protected RitualCoreEntity.CircleType smallCircleType;
    protected RitualCoreEntity.CircleType mediumCircleType;
    protected RitualCoreEntity.CircleType largeCircleType;
    protected Entity sacrificeEntity;
    protected NonNullList<Item> complnents;
    protected String ritualName;
    protected Dimension dimension;
    abstract public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore);
    abstract public boolean doesMatch(SimpleContainer container);

    public RitualCoreEntity.CircleType getSmallCircleType() {
        return smallCircleType;
    }
    public RitualCoreEntity.CircleType getMediumCircleType() {
        return mediumCircleType;
    }
    public RitualCoreEntity.CircleType getLargeCircleType() {
        return largeCircleType;
    }

}

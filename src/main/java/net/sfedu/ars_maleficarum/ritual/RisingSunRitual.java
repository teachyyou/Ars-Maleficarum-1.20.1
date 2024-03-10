package net.sfedu.ars_maleficarum.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.TickPriority;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;

import javax.naming.Context;

public class RisingSunRitual extends CircleRitual{

    public RisingSunRitual() {}

    private int ticks = 0;

    private final String ritualName = "Rite of Rising Sun";

    protected RitualCoreEntity.CircleType smallCircleType = RitualCoreEntity.CircleType.ANY;
    protected RitualCoreEntity.CircleType mediumCircleType = RitualCoreEntity.CircleType.NATURAL;
    protected RitualCoreEntity.CircleType largeCircleType = RitualCoreEntity.CircleType.WHITE;

    private NonNullList<Item> components = NonNullList.of(ModItems.RING_OF_MORNING_DEW.get(), ModItems.SUNLIGHT_FLOWER.get(),Items.SUNFLOWER);

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {
        ticks++;
        System.out.println("execute start");
        if (ticks%20.0==0 && pPos!=null) {
            EntityType.LIGHTNING_BOLT.spawn((ServerLevel) pLevel, (ItemStack) null,null, pPos.relative(Direction.Axis.Z, 5-pLevel.random.nextInt(10)).relative(Direction.Axis.X, 5-pLevel.random.nextInt(10)), MobSpawnType.TRIGGERED,true,true);
        }
        //pLevel.getBlockTicks().schedule(new ScheduledTick<>(ModBlocks.ROWAN_LOG.get(), pPos, pLevel.getGameTime() + 100, TickPriority.NORMAL, pLevel.getGameTime()));
        if (ticks/20.0 == 5.0) {
            System.out.println("ticks check passed");
            pLevel.getServer().getLevel(Level.OVERWORLD).setDayTime(13000);
            pPlayer.sendSystemMessage(Component.translatable("Voila!!"));
            ticks=0;
            riteCore.stopRitual();
        }
    }

    @Override
    public String toString() {
        return "Rising Sun Ritual";
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

    public boolean doesMatch(SimpleContainer container) {

        for (int i = 0; i < components.size(); i++) {
            boolean flag = false;
            foundItem: for (int j = 0; j < container.getContainerSize(); j++) {
                if (container.getItem(j).is(components.get(i))) {
                    flag = true;
                    break foundItem;
                }
                //System.out.println("does match false");
                //System.out.println(container.getItem(j));

            }
            if (!flag) return false;
        }
        //System.out.println("does match pass");
        return true;
    }
}

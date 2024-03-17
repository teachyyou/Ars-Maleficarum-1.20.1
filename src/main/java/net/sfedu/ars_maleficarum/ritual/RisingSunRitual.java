package net.sfedu.ars_maleficarum.ritual;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.item.ItemEntity;
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
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.RitualCircleCore;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;

import javax.naming.Context;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RisingSunRitual extends CircleRitual{

    public RisingSunRitual() {
        ticks=0;
        ritualName="Rite of Rising Sun";
        smallCircleType= RitualCoreEntity.CircleType.NATURAL;
        mediumCircleType= RitualCoreEntity.CircleType.WHITE;
        largeCircleType= RitualCoreEntity.CircleType.NATURAL;
        coreType= RitualCoreEntity.CircleType.NATURAL;
        components.put(Items.DIAMOND, 2);
        components.put(Items.GOLD_INGOT, 2);
        components.put(Items.NETHER_STAR, 1);
    }

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {


        if (!items.isEmpty() && (ticks%20==0)) {
            ticks=0;
            ItemEntity item = items.get(0);
            items.remove(item);
            int amount = components.get(item.getItem().getItem());
            if (amount>=1) {
                double d0 = item.position().x;
                double d1 = item.position().y;
                double d2 = item.position().z;
                ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, d0, d1, d2, 20, 0,0.5D,0,0.2);
                int toTake = Math.min(amount,item.getItem().getCount());
                components.computeIfPresent(item.getItem().getItem(),(k,v)->v-toTake);
                item.getItem().shrink(toTake);

            } else return;

        }
        ticks++;
        if (items.isEmpty() && ticks%20.0==0 && pPos!=null) {
            EntityType.LIGHTNING_BOLT.spawn((ServerLevel) pLevel, (ItemStack) null,null, pPos.relative(Direction.Axis.Z, 5-pLevel.random.nextInt(10)).relative(Direction.Axis.X, 5-pLevel.random.nextInt(10)), MobSpawnType.TRIGGERED,true,true);
        }
        if (items.isEmpty() && (ticks/20.0 == 5)) {
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

}

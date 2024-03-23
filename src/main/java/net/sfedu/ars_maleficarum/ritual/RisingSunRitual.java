package net.sfedu.ars_maleficarum.ritual;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleType;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.TickPriority;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.RitualCircleCore;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;

import javax.naming.Context;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class RisingSunRitual extends CircleRitual{

    public RisingSunRitual() {
        ritualName="Rite of Rising Sun";
        smallCircleType= RitualCoreEntity.CircleType.WHITE;
        mediumCircleType= RitualCoreEntity.CircleType.ANY;
        largeCircleType= RitualCoreEntity.CircleType.WHITE;
        coreType= RitualCoreEntity.CircleType.WHITE;
        components.put(Items.SUNFLOWER, 1);
        components.put(ModItems.SUNLIGHT_FLOWER.get(), 1);
        components.put(ModItems.RING_OF_MORNING_DEW.get(), 1);
        doesRequireLargeCircle=true;
        doesRequireMediumCircle=true;
        doesRequireSmallCircle=true;
    }

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        if (pLevel.isDay()) {
            riteCore.stopRitual();
            return;
        }
        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;
        if (allComponentsConsumed && ticks%20.0==0 && pPos!=null) {
            EntityType.LIGHTNING_BOLT.spawn((ServerLevel) pLevel, (ItemStack) null,null, pPos.relative(Direction.Axis.Z, 5-pLevel.random.nextInt(10)).relative(Direction.Axis.X, 5-pLevel.random.nextInt(10)), MobSpawnType.TRIGGERED,true,true);
        }
        if (allComponentsConsumed && ticks/20.0 == 5) {
            pLevel.getServer().getLevel(Level.OVERWORLD).setDayTime(23500);
            pPlayer.sendSystemMessage(Component.translatable(ritualName));
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
        }
    }



    @Override
    public String toString() {
        return "Rising Sun Ritual";
    }



}

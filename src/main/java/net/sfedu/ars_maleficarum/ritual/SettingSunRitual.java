package net.sfedu.ars_maleficarum.ritual;

import com.sun.jna.platform.unix.X11;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;

public class SettingSunRitual extends CircleRitual {


    public SettingSunRitual(RitualType<?> type) {
        super(type, WHITE, WHITE, ANY, WHITE);
        components.put(Items.LAPIS_LAZULI, 1);
        components.put(ModItems.MOONLIGHT_FLOWER.get(), 1);
        components.put(ModItems.SWEET_DREAM.get(), 1);
    }

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        if (pLevel.isNight()) {
            riteCore.stopRitual();
            return;
        }

        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;

        if (allComponentsConsumed && ticks%20.0==0) {
            if (ticks%40.0==0 && pPos!=null) EntityType.LIGHTNING_BOLT.spawn((ServerLevel) pLevel, (ItemStack) null,null, pPos.relative(Direction.Axis.Z, 5-pLevel.random.nextInt(10)).relative(Direction.Axis.X, 5-pLevel.random.nextInt(10)), MobSpawnType.TRIGGERED,true,true);
            pLevel.getServer().getLevel(Level.OVERWORLD).setDayTime(pLevel.getDayTime()+2000);
            pLevel.updateSkyBrightness();
        }
        if (allComponentsConsumed && pLevel.getDayTime()>=13000) {
            pPlayer.sendSystemMessage(ritualName);
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
        }
    }




}

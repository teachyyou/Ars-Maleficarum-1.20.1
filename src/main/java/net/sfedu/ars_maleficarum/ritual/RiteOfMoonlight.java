package net.sfedu.ars_maleficarum.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;

public class RiteOfMoonlight extends CircleRitual {

    public RiteOfMoonlight(RitualType<?> type) {
        super(type, RitualCoreEntity.ChalkType.WHITE, RitualCoreEntity.ChalkType.WHITE,RitualCoreEntity.ChalkType.WHITE,RitualCoreEntity.ChalkType.WHITE);
        components.put(ModItems.SCENT_OF_UNCERTAINTY.get(), 1);
        components.put(ModItems.ABSOLUTE_ORDER.get(), 1);
        components.put(ModItems.SILVER.get(), 1);
        components.put(ModItems.SUNLIGHT_FLOWER_SEED.get(), 1);
    }


    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        if (pLevel.getDayTime()<16000 || pLevel.getDayTime() > 20000 || pLevel.isRaining() || (!hasNoBlocksAbove3x3(pLevel,pPos))) {
            riteCore.stopRitual();
            return;
        }
        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;
        if (allComponentsConsumed && pPos!=null) {
            double d0 = pPos.getCenter().x;
            double d1 = pPos.getCenter().y+20-(ticks/5);
            double d2 = pPos.getCenter().z;
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WAX_OFF, d0, d1, d2, 50, 0,0.5D,0,0.5);

        }
        if (allComponentsConsumed && ticks/20.0 == 5) {
            double d0 = pPos.getCenter().x;
            double d1 = pPos.getCenter().y;
            double d2 = pPos.getCenter().z;
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WAX_OFF, d0, d1, d2, 50, 0,0.5D,0,0.5);
            pLevel.playSound(null, pPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS,1F,1F);
            pLevel.addFreshEntity(new ItemEntity(pLevel, d0, d1, d2, new ItemStack(ModItems.MOONLIGHT_FLOWER_SEED.get(),1)));
            pPlayer.sendSystemMessage(ritualName);
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
        }
    }




}

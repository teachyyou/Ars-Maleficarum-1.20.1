package net.sfedu.ars_maleficarum.ritual.ImprisonmentRituals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ChalkSymbol;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;

import java.util.List;

public class RiteOfMediumDemonicImprisonment extends CircleRitual {

    public RiteOfMediumDemonicImprisonment() {
        ritualName="Rite of Demonic Imprisonment";
        smallCircleType= RitualCoreEntity.CircleType.ANY;
        mediumCircleType= RitualCoreEntity.CircleType.ANY;
        largeCircleType= RitualCoreEntity.CircleType.ANY;
        coreType= RitualCoreEntity.CircleType.ANY;
        components.put(Items.SOUL_SAND, 1);
        components.put(Items.IRON_INGOT, 1);
        components.put(ModItems.SALT.get(), 2);
        components.put(ModItems.SILVER.get(), 1);
        doesRequireLargeCircle=false;
        doesRequireMediumCircle=true;
        doesRequireSmallCircle=false;
    }


    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;
        if (allComponentsConsumed && ticks%5 == 0) {
            if (ticks%10==0)pLevel.playSound(null, pPos, SoundEvents.VEX_AMBIENT, SoundSource.PLAYERS,1F,1F);
            double d0 = pPos.getCenter().x;
            double d1 = pPos.getCenter().y;
            double d2 = pPos.getCenter().z;
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.ENCHANT, d0, d1, d2, 50, 0,1D,0,0.75);
        }
        if (allComponentsConsumed && ticks/20.0 == 3) {
            List<BlockPos> MediumCircle = riteCore.mediumCircle();
            for (int i = 0; i < MediumCircle.size(); i++) {
                BlockPos pos = MediumCircle.get(i);
                BlockState currentSymbol = pLevel.getBlockState(pos);
                currentSymbol = currentSymbol.setValue(ChalkSymbol.IMPRISONMENT,2);
                pLevel.setBlock(pos,currentSymbol,3);
                double d0 = pos.getCenter().x;
                double d1 = pos.getCenter().y;
                double d2 = pos.getCenter().z;
                ((ServerLevel)pLevel).sendParticles(ParticleTypes.ENCHANT, d0, d1, d2, 50, 0,0.1D,0,1D);
            }

            pPlayer.sendSystemMessage(Component.translatable(ritualName));
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);


        }
    }




}

package net.sfedu.ars_maleficarum.ritual.ritualTemplates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class ApplyEffectOnPlayersRitual extends CircleRitual{

    public List<MobEffectInstance> effects = new ArrayList<>();
    public int playersAmount;
    public int radius;


    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;
        if (allComponentsConsumed && ticks%20==0) {
            System.out.println("if entered");
            List<ServerPlayer> list;
            for (MobEffectInstance instance : effects) {
                list = MobEffectUtil.addEffectToPlayersAround((ServerLevel)pLevel, pPlayer, pPos.getCenter(), radius, instance, instance.getDuration()).stream().limit(playersAmount).toList();
                list.forEach((player) -> {
                    pLevel.playSound(null, pPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS,1F,1F);
                    ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, player.getX(), player.getY(), player.getZ(), 100, 0,0.5D,0,0.1);
                    ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, player.getX(), player.getY(), player.getZ(), 100, 0,0.0D,0,0.1);
                    ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, player.getX(), player.getY(), player.getZ(), 100, 0,1D,0,0.1);
                    ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, player.getX(), player.getY(), player.getZ(), 100, 0,1.5D,0,0.1);
                });
            }
            pPlayer.sendSystemMessage(Component.translatable(ritualName));
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
        }


    }

}

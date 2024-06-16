package net.sfedu.ars_maleficarum.ritual.RitesOfSummoning;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.custom.GluttonyDemonEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.RitualType;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;
import net.sfedu.ars_maleficarum.sound.ModSounds;

public class    RiteOfAbyssalFeast extends CircleRitual {

    public RiteOfAbyssalFeast(RitualType<?> type) {
        super(type, NETHER, NONE, NETHER, NETHER);
        components.put(ModItems.ROWAN_BERRIES.get(), 1);
        components.put(Items.GOLDEN_APPLE,1);
        components.put(Items.CAKE,1);
        components.put(Items.COOKED_PORKCHOP,1);
        components.put(Items.COOKED_BEEF,1);
        components.put(Items.BLAZE_POWDER,1);
        components.put(ModItems.WHIFF_OF_TIME.get(), 1);
        
        particleType=ParticleTypes.FLAME;
        itemConsumeSound = SoundEvents.BLAZE_SHOOT;
        itemConsumeParticleSpeed = 0.05f;
    }

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;

        if (allComponentsConsumed && ticks%5==0) {
            ((ServerLevel)pLevel).sendParticles(particleType, pPos.getCenter().x, pPos.getCenter().y, pPos.getCenter().z, 200, 0,0.5D,0,0.01);
            ((ServerLevel)pLevel).sendParticles(particleType, pPos.getCenter().x, pPos.getCenter().y, pPos.getCenter().z, 200, 0,0.5D,0,0.01);
            ((ServerLevel)pLevel).sendParticles(particleType, pPos.getCenter().x, pPos.getCenter().y, pPos.getCenter().z, 200, 0,1D,0,0.01);
            ((ServerLevel)pLevel).sendParticles(particleType, pPos.getCenter().x, pPos.getCenter().y, pPos.getCenter().z, 200, 0,1D,0,0.01);
        }
        if (allComponentsConsumed && ticks%20.0 == 0) {
            pLevel.playSound(null,pPos,SoundEvents.BLAZE_HURT,SoundSource.BLOCKS, 1f, 1f);
            for (BlockPos pos : riteCore.mediumCircle()) {
                ((ServerLevel)pLevel).sendParticles(particleType, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 50, 0,0.2D,0,0.01);
            }
            for (BlockPos pos : riteCore.mediumJoints()) {
                ((ServerLevel)pLevel).sendParticles(particleType, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 50, 0,0.2D,0,0.01);
            }
            for (BlockPos pos : riteCore.largeCircle()) {
                ((ServerLevel)pLevel).sendParticles(particleType, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 50, 0,0.2D,0,0.01);
            }
        }
        if (allComponentsConsumed && ticks/20.0 == 5) {
            GluttonyDemonEntity mandrake = ModEntities.GLUTTONY_DEMON.get().create(pLevel);
            if (mandrake != null) {
                pLevel.playSound(null,pPos,SoundEvents.BLAZE_DEATH,SoundSource.BLOCKS, 1f, 1f);
                pLevel.playSound(null,pPos,ModSounds.GLUTTONY_DEMON_SPAWN.get(), SoundSource.BLOCKS, 1f, 1f);
                mandrake.moveTo((double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, 0.0F, 0.0F);
                pLevel.addFreshEntity(mandrake);

                pPlayer.sendSystemMessage(ritualName);
                ticks=0;
                riteCore.stopRitual();
                tryToContinue(pLevel,pPos,pPlayer,riteCore);
            }
        }


    }

}

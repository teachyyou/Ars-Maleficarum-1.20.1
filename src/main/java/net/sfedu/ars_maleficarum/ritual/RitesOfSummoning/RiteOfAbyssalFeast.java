package net.sfedu.ars_maleficarum.ritual.RitesOfSummoning;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.custom.GluttonyDemonEntity;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;
import net.sfedu.ars_maleficarum.sound.ModSounds;

import java.util.Optional;

public class RiteOfAbyssalFeast extends CircleRitual {

    public RiteOfAbyssalFeast() {
        ritualName="Rite of Abyssal Feast";
        smallCircleType= RitualCoreEntity.CircleType.ANY;
        mediumCircleType= RitualCoreEntity.CircleType.NETHER;
        largeCircleType= RitualCoreEntity.CircleType.NETHER;
        coreType= RitualCoreEntity.CircleType.NETHER;
        components.put(Items.DIAMOND,1);
        doesRequireLargeCircle=true;
        doesRequireMediumCircle=true;
        doesRequireSmallCircle=false;
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
            MandrakeEntity mandrake = ModEntities.MANDRAKE.get().create(pLevel);
            if (mandrake != null) {
                pLevel.playSound(null,pPos,SoundEvents.BLAZE_DEATH,SoundSource.BLOCKS, 1f, 1f);
                mandrake.moveTo((double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, 0.0F, 0.0F);
                pLevel.addFreshEntity(mandrake);

                pPlayer.sendSystemMessage(Component.translatable(ritualName));
                ticks=0;
                riteCore.stopRitual();
                tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
            }
        }


    }

}

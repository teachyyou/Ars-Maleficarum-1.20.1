package net.sfedu.ars_maleficarum.ritual.craftingRituals.craftingRitualsThatRequiresDemon;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.custom.GluttonyDemonEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;
import net.sfedu.ars_maleficarum.sound.ModSounds;

import java.util.NoSuchElementException;
import java.util.Optional;

public class RiteOfKramerTorchCreation extends CircleRitual {

    public RiteOfKramerTorchCreation() {
        ritualName="Rite of Forgotten Weapon Awakening"; //TODO придумать нормальное название
        smallCircleType= RitualCoreEntity.CircleType.WHITE;
        mediumCircleType= RitualCoreEntity.CircleType.NETHER;
        largeCircleType= RitualCoreEntity.CircleType.NETHER;
        coreType= RitualCoreEntity.CircleType.NETHER;
        components.put(ModItems.FERMENTED_TREE_LARVA.get(), 1);
        doesRequireLargeCircle=true;
        doesRequireMediumCircle=true;
        doesRequireSmallCircle=true;
        sacrificeEntity = GluttonyDemonEntity.class;
        particleType = ParticleTypes.FLAME;
        itemConsumeSound = SoundEvents.BLAZE_SHOOT;
    }

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        Optional<ItemEntity> possiblyStaff = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pPos.relative(Direction.Axis.Z,-3).relative(Direction.Axis.X,-3).relative(Direction.Axis.Y,-5),pPos.relative(Direction.Axis.Z,3).relative(Direction.Axis.X,3).relative(Direction.Axis.Y,5)))
                .stream().filter(x->x.getItem().is(ModItems.POISON_STAFF.get())).findAny(); //todo поменять итем

        if (possiblyStaff.isEmpty()) {
            riteCore.stopRitual();
            return;
        }
        ItemEntity staff = possiblyStaff.get();
        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;

        if (allComponentsConsumed && ticks%5==0) {
            double d0 = staff.position().x;
            double d1 = staff.position().y;
            double d2 = staff.position().z;

            ((ServerLevel)pLevel).sendParticles(ParticleTypes.FLAME, d0, d1, d2, 100, 0,0.5D,0,0.01);
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.FLAME, d0, d1, d2, 100, 0,1D,0,0.01);

            if (ticks%20==0) pLevel.playSound(null, pPos,itemConsumeSound, SoundSource.HOSTILE,1F,1F);
        }

        if (allComponentsConsumed && ticks/20.0==5.0) {
            Optional<GluttonyDemonEntity> possiblyDemon = pLevel.getEntitiesOfClass(GluttonyDemonEntity.class ,new AABB(pPos.relative(Direction.Axis.Z,-3).relative(Direction.Axis.X,-3).relative(Direction.Axis.Y,-5),pPos.relative(Direction.Axis.Z,3).relative(Direction.Axis.X,3).relative(Direction.Axis.Y,5)))
                    .stream().findAny();
            if (possiblyDemon.isEmpty()) {
                EntityType.LIGHTNING_BOLT.spawn((ServerLevel) pLevel,null,pPlayer,pPlayer.blockPosition(), MobSpawnType.TRIGGERED,true,true);
                //pPlayer.thunderHit((ServerLevel) pLevel,new LightningBolt(EntityType.LIGHTNING_BOLT,pLevel)); //todo проверить работает ли, выглядит как дичь
                pLevel.playSound(null, pPos, ModSounds.MYSTIC_WHISPERING.get(), SoundSource.HOSTILE,1F,1F);

                pPlayer.kill();
                riteCore.stopRitual();
                return;
            }
            GluttonyDemonEntity demon = possiblyDemon.get();

            double d0 = demon.position().x;
            double d1 = demon.position().y;
            double d2 = demon.position().z;

            ((ServerLevel)pLevel).sendParticles(ParticleTypes.ENCHANT, d0, d1, d2, 100, 0,0.5D,0,0.5);
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.ENCHANT, d0, d1, d2, 100, 0,1D,0,0.5);
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.ENCHANT, d0, d1, d2, 100, 0,1.5D,0,0.5);
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.ENCHANT, d0, d1, d2, 100, 0,2D,0,0.5);
            pLevel.playSound(null, pPos, ModSounds.GLUTTONY_DEMON_DIED.get(), SoundSource.HOSTILE,1F,1F);
            demon.discard();
        }


        if (allComponentsConsumed && ticks/20.0 == 6.0) {
            double d0 = staff.position().x;
            double d1 = staff.position().y;
            double d2 = staff.position().z;

            ((ServerLevel)pLevel).sendParticles(ParticleTypes.FLAME, d0, d1, d2, 100, 0,0.5D,0,0.2);
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.FLAME, d0, d1, d2, 100, 0,1D,0,0.2);
            pLevel.playSound(null, pPos, SoundEvents.WITHER_DEATH, SoundSource.PLAYERS,1F,1F);
            staff.setItem(new ItemStack(ModItems.FIRE_STAFF.get()));
            pPlayer.sendSystemMessage(Component.translatable(ritualName));
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
        }
    }

}

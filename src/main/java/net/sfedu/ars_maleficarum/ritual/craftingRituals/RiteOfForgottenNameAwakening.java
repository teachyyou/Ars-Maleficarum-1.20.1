package net.sfedu.ars_maleficarum.ritual.craftingRituals;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;
import net.sfedu.ars_maleficarum.sound.ModSounds;

import java.util.Optional;

public class RiteOfForgottenNameAwakening extends CircleRitual {

    public RiteOfForgottenNameAwakening() {
        ritualName="Rite of Forgotten Name Awakening";
        smallCircleType= RitualCoreEntity.ChalkType.NETHER;
        mediumCircleType= RitualCoreEntity.ChalkType.WHITE;
        largeCircleType= RitualCoreEntity.ChalkType.NETHER;
        coreType= RitualCoreEntity.ChalkType.WHITE;
        components.put(Items.DIAMOND,1);
        components.put(Items.BLAZE_ROD,1);
        components.put(Items.NAME_TAG,1);
        components.put(ModItems.PETRICHOR.get(), 1);
        components.put(ModItems.SCENT_OF_UNCERTAINTY.get(), 1);
        components.put(ModItems.GROUND_MARIGOLD_FLOWERS.get(), 1);
        components.put(ModItems.NAMELESS_CHARCOAL.get(), 1);
        doesRequireLargeCircle=true;
        doesRequireMediumCircle=true;
        doesRequireSmallCircle=true;
        particleType=ParticleTypes.FLAME;
        itemConsumeSound = SoundEvents.BLAZE_SHOOT;
        itemConsumeParticleSpeed = 0.05f;
    }

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        Optional<ItemEntity> possiblySapling = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pPos.relative(Direction.Axis.Z,-3).relative(Direction.Axis.X,-3).relative(Direction.Axis.Y,-5),pPos.relative(Direction.Axis.Z,3).relative(Direction.Axis.X,3).relative(Direction.Axis.Y,5)))
                .stream().filter(x->x.getItem().is(ModBlocks.NAMELESS_TREE_SAPLING.get().asItem())).findAny();

        if (possiblySapling.isEmpty() || possiblySapling.get().getItem().getCount()!=1) {
            riteCore.stopRitual();
            return;
        }
        ItemEntity sapling = possiblySapling.get();
        sapling.setInvulnerable(true);


        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;

        if (allComponentsConsumed && ticks/20.0 == 1) {
            pLevel.getServer().getLevel(Level.OVERWORLD).setWeatherParameters(0, 20*60, true, true);
        }
        if (allComponentsConsumed && ticks%20.0 == 0) {
            EntityType.LIGHTNING_BOLT.spawn((ServerLevel) pLevel, (ItemStack) null,null, pPos.relative(Direction.Axis.Z, 5-pLevel.random.nextInt(10)).relative(Direction.Axis.X, 5-pLevel.random.nextInt(10)), MobSpawnType.TRIGGERED,true,true);
        }
        if (allComponentsConsumed && ticks%40.0 == 0) {
            for (BlockPos pos : riteCore.getAllPositions()) {
                ((ServerLevel)pLevel).sendParticles(particleType, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 50, 0,0.2D,0,0.01);
            }
        }

        if (allComponentsConsumed && ticks/20.0 == 10) {
            double d0 = sapling.position().x;
            double d1 = sapling.position().y;
            double d2 = sapling.position().z;


            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, d0, d1, d2, 100, 0,0.5D,0,0.1);
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, d0, d1, d2, 100, 0,0.5D,0,0.1);
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, d0, d1, d2, 100, 0,0.5D,0,0.1);
            pLevel.playSound(null, pPos, ModSounds.MYSTIC_WHISPERING.get(), SoundSource.PLAYERS,1F,1F);
            sapling.setItem(new ItemStack(ModBlocks.KRAMER_SAPLING.get()));



            pPlayer.sendSystemMessage(Component.translatable(ritualName));
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
        }
    }

}

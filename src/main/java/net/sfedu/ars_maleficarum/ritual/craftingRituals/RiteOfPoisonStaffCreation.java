package net.sfedu.ars_maleficarum.ritual.craftingRituals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.RitualType;
import net.sfedu.ars_maleficarum.ritual.RitualTypes;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;
import net.sfedu.ars_maleficarum.sound.ModSounds;

import java.util.Optional;

public class RiteOfPoisonStaffCreation extends CircleRitual {

    public RiteOfPoisonStaffCreation(RitualType<?> type) {
        super(type);
        smallCircleType= RitualCoreEntity.ChalkType.NATURAL;
        mediumCircleType= RitualCoreEntity.ChalkType.WHITE;
        largeCircleType= RitualCoreEntity.ChalkType.NATURAL;
        coreType= RitualCoreEntity.ChalkType.WHITE;
        components.put(Items.SPIDER_EYE,1);
        components.put(Items.GLOWSTONE,1);
        components.put(Items.EMERALD, 1);
        components.put(ModItems.STINK_OF_SWAMP.get(), 1);
        components.put(Items.BONE, 1);
        doesRequireLargeCircle=true;
        doesRequireMediumCircle=true;
        doesRequireSmallCircle=true;

    }

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        Optional<ItemEntity> possiblyStaff = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pPos.relative(Direction.Axis.Z,-3).relative(Direction.Axis.X,-3).relative(Direction.Axis.Y,-5),pPos.relative(Direction.Axis.Z,3).relative(Direction.Axis.X,3).relative(Direction.Axis.Y,5)))
                .stream().filter(x->x.getItem().is(ModItems.INERT_POISON_STAFF.get())).findAny();

        if (possiblyStaff.isEmpty() || possiblyStaff.get().getItem().getCount()!=1) {
            riteCore.stopRitual();
            return;
        }
        ItemEntity staff = possiblyStaff.get();

        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;
        if (allComponentsConsumed && ticks%20.0 == 0) {
            double d0 = staff.position().x;
            double d1 = staff.position().y;
            double d2 = staff.position().z;

            staff.setItem(new ItemStack(ModItems.POISON_STAFF.get()));
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, d0, d1, d2, 100, 0,0.5D,0,0.2);
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, d0, d1, d2, 100, 0,1D,0,0.2);
            pLevel.playSound(null, pPos, ModSounds.MYSTIC_WHISPERING.get(), SoundSource.PLAYERS,1F,1F);



            pPlayer.sendSystemMessage(ritualName);
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
        }
    }

}

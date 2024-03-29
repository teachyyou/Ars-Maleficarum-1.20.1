package net.sfedu.ars_maleficarum.ritual.craftingRituals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;

import java.util.Optional;

public class RiteOfForgottenNameAwakening extends CircleRitual {

    public RiteOfForgottenNameAwakening() {
        ritualName="Rite of Forgotten Name Awakening";
        smallCircleType= RitualCoreEntity.CircleType.NETHER;
        mediumCircleType= RitualCoreEntity.CircleType.WHITE;
        largeCircleType= RitualCoreEntity.CircleType.NETHER;
        coreType= RitualCoreEntity.CircleType.WHITE;
        components.put(ModItems.FERMENTED_TREE_LARVA.get(), 1);
        components.put(ModItems.DEAD_TREE_BARK.get(), 3);
        components.put(ModItems.CURSED_GOLD_CHUNK.get(), 1);
        components.put(ModItems.STINK_OF_SWAMP.get(), 1);
        components.put(ModItems.MANDRAKE_ROOT.get(), 1);
        components.put(Items.FERMENTED_SPIDER_EYE, 1);
        doesRequireLargeCircle=true;
        doesRequireMediumCircle=true;
        doesRequireSmallCircle=true;
    }

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        Optional<ItemEntity> possiblyStaff = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pPos.relative(Direction.Axis.Z,-3).relative(Direction.Axis.X,-3).relative(Direction.Axis.Y,-5),pPos.relative(Direction.Axis.Z,3).relative(Direction.Axis.X,3).relative(Direction.Axis.Y,5)))
                .stream().filter(x->x.getItem().is(ModItems.POISON_STAFF.get())).findAny();

        if (possiblyStaff.isEmpty() || !possiblyStaff.get().getItem().isDamaged()) {
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


            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, d0, d1, d2, 100, 0,0.5D,0,0.2);
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WITCH, d0, d1, d2, 100, 0,1D,0,0.2);
            pLevel.playSound(null, pPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS,1F,1F);
            staff.getItem().setDamageValue(0);



            pPlayer.sendSystemMessage(Component.translatable(ritualName));
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
        }
    }

}

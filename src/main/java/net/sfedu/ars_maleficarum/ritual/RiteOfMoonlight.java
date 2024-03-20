package net.sfedu.ars_maleficarum.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;

public class RiteOfMoonlight extends CircleRitual{

    public RiteOfMoonlight() {
        ritualName="Rite of Moonlight Charm";
        smallCircleType= RitualCoreEntity.CircleType.WHITE;
        mediumCircleType= RitualCoreEntity.CircleType.WHITE;
        largeCircleType= RitualCoreEntity.CircleType.WHITE;
        coreType= RitualCoreEntity.CircleType.WHITE;
        components.put(ModItems.SCENT_OF_UNCERTAINTY.get(), 1);
        components.put(ModItems.ABSOLUTE_ORDER.get(), 1);
        components.put(ModItems.SILVER.get(), 1);
        components.put(ModItems.SUNLIGHT_FLOWER_SEED.get(), 1);
        doesRequireLargeCircle=true;
        doesRequireMediumCircle=true;
        doesRequireSmallCircle=true;
    }

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        if (pLevel.getDayTime()<16000 || pLevel.getDayTime() > 20000 || pLevel.isRaining()) {
            riteCore.stopRitual();
            return;
        }
        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;
        if (allComponentsConsumed && pPos!=null) {
            ParticleOptions particle = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SEA_LANTERN));
            double d0 = pPos.getCenter().x;
            double d1 = pPos.getCenter().y+20-(ticks/5);
            double d2 = pPos.getCenter().z;
            //TODO: добавить ещё и звук
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WAX_OFF, d0, d1, d2, 50, 0,0.5D,0,0.5);

        }
        if (allComponentsConsumed && ticks/20.0 == 5) {
            ParticleOptions particle = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SEA_LANTERN));
            double d0 = pPos.getCenter().x;
            double d1 = pPos.getCenter().y;
            double d2 = pPos.getCenter().z;
            ((ServerLevel)pLevel).sendParticles(ParticleTypes.WAX_OFF, d0, d1, d2, 50, 0,0.5D,0,0.5);
            pLevel.addFreshEntity(new ItemEntity(pLevel, d0, d1, d2, new ItemStack(ModItems.MOONLIGHT_FLOWER_SEED.get(),1)));
            pPlayer.sendSystemMessage(Component.translatable(ritualName));
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
        }
    }




}

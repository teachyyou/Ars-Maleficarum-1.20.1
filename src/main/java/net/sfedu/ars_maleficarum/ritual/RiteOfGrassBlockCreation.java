package net.sfedu.ars_maleficarum.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;

public class RiteOfGrassBlockCreation extends CircleRitual {

    public RiteOfGrassBlockCreation(RitualType<?> type) {
        super(type, RitualCoreEntity.ChalkType.NATURAL, RitualCoreEntity.ChalkType.NATURAL,RitualCoreEntity.ChalkType.NATURAL,RitualCoreEntity.ChalkType.NONE);
        components.put(ModItems.ASH.get(),2);
        components.put(ModItems.FERMENTED_TREE_LARVA.get(), 1);
        components.put(Items.BONE_MEAL, 1);
        components.put(Blocks.GRASS.asItem(), 1);
        components.put(Blocks.DIRT.asItem(), 1);
    }

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {

        consumeComponents(pLevel,pPos,riteCore, pPlayer);
        ticks++;
        if (allComponentsConsumed && ticks%5.0==0 && pPos!=null) {
            ParticleOptions particle = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Blocks.MOSS_BLOCK.asItem()));
            double d0 = pPos.getCenter().x;
            double d1 = pPos.getCenter().y;
            double d2 = pPos.getCenter().z;
            //TODO: добавить ещё и звук
            ((ServerLevel)pLevel).sendParticles(particle, d0, d1, d2, 100, 0,0.5D,0,0.1);
        }
        if (allComponentsConsumed && ticks/20.0 == 5 && pPos!=null) {
            ParticleOptions particle = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Blocks.MOSS_BLOCK.asItem()));
            double d0 = pPos.getCenter().x;
            double d1 = pPos.getCenter().y;
            double d2 = pPos.getCenter().z;
            ((ServerLevel)pLevel).sendParticles(particle, d0, d1, d2, 100, 0,0.5D,0,0.1);
            pLevel.addFreshEntity(new ItemEntity(pLevel, d0, d1, d2, new ItemStack(Blocks.GRASS_BLOCK.asItem(),1)));
            pPlayer.sendSystemMessage(ritualName);
            ticks=0;
            riteCore.stopRitual();
            tryToContinue(pState,pLevel,pPos,pPlayer,riteCore);
        }
    }

}

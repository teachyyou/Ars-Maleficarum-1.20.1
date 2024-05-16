package net.sfedu.ars_maleficarum.ritual.ritualTemplates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.ritual.*;
import net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual.GreatRiteOfEmpowering;
import net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual.GreatRiteOfSwiftness;
import net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual.WeakRiteOfEmpowering;
import net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual.WeakRiteOfSwiftness;
import net.sfedu.ars_maleficarum.ritual.ImprisonmentRituals.RiteOfLargeDemonicImprisonment;
import net.sfedu.ars_maleficarum.ritual.ImprisonmentRituals.RiteOfMediumDemonicImprisonment;
import net.sfedu.ars_maleficarum.ritual.ImprisonmentRituals.RiteOfSmallDemonicImprisonment;
import net.sfedu.ars_maleficarum.ritual.RitesOfSummoning.RiteOfAbyssalFeast;
import net.sfedu.ars_maleficarum.ritual.craftingRituals.RiteOfForgottenNameAwakening;
import net.sfedu.ars_maleficarum.ritual.craftingRituals.RiteOfPoisonStaffCreation;
import net.sfedu.ars_maleficarum.ritual.craftingRituals.RiteOfPoisonStaffRepair;
import net.sfedu.ars_maleficarum.ritual.craftingRituals.RiteOfPoisonStaffRepairWithAliveLarva;
import net.sfedu.ars_maleficarum.ritual.craftingRituals.craftingRitualsThatRequiresDemon.RiteOfKramerTorchCreation;

import java.util.*;

public abstract class CircleRitual {

    protected enum Dimension {NETHER, OVERWORLD, END, ANY};


    protected RitualCoreEntity.CircleType smallCircleType;
    protected RitualCoreEntity.CircleType mediumCircleType;
    protected RitualCoreEntity.CircleType largeCircleType;
    protected RitualCoreEntity.CircleType coreType;

    protected boolean doesRequireSmallCircle;
    protected boolean doesRequireMediumCircle;
    protected boolean doesRequireLargeCircle;

    protected boolean allComponentsConsumed = false;

    protected SimpleParticleType particleType = ParticleTypes.WITCH;
    protected SoundEvent itemConsumeSound = SoundEvents.ITEM_PICKUP;
    protected float itemConsumeParticleSpeed = 0.2f;

    protected int ticks = 0;
    protected Class<? extends Entity> sacrificeEntity;
    protected Map<Item, Integer> components = new HashMap<Item,Integer>();
    protected String ritualName;


    protected Dimension dimension;
    abstract public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore);
    public boolean doesMatch(SimpleContainer container) {
        for (Item item : components.keySet()) {
            boolean flag = false;
            foundItem: for (int j = 0; j < container.getContainerSize(); j++) {
                if (container.getItem(j).is(item) && container.getItem(j).getCount()>=(components.get(item))) {
                    flag = true;
                    break foundItem;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return ritualName;
    }

    public Map<Item, Integer> getComponents() {
        return components;
    }

    public boolean hasNoBlocksAbove3x3(Level pLevel, BlockPos pPos) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j<=1; j++) {
                for (int h = 1; h <320; h++) {
                    if (!pLevel.getBlockState(pPos.relative(Direction.Axis.X,i).relative(Direction.Axis.Z,j).above(h)).is(Blocks.AIR)) {
                        System.out.println(pPos.relative(Direction.Axis.X,i).relative(Direction.Axis.Z,j).above(h));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void tryToContinue(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {
        riteCore.tryStartRitual(pState,pLevel,pPos,pPlayer);
    }

    public void consumeComponents(Level pLevel, BlockPos pPos, RitualCoreEntity riteCore, Player pPlayer) {
        if (ticks%20==0 && !allComponentsConsumed) {
            for (Map.Entry<Item, Integer> requiredItem : components.entrySet()) {

                int amount = requiredItem.getValue();
                if (amount>=1) {
                    try {
                        ticks=0;
                        ItemEntity item = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pPos.relative(Direction.Axis.Z,-5).relative(Direction.Axis.X,-5).relative(Direction.Axis.Y,-5),pPos.relative(Direction.Axis.Z,5).relative(Direction.Axis.X,5).relative(Direction.Axis.Y,5)))
                                .stream().filter(x->x.getItem().is(requiredItem.getKey())).findAny().get();
                        
                        double d0 = item.position().x;
                        double d1 = item.position().y;
                        double d2 = item.position().z;
                        ((ServerLevel)pLevel).sendParticles(particleType, d0, d1, d2, 20, 0,0.5D,0,itemConsumeParticleSpeed);
                        int toTake = Math.min(amount,item.getItem().getCount());
                        components.computeIfPresent(item.getItem().getItem(),(k,v)->v-toTake);
                        item.getItem().shrink(toTake);
                        pLevel.playSound(null, pPos, itemConsumeSound, SoundSource.PLAYERS,1F,1F);
                        break;
                    } catch (NoSuchElementException e) {
                        pPlayer.sendSystemMessage(Component.translatable("ritual.rite_interrupt_by_components"));
                        riteCore.stopRitual();
                    }
                }
            }
            allComponentsConsumed=components.values().stream().allMatch(x->x==0);
        }
    }

    @Override
    public String toString() {
        return ritualName;
    }


    public RitualCoreEntity.CircleType getSmallCircleType() {
        return smallCircleType;
    }
    public RitualCoreEntity.CircleType getMediumCircleType() {
        return mediumCircleType;
    }
    public RitualCoreEntity.CircleType getLargeCircleType() {
        return largeCircleType;
    }
    public RitualCoreEntity.CircleType getCoreType() {
        return coreType;
    }

    public boolean doesRequireSmallCircle() {
        return doesRequireSmallCircle;
    }
    public boolean doesRequireMediumCircle() {
        return doesRequireMediumCircle;
    }
    public boolean doesRequireLargeCircle() {
        return doesRequireLargeCircle;
    }

}

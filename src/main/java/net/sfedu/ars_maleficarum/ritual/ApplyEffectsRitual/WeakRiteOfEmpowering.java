package net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.ApplyEffectOnPlayersRitual;

public class WeakRiteOfEmpowering extends ApplyEffectOnPlayersRitual {

    public WeakRiteOfEmpowering() {
        ritualName="Rife of Empowering";
        smallCircleType= RitualCoreEntity.ChalkType.WHITE;
        mediumCircleType= RitualCoreEntity.ChalkType.WHITE;
        largeCircleType= RitualCoreEntity.ChalkType.ANY;
        coreType= RitualCoreEntity.ChalkType.WHITE;
        components.put(Items.BLAZE_POWDER, 1);
        components.put(ModItems.SAGE_LEAF.get(), 2);
        components.put(ModItems.MANDRAKE_ROOT.get(), 1);
        components.put(Items.COAL, 1);
        components.put(Items.GLOWSTONE_DUST, 1);
        doesRequireLargeCircle=false;
        doesRequireMediumCircle=true;
        doesRequireSmallCircle=true;

        effects.add(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20*60*15, 1));
        playersAmount = 5;
        radius = 7;

    }


}

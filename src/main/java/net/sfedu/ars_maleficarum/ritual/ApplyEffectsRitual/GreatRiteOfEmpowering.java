package net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.RitualType;
import net.sfedu.ars_maleficarum.ritual.RitualTypes;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.ApplyEffectOnPlayersRitual;
import org.checkerframework.checker.units.qual.C;

public class GreatRiteOfEmpowering extends ApplyEffectOnPlayersRitual {

    public GreatRiteOfEmpowering(RitualType<?> type) {
        super(type, RitualCoreEntity.ChalkType.WHITE, RitualCoreEntity.ChalkType.WHITE,RitualCoreEntity.ChalkType.WHITE,RitualCoreEntity.ChalkType.WHITE);
        components.put(Items.BLAZE_ROD, 1);
        components.put(ModItems.ABSOLUTE_ORDER.get(), 1);
        components.put(ModItems.FLINT_KNIFE.get(), 1);
        components.put(ModItems.MANDRAKE_ROOT.get(), 1);
        components.put(ModItems.NAMELESS_CHARCOAL.get(), 1);
        components.put(Items.GLOWSTONE, 1);
        components.put(Items.CRIMSON_FUNGUS, 1);

        effects.add(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20*60*15, 2));
        playersAmount = 3;
        radius = 7;

    }


}

package net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.RitualType;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.ApplyEffectOnPlayersRitual;

public class WeakRiteOfSwiftness extends ApplyEffectOnPlayersRitual {

    public WeakRiteOfSwiftness(RitualType<?> type) {
        super(type, WHITE, WHITE, WHITE, NONE);
        components.put(Items.SUGAR, 1);
        components.put(ModItems.WASTELAND_WIND.get(), 1);
        components.put(ModItems.MANDRAKE_ROOT.get(), 1);
        components.put(Items.REDSTONE, 1);
        components.put(ModItems.SWAMP_ROTFIEND_INGREDIENT.get(), 1);


        effects.add(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20*60*15, 1));
        playersAmount = 5;
        radius = 7;
    }
}

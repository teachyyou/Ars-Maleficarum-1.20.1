package net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.RitualType;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.ApplyEffectOnPlayersRitual;

public class GreatRiteOfSwiftness extends ApplyEffectOnPlayersRitual {

    public GreatRiteOfSwiftness(RitualType<?> type) {
        super(type, WHITE, WHITE, WHITE, WHITE);
        components.put(Items.SUGAR, 2);
        components.put(ModItems.WASTELAND_WIND.get(), 1);
        components.put(ModItems.SOARING_LIGHTNESS.get(), 1);
        components.put(ModItems.MANDRAKE_ROOT.get(), 1);
        components.put(Items.REDSTONE_BLOCK, 1);
        components.put(ModItems.SWAMP_ROTFIEND_INGREDIENT.get(), 1);
        components.put(Items.WARPED_FUNGUS, 1);
        effects.add(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20*60*15, 2));
        playersAmount = 3;
        radius = 7;
    }
}

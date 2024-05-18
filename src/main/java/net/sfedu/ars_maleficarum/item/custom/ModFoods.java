package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    //Свойства ягод рябины как еды
    public static final FoodProperties ROWAN_BERRIES = new FoodProperties.Builder().alwaysEat().fast().nutrition(2)
            .saturationMod(0.2f).build();

    public static final FoodProperties TIRED_SWALLOW_POTION_PROPERTIES = new FoodProperties.Builder()
            .effect(new MobEffectInstance(MobEffects.HARM, 1, 1), 1F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1F).alwaysEat().build();

    public static final FoodProperties SCORCHED_THUNDER_POTION_PROPERTIES = new FoodProperties.Builder()
            .effect(new MobEffectInstance(MobEffects.BLINDNESS, 250, 0), 1F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 250, 2), 1F).alwaysEat().build();

    public static final FoodProperties MAGMA_BILE_POTION_PROPERTIES = new FoodProperties.Builder()
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0), 1F)
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0), 1F).alwaysEat().build();
}

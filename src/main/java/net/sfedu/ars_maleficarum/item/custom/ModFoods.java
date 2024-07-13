package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties ROWAN_BERRIES = new FoodProperties.Builder().alwaysEat().fast().nutrition(2)
            .saturationMod(0.2f).build();

    public static final FoodProperties EXHAUSTED_SWALLOW_POTION_PROPERTIES = new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 1), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 20*60*2, 1), 1F).alwaysEat().build();

    public static final FoodProperties SPURIOUS_THUNDERBOLT_POTION_PROPERTIES = new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 20*60*2, 1), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20*60, 2), 1F).alwaysEat().build();

    public static final FoodProperties MAGMACUBE_GALL_POTION_PROPERTIES = new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20*60*15, 0), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20*60*15, 0), 1F).alwaysEat().build();

    public static final FoodProperties GOLDEN_CARROT_SOUP = new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 20*60*15, 0), 1F).alwaysEat().nutrition(6).build();
    public static final FoodProperties MANDRAKE_SOUP = new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 20*60*5, 3), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 20*60*5, 1), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20*60*5, 1), 1F)
            .alwaysEat().nutrition(6).build();

    public static final FoodProperties ROWAN_COMPOTE = new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20*60*10, 3), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 20*60*10, 1), 1F)
            .alwaysEat().nutrition(2).fast().build();
}

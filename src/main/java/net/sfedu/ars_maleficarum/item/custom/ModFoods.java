package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    //Свойства ягод рябины как еды
    public static final FoodProperties ROWAN_BERRIES = new FoodProperties.Builder().alwaysEat().fast().nutrition(2)
            .saturationMod(0.2f).build();
}

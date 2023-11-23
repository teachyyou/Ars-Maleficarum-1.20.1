package net.sfedu.ars_maleficarum.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArsMaleficarum.MOD_ID);

    public static final RegistryObject<RecipeSerializer<OdourExtractingRecipe>> ODOUR_EXTRACTING_SERIALIZER =
            SERIALIZERS.register("odour_extracting",()->OdourExtractingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<InfusingAltarRecipe>> ALTAR_INFUSING_SERIALIZER =
            SERIALIZERS.register("altar_infusing",()->InfusingAltarRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventbus) {
        SERIALIZERS.register(eventbus);
    }
}

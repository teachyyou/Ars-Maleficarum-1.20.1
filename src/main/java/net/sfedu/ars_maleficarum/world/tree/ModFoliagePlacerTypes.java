package net.sfedu.ars_maleficarum.world.tree;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.world.tree.custom.RowanFoliagePlacer;

public class ModFoliagePlacerTypes {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, ArsMaleficarum.MOD_ID);


    public static final RegistryObject<FoliagePlacerType<RowanFoliagePlacer>> ROWAN_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("rowan_foliage_placer",()->new FoliagePlacerType<>(RowanFoliagePlacer.CODEC));
    public static void register(IEventBus eventbus) {
        FOLIAGE_PLACERS.register(eventbus);
    }
}

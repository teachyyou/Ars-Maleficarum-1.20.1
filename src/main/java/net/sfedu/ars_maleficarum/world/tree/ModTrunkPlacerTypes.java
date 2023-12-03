package net.sfedu.ars_maleficarum.world.tree;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.world.tree.custom.NamelessTrunkPlacer;
import net.sfedu.ars_maleficarum.world.tree.custom.RowanTrunkPlacer;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, ArsMaleficarum.MOD_ID);

    public static final RegistryObject<TrunkPlacerType<RowanTrunkPlacer>> ROWAN_TRUNK_PLACER =
            TRUNK_PLACERS.register("rowan_trunk_placer",()->new TrunkPlacerType<>(RowanTrunkPlacer.CODEC));

    public static final RegistryObject<TrunkPlacerType<NamelessTrunkPlacer>> NAMELESS_TRUNK_PLACER =
            TRUNK_PLACERS.register("nameless_trunk_placer",()->new TrunkPlacerType<>(NamelessTrunkPlacer.CODEC));

    public static final RegistryObject<TrunkPlacerType<RowanTrunkPlacer>> DEAD_TREE_TRUNK_PLACER =
            TRUNK_PLACERS.register("dead_tree_trunk_placer",()->new TrunkPlacerType<>(RowanTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        TRUNK_PLACERS.register(eventBus);
    }
}

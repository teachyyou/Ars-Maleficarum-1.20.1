package net.sfedu.ars_maleficarum.world;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> ROWAN_PLACED_KEY  = registerKey("rowan_placed");

    public static final ResourceKey<PlacedFeature> DEAD_TREE_PLACED_KEY  = registerKey("dead_tree_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?,?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context,ROWAN_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.ROWAN_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1,0.1f,0),
                        ModBlocks.ROWAN_SAPLING.get()));

        register(context,DEAD_TREE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.DEAD_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1,0.1f,0),
                        ModBlocks.DEAD_TREE_SAPLING.get()));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,new ResourceLocation(ArsMaleficarum.MOD_ID,name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?,?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key,new PlacedFeature(configuration,List.copyOf(modifiers)));
    }
}

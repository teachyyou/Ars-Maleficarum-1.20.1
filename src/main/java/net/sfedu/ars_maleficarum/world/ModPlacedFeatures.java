package net.sfedu.ars_maleficarum.world;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> ROWAN_PLACED_KEY  = registerKey("rowan_placed");

    public static final ResourceKey<PlacedFeature> DEAD_TREE_PLACED_KEY  = registerKey("dead_tree_placed");

    public static final ResourceKey<PlacedFeature> NAMELESS_TREE_PLACED_KEY = registerKey("nameless_tree_placed");

    public static final ResourceKey<PlacedFeature> OVERWORLD_CURSED_GOLD_ORE_PLACED_KEY = registerKey("overworld_cursed_gold_ores_placed");

    public static final ResourceKey<PlacedFeature> OVERWORLD_SILVER_ORE_PLACED_KEY = registerKey("overworld_silver_ores_placed");
    public static final ResourceKey<PlacedFeature> OVERWORLD_SILVER_DEEPSLATE_ORE_PLACED_KEY = registerKey("overworld_silver_deepslate_ores_placed");
    public static final ResourceKey<PlacedFeature> OVERWORLD_CURSED_GOLD_DEEPSLATE_ORE_PLACED_KEY = registerKey("overworld_cursed_gold_deepslate_ores_placed");
    public static final ResourceKey<PlacedFeature> NETHER_CURSED_GOLD_ORE_PLACED_KEY = registerKey("nether_cursed_gold_ores_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?,?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, OVERWORLD_SILVER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SILVER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(9,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(64))));

        register(context, OVERWORLD_CURSED_GOLD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CURSED_GOLD_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(32))));

        register(context, OVERWORLD_SILVER_DEEPSLATE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SILVER_DEEPSLATE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(16))));

        register(context, OVERWORLD_CURSED_GOLD_DEEPSLATE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CURSED_GOLD_DEEPSLATE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(16))));

        register(context, NETHER_CURSED_GOLD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CURSED_GOLD_NETHER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(80))));

        register(context,ROWAN_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.ROWAN_KEY),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(16), ModBlocks.ROWAN_SAPLING.get()));

        register(context,NAMELESS_TREE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.NAMELESS_TREE_KEY),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(16), ModBlocks.NAMELESS_TREE_SAPLING.get()));

        register(context,DEAD_TREE_PLACED_KEY,configuredFeatures.getOrThrow(ModConfiguredFeatures.DEAD_TREE_KEY),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(6), ModBlocks.DEAD_TREE_SAPLING.get()));

    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,new ResourceLocation(ArsMaleficarum.MOD_ID,name));
    }


    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?,?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key,new PlacedFeature(configuration,List.copyOf(modifiers)));
    }
}

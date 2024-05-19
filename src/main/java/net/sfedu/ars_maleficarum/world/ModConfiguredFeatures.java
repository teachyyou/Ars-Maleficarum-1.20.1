package net.sfedu.ars_maleficarum.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.world.decorator.custom.ModMushroomDecorator;
import net.sfedu.ars_maleficarum.world.tree.custom.*;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> ROWAN_KEY = registerKey("rowan");

    public static final ResourceKey<ConfiguredFeature<?,?>> NAMELESS_TREE_KEY = registerKey("nameless_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> DEAD_TREE_KEY = registerKey("dead_tree");

    public static final  ResourceKey<ConfiguredFeature<?,?>> KRAMER_TREE_KEY = registerKey("kramer_tree");

    public static final ResourceKey<ConfiguredFeature<?,?>> CURSED_GOLD_ORE_KEY = registerKey("cursed_gold_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> SILVER_ORE_KEY = registerKey("silver_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> SILVER_DEEPSLATE_ORE_KEY = registerKey("silver_deepslate_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> CURSED_GOLD_DEEPSLATE_ORE_KEY = registerKey("cursed_gold_deepslate_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> CURSED_GOLD_NETHER_ORE_KEY = registerKey("cursed_gold_nether_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context) {
        RuleTest stone_replace = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslate_replace = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest nether_replace = new BlockMatchTest(Blocks.NETHERRACK);
        List<OreConfiguration.TargetBlockState> overworld_Cursed_Ores = List.of(OreConfiguration.target(stone_replace,
                ModBlocks.CURSED_GOLD_ORE_BLOCK.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> overworld_cursed_gold_deepslate_ore = List.of(OreConfiguration.target(deepslate_replace,
                ModBlocks.CURSED_GOLD_DEEPSLATE_ORE_BLOCK.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> overworld_Ores = List.of(OreConfiguration.target(stone_replace,
                ModBlocks.SILVER_ORE_BLOCK.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> overworld_silver_deepslate_ore = List.of(OreConfiguration.target(deepslate_replace,
                ModBlocks.SILVER_DEEPSLATE_ORE_BLOCK.get().defaultBlockState()));

        register(context, CURSED_GOLD_ORE_KEY,Feature.ORE, new OreConfiguration(overworld_Cursed_Ores,9));
        register(context, SILVER_ORE_KEY,Feature.ORE, new OreConfiguration(overworld_Ores,9));
        register(context, CURSED_GOLD_DEEPSLATE_ORE_KEY, Feature.ORE, new OreConfiguration(overworld_cursed_gold_deepslate_ore,9));
        register(context, SILVER_DEEPSLATE_ORE_KEY, Feature.ORE, new OreConfiguration(overworld_silver_deepslate_ore,9));
        register(context, CURSED_GOLD_NETHER_ORE_KEY, Feature.ORE, new OreConfiguration(nether_replace,
                ModBlocks.CURSED_GOLD_NETHER_ORE_BLOCK.get().defaultBlockState(),9));

        register(context,ROWAN_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.ROWAN_LOG.get()),
                new RowanTrunkPlacer(3,0,0),
                BlockStateProvider.simple(ModBlocks.ROWAN_LEAVES.get()),
                new RowanFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),2),
                new TwoLayersFeatureSize(2,1,2)) .build());

        register(context,NAMELESS_TREE_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.NAMELESS_TREE_LOG.get()),
                new NamelessTrunkPlacer(4,2,3),
                BlockStateProvider.simple(ModBlocks.NAMELESS_TREE_LEAVES.get()),
                new NamelessFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),2),
                new TwoLayersFeatureSize(2,1,2)) .build());

        register(context,KRAMER_TREE_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.KRAMER_TREE_LOG.get()),
                new KramerTrunkPlacer(3,0,0),
                BlockStateProvider.simple(ModBlocks.KRAMER_TREE_LEAVES.get()),
                new KramerFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),2),
                new TwoLayersFeatureSize(2,1,2)) .build());

        register(context,DEAD_TREE_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.DEAD_TREE_LOG.get()),
                new NamelessTrunkPlacer(3,2,2),
                BlockStateProvider.simple(Blocks.AIR),
                new NamelessFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),2),
                new TwoLayersFeatureSize(2,1,2)).decorators(ImmutableList.of(ModMushroomDecorator.INSTANCE)).build());
    }

    public static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(ArsMaleficarum.MOD_ID,name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?,?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature,configuration));
    }

}

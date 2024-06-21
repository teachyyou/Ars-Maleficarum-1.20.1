package net.sfedu.ars_maleficarum.world.tree;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.sfedu.ars_maleficarum.world.ModConfiguredFeatures;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class RowanTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return ModConfiguredFeatures.ROWAN_KEY;
    }
}

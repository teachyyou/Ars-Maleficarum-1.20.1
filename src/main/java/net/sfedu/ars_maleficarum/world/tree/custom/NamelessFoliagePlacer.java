package net.sfedu.ars_maleficarum.world.tree.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.sfedu.ars_maleficarum.world.tree.ModFoliagePlacerTypes;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class NamelessFoliagePlacer extends FoliagePlacer {

    public static final Codec<NamelessFoliagePlacer> CODEC = RecordCodecBuilder.create((instance)->foliagePlacerParts(instance)
            .and(Codec.intRange(0,16).fieldOf("height").forGetter(fp->fp.height)).apply(instance,NamelessFoliagePlacer::new));
    public final int height;
    public NamelessFoliagePlacer(IntProvider pRadius, IntProvider pOffset, int height) {
        super(pRadius, pOffset);
        this.height=height;
    }

    @Override
    @NonNull
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.NAMELESS_FOLIAGE_PLACER.get();
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void createFoliage(LevelSimulatedReader pLevel, FoliageSetter foliageSetter, RandomSource pRandom, TreeConfiguration pConfig, int pMaxFreeTreeHeight, FoliageAttachment attachment, int pFoliageHeight, int pFoliageRadius, int pOffset) {
        Direction[] dir = {Direction.NORTH,Direction.EAST,Direction.SOUTH,Direction.WEST};
        tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos());
        BlockPos p = attachment.pos().below();

        for (int i = -2; i<=2; i++) {
            tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.above(i));
            for (int j = 0; j<4; j++) {
                if (i==2 || i==-2) {
                    if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.above(i).relative(dir[j]));
                } else {
                    tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.above(i).relative(dir[j]));
                }
                if (!(i==2 || i==-2)) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.above(i).relative(dir[j]).relative(dir[(j+1)%4]));
            }
        }
        for (int i = 0; i<4; i++) {
            tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.relative(dir[i],2));
            if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.relative(dir[i],2).relative(dir[(i+1+4)%4]));
            if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.relative(dir[i],2).relative(dir[(i-1+4)%4]));
            if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.above().relative(dir[i],2));
            if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.below().relative(dir[i],2));
        }

    }

    @Override
    @ParametersAreNonnullByDefault
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        return this.height;
    }

    @Override
    @ParametersAreNonnullByDefault
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return false;
    }
}

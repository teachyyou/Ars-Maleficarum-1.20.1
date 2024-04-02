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

public class KramerFoliagePlacer extends FoliagePlacer {

    public static final Codec<KramerFoliagePlacer> CODEC = RecordCodecBuilder.create((instance)->foliagePlacerParts(instance)
            .and(Codec.intRange(0,16).fieldOf("height").forGetter(fp->fp.height)).apply(instance, KramerFoliagePlacer::new));
    public final int height;
    public KramerFoliagePlacer(IntProvider pRadius, IntProvider pOffset, int height) {
        super(pRadius, pOffset);
        this.height=height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.KRAMER_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader pLevel, FoliageSetter foliageSetter, RandomSource pRandom, TreeConfiguration pConfig, int pMaxFreeTreeHeight, FoliageAttachment attachment, int pFoliageHeight, int pFoliageRadius, int pOffset) {
        Direction[] dir = {Direction.NORTH,Direction.EAST,Direction.SOUTH,Direction.WEST};
        tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos());
        BlockPos p = attachment.pos().below();

        for (int i = -1; i<=1; i++) {
            tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.above(i));
            for (int j = 0; j<4; j++) {
                if (i==1 || i==-1) {
                    if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.above(i).relative(dir[j]));
                } else {
                    tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.above(i).relative(dir[j]));
                }
                if (!(i==1 || i==-1)) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.above(i).relative(dir[j]).relative(dir[(j+1)%4]));
            }
        }
        for (int i = 0; i<4; i++) {
            tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.relative(dir[i],1));
            if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.relative(dir[i],1).relative(dir[(i+1+4)%4]));
            if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.relative(dir[i],1).relative(dir[(i-1+4)%4]));
            if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.above().relative(dir[i],1));
            if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,p.below().relative(dir[i],1));
        }

    }

    @Override
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        return this.height;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return false;
    }
}

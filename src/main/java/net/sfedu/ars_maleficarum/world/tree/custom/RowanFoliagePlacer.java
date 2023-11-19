package net.sfedu.ars_maleficarum.world.tree.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.sfedu.ars_maleficarum.world.tree.ModFoliagePlacerTypes;

public class RowanFoliagePlacer extends FoliagePlacer {

    public static final Codec<RowanFoliagePlacer> CODEC = RecordCodecBuilder.create((instance)->foliagePlacerParts(instance)
            .and(Codec.intRange(0,16).fieldOf("height").forGetter(fp->fp.height)).apply(instance,RowanFoliagePlacer::new));
    public final int height;
    public RowanFoliagePlacer(IntProvider pRadius, IntProvider pOffset, int height) {
        super(pRadius, pOffset);
        this.height=height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.ROWAN_FOLIAGE_PLACER.get();
    }
    Direction[] dir = {Direction.NORTH,Direction.EAST,Direction.WEST,Direction.SOUTH};
    @Override
    protected void createFoliage(LevelSimulatedReader pLevel, FoliageSetter foliageSetter, RandomSource pRandom, TreeConfiguration pConfig, int pMaxFreeTreeHeight,
                                 FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos());
        tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos().below(2));

        if (pRandom.nextFloat() > 0.25F) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos().above(1));
        for (Direction d : dir) {
            for (int i = -2; i <= 0; i++){
                tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos().above(i).relative(d));
            }
        }
        for (Direction d : dir) {
            if (pRandom.nextBoolean()) tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos().below(1).relative(d,2));

        }
        tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos().relative(Direction.NORTH).relative(Direction.EAST).below(1));
        tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos().relative(Direction.EAST).relative(Direction.SOUTH).below(1));
        tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos().relative(Direction.SOUTH).relative(Direction.WEST).below(1));
        tryPlaceLeaf(pLevel,foliageSetter,pRandom,pConfig,attachment.pos().relative(Direction.WEST).relative(Direction.NORTH).below(1));



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

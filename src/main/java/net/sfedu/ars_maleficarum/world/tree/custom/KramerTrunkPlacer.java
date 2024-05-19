package net.sfedu.ars_maleficarum.world.tree.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.sfedu.ars_maleficarum.world.tree.ModTrunkPlacerTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class KramerTrunkPlacer extends TrunkPlacer {

    public static final Codec<KramerTrunkPlacer> CODEC = RecordCodecBuilder.create(kramerTrunkPlacerInstance->
            trunkPlacerParts(kramerTrunkPlacerInstance).apply(kramerTrunkPlacerInstance, KramerTrunkPlacer::new));
    public KramerTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.KRAMER_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        setDirtAt(pLevel,pBlockSetter,pRandom,pPos.below(),pConfig);

        int height = pFreeTreeHeight + pRandom.nextInt(3);
        height=Math.min(height,5);
        List<FoliagePlacer.FoliageAttachment> foliage = new ArrayList<>();

        Direction[] dir = {Direction.NORTH,Direction.WEST,Direction.SOUTH,Direction.EAST};
        int possibleCurveLevel = 2;
        for (Direction d : dir) {
            pBlockSetter.accept(pPos.relative(d).below(), (BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos.relative(d).below()).setValue(RotatedPillarBlock.AXIS, (d == Direction.SOUTH || d == Direction.NORTH) ? Direction.Axis.Z : Direction.Axis.X)));
            if (pRandom.nextFloat()<=0.75F) {
                placeLog(pLevel, pBlockSetter, pRandom, pPos.relative(d), pConfig);
                if (pRandom.nextFloat() <= 0.15F) {
                    placeLog(pLevel, pBlockSetter, pRandom, pPos.relative(d).above(), pConfig);
                    height++;
                    possibleCurveLevel++;
                }
            }
        }
        for (int i = 0; i < height; i++) {;
            if (i==possibleCurveLevel && pRandom.nextFloat() >= 0.25) pPos=pPos.relative(dir[pRandom.nextInt(4)]);
            placeLog(pLevel,pBlockSetter,pRandom,pPos.above(i),pConfig);
        }
        pPos=pPos.above(height-1);
        foliage.add(new FoliagePlacer.FoliageAttachment(pPos.above(4),3,false));


        boolean hasMissingBranch = false;

        for (int i = 0; i < 4; i++) {
            if (pRandom.nextFloat()>0.9 && !hasMissingBranch) {
                hasMissingBranch = true;
                continue;
            }
            Direction d = dir[i];
            BlockPos tempPos = pPos;
            if (pRandom.nextFloat()<=0.4F) tempPos=tempPos.relative(dir[(i+1)%4]);
            for (int j = 0; j < (height > 4 ? 4 : 3); j++) {
                if (j!=0 && pRandom.nextFloat()<=0.4F) tempPos=tempPos.relative(dir[(i+1)%4]);
                if (j != ((height) > 4 ? 3 : 2)) {
                    placeLog(pLevel, pBlockSetter, pRandom, tempPos.relative(d, j + 1).above(j + 1), pConfig);
                    foliage.add(new FoliagePlacer.FoliageAttachment(tempPos.relative(d, j + 1).above(j + 2),0,false));
                }
                else if (pRandom.nextFloat()<=0.35F) {
                    placeLog(pLevel,pBlockSetter,pRandom,tempPos.relative(d,j+1).above(j+1),pConfig);
                    foliage.add(new FoliagePlacer.FoliageAttachment(tempPos.relative(d, j + 1).above(j + 2),0,false));
                }

            }
        }
        return foliage;
    }
}

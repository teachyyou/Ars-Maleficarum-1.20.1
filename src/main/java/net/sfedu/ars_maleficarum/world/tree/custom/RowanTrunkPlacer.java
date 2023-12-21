package net.sfedu.ars_maleficarum.world.tree.custom;

import com.google.common.collect.ImmutableList;
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
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RowanTrunkPlacer extends TrunkPlacer {

    public static final Codec<RowanTrunkPlacer> CODEC = RecordCodecBuilder.create(rowanTrunkPlacerInstance->
            trunkPlacerParts(rowanTrunkPlacerInstance).apply(rowanTrunkPlacerInstance,RowanTrunkPlacer::new));
    public RowanTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.ROWAN_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom,
                                                            int height, BlockPos pPos, TreeConfiguration pConfig) {

        setDirtAt(pLevel,pBlockSetter,pRandom,pPos.below(),pConfig);
        List<FoliagePlacer.FoliageAttachment> foliage = new ArrayList<>();

        Direction[] dir = {Direction.NORTH,Direction.EAST,Direction.SOUTH,Direction.WEST};

        placeLog(pLevel, pBlockSetter, pRandom, pPos,pConfig);
        int offsetDirInd = pRandom.nextInt(4);
        if (pRandom.nextBoolean()) pPos=pPos.relative(dir[offsetDirInd]);
        if (pRandom.nextBoolean()) pPos=pPos.relative(dir[(offsetDirInd+1)%4]);
        for (int i = 1; i < height; i++) {
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i),pConfig);
        }
        pPos=pPos.above(height-1);
        int branchIndA = pRandom.nextInt(4);
        int branchIndB = (branchIndA+2)%4;
        BlockPos pPosA = pPos.relative(dir[branchIndA]).relative(dir[(branchIndA+1)%4]);
        for (int i = 0; i < 3; i++) {
            placeLog(pLevel, pBlockSetter, pRandom, pPosA.above(i),pConfig);
        }
        foliage.add(new FoliagePlacer.FoliageAttachment(pPosA.above(3),0,false));
        BlockPos pPosB=pPos.relative(dir[branchIndB]).relative(dir[(branchIndB+1)%4]);
        for (int i = 0; i < 2; i++) {
            placeLog(pLevel, pBlockSetter, pRandom, pPosB.above(i),pConfig);
        }
        foliage.add(new FoliagePlacer.FoliageAttachment(pPosB.above(2),0,false));

        return foliage;
    }

}

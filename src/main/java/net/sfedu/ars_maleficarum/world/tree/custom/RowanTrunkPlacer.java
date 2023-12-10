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

    public void placeBranchA(LevelSimulatedReader pLevel, BiConsumer<BlockPos,BlockState> pBlockSetter, RandomSource pRandom, BlockPos pPos, TreeConfiguration pConfig, Direction direction, int h) {
        placeLog(pLevel,pBlockSetter,pRandom,pPos.above(h).relative(direction,1),pConfig);
        placeLog(pLevel,pBlockSetter,pRandom,pPos.above(h+1).relative(direction,2),pConfig);
        placeLog(pLevel,pBlockSetter,pRandom,pPos.above(h+2).relative(direction,2),pConfig);
    }
    public void placeBranchB(LevelSimulatedReader pLevel, BiConsumer<BlockPos,BlockState> pBlockSetter, RandomSource pRandom, BlockPos pPos, TreeConfiguration pConfig, Direction direction, int h) {
        placeLog(pLevel,pBlockSetter,pRandom,pPos.above(h).relative(direction,1),pConfig);
        placeLog(pLevel,pBlockSetter,pRandom,pPos.above(h+1).relative(direction,2),pConfig);
    }
    public void placeBranchC(LevelSimulatedReader pLevel, BiConsumer<BlockPos,BlockState> pBlockSetter, RandomSource pRandom, BlockPos pPos, TreeConfiguration pConfig, Direction direction, int h) {
        placeLog(pLevel,pBlockSetter,pRandom,pPos.above(h).relative(direction,1),pConfig);
        placeLog(pLevel,pBlockSetter,pRandom,pPos.above(h+1).relative(direction,2),pConfig);
        placeLog(pLevel,pBlockSetter,pRandom,pPos.above(h+2).relative(direction,3),pConfig);
    }
    public void placeBranchD(LevelSimulatedReader pLevel, BiConsumer<BlockPos,BlockState> pBlockSetter, RandomSource pRandom, BlockPos pPos, TreeConfiguration pConfig, Direction direction, int h) {
        pBlockSetter.accept(pPos.above(h).relative(direction,1), (BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos.above(h).relative(direction,1)).setValue(RotatedPillarBlock.AXIS, (direction == Direction.SOUTH || direction == Direction.NORTH) ? Direction.Axis.Z : Direction.Axis.X)));
        pBlockSetter.accept(pPos.above(h).relative(direction,2), (BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos.above(h).relative(direction,2)).setValue(RotatedPillarBlock.AXIS, (direction == Direction.SOUTH || direction == Direction.NORTH) ? Direction.Axis.Z : Direction.Axis.X)));
        placeLog(pLevel,pBlockSetter,pRandom,pPos.above(h).relative(direction,1),pConfig);
        placeLog(pLevel,pBlockSetter,pRandom,pPos.above(h).relative(direction,2),pConfig);

    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom,
                                                            int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {

        setDirtAt(pLevel,pBlockSetter,pRandom,pPos.below(),pConfig);

        int height = pFreeTreeHeight + pRandom.nextInt(3);
        height=Math.min(height,7);
        List<FoliagePlacer.FoliageAttachment> foliage = new ArrayList<>();
        foliage.add(new FoliagePlacer.FoliageAttachment(pPos.above(height),0,false));

        Direction[] dir = {Direction.NORTH,Direction.EAST,Direction.WEST,Direction.SOUTH};
        boolean[] branches = {true,true,true,true};



        for (int i = 0; i < height; i++) {
            placeLog(pLevel,pBlockSetter,pRandom,pPos.above(i),pConfig);
            if (i%2==0 && i > 2 && i != height-1) {
                for (int j = 0; j < 4; j++) {
                    int branchType = pRandom.nextInt(4);
                    if (branchType==0 && pRandom.nextBoolean() && branches[j]){
                        placeBranchA(pLevel,pBlockSetter,pRandom,pPos,pConfig,dir[j],i);
                        branches[j]=false;
                        foliage.add(new FoliagePlacer.FoliageAttachment(pPos.above(i+3).relative(dir[j],2),0,true));
                    }
                    else if (branchType==1 && pRandom.nextBoolean() && branches[j]){
                        placeBranchB(pLevel,pBlockSetter,pRandom,pPos,pConfig,dir[j],i);
                        branches[j]=false;
                        foliage.add(new FoliagePlacer.FoliageAttachment(pPos.above(i+2).relative(dir[j],2),0,true));
                    }
                    else if (branchType==2 && pRandom.nextBoolean() && branches[j]){
                        placeBranchC(pLevel,pBlockSetter,pRandom,pPos,pConfig,dir[j],i);
                        branches[j]=false;
                        foliage.add(new FoliagePlacer.FoliageAttachment(pPos.above(i+3).relative(dir[j],3),0,true));
                    }
                    else if (branchType==3 && pRandom.nextBoolean() && branches[j]){
                        placeBranchD(pLevel,pBlockSetter,pRandom,pPos,pConfig,dir[j],i);
                        branches[j]=false;
                        foliage.add(new FoliagePlacer.FoliageAttachment(pPos.above(i+1).relative(dir[j],2),0,true));
                    }

                }

            }
        }


        return foliage;
    }
}

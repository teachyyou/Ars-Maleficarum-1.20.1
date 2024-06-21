package net.sfedu.ars_maleficarum.world.tree.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.sfedu.ars_maleficarum.world.tree.ModTrunkPlacerTypes;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class NamelessTrunkPlacer extends TrunkPlacer {

    public static final Codec<NamelessTrunkPlacer> CODEC = RecordCodecBuilder.create(namelessTrunkPlacerInstance->
            trunkPlacerParts(namelessTrunkPlacerInstance).apply(namelessTrunkPlacerInstance,NamelessTrunkPlacer::new));
    public NamelessTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    @NonNull
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.NAMELESS_TRUNK_PLACER.get();
    }

    @Override
    @ParametersAreNonnullByDefault
    @NonNull
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        setDirtAt(pLevel,pBlockSetter,pRandom,pPos.below(),pConfig);

        int height = pFreeTreeHeight + pRandom.nextInt(3);
        height=Math.min(height,5);
        List<FoliagePlacer.FoliageAttachment> foliage = new ArrayList<>();


        Direction[] dir = {Direction.NORTH,Direction.WEST,Direction.SOUTH,Direction.EAST};

        for (int i = 0; i < height; i++) {
            placeLog(pLevel,pBlockSetter,pRandom,pPos.above(i),pConfig);
        }
        int dirId = pRandom.nextInt(4);
        Direction d = dir[dirId];
        if (pRandom.nextBoolean()) {
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(height).relative(d), pConfig);
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(height + 1).relative(d), pConfig);
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(height + 2).relative(d, 2), pConfig);
            foliage.add(new FoliagePlacer.FoliageAttachment(pPos.above(height + 3).relative(d, 2),0,false));
        } else{
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(height).relative(d), pConfig);
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(height+1).relative(d,2), pConfig);
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(height+2).relative(d,2), pConfig);
            foliage.add(new FoliagePlacer.FoliageAttachment(pPos.above(height + 3).relative(d, 2),0,false));
        }

        if (pRandom.nextBoolean()) {
            dirId=(dirId+1)%4;
            d=dir[dirId];
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(height-1).relative(d).relative(dir[(dirId+1)%4]), pConfig);
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(height).relative(d).relative(dir[(dirId+1)%4]), pConfig);
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(height+1).relative(d,2).relative(dir[(dirId+1)%4],2), pConfig);
            foliage.add(new FoliagePlacer.FoliageAttachment(pPos.above(height + 2).relative(d, 2).relative(dir[(dirId+1)%4],2),0,false));
        }


        return foliage;
    }
}

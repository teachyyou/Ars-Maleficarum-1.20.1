package net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.entity.InfusingAltarBlockEntity;
import net.sfedu.ars_maleficarum.block.custom.entity.ModBlockEntities;

import java.awt.*;

public class RitualCoreEntity extends BlockEntity {

    public enum CircleType {WHITE,NETHER,ENDER,NATURAL};


    public boolean hasProperSmallCircle;
    public CircleType smallCircle;
    public boolean hasProperMediumCircle;
    public CircleType mediumCircle;
    public boolean hasProperLargeCircle;
    public CircleType largeCircle;

    public boolean hasAllProperJoints;
    public boolean hasAllProperCircles;

    private CircleType currentSmallType = CircleType.WHITE;
    private CircleType currentMediumType = CircleType.WHITE;
    private CircleType currentLargeType = CircleType.WHITE;

    public RitualCoreEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.RITUAL_CORE_ENTITY.get(),pPos, pBlockState);
    }

    private void checkForJoints(Level pLevel, BlockPos pPos) {
        Direction[] dirs = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        if (hasProperSmallCircle && hasProperMediumCircle) {
            Block toCheck = Blocks.AIR;
            switch (smallCircle) {
                case NATURAL -> {
                    toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                }
                case NETHER ->  {
                    toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                }
                case WHITE -> {
                    toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                }
                case ENDER ->  {
                    toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                }
            }

            for (int i = -2; i <=2; i+=4) {
                for (int j = -2; j<=2; j+=4) {
                    if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j)).is(toCheck)) {
                        hasAllProperJoints=false;
                        return;
                    }
                }
            }
            for (Direction dir: dirs) {
                if (!pLevel.getBlockState(pPos.relative(dir,3)).is(toCheck)) {
                    hasAllProperJoints=false;
                    return;
                }
            }
        }
        if (hasProperMediumCircle && hasProperLargeCircle) {
            Block toCheck = Blocks.AIR;
            switch (mediumCircle) {
                case NATURAL -> {
                    toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                }
                case NETHER ->  {
                    toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                }
                case WHITE -> {
                    toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                }
                case ENDER ->  {
                    toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                }
            }
            for (int i = -4; i <=4; i+=8) {
                for (int j = -4; j<=4; j+=8) {
                    if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j)).is(toCheck)) {
                        hasAllProperJoints=false;
                        return;
                    }
                }
            }
            for (Direction dir: dirs) {
                if (!pLevel.getBlockState(pPos.relative(dir,5)).is(toCheck)) {
                    hasAllProperJoints=false;
                    return;
                }
            }
        }
        hasAllProperJoints = true;


    }

    private void checkForLargeCircle(Level pLevel, BlockPos pPos, CircleType type) {
        Block toCheck = Blocks.AIR;
        switch (type) {
            case NATURAL -> {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
            case NETHER ->  {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
            case WHITE -> {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
            case ENDER ->  {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
        }
        for (int i = -3; i <= 3; i++) {
            if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,6).relative(Direction.Axis.X, i)).is(toCheck)) {
                hasProperLargeCircle=false;
                return;
            }
            else if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,-6).relative(Direction.Axis.X, i)).is(toCheck)) {
                hasProperLargeCircle=false;
                return;
            }
            else if (!pLevel.getBlockState(pPos.relative(Direction.Axis.X,6).relative(Direction.Axis.Z, i)).is(toCheck)) {
                hasProperLargeCircle=false;
                return;
            }
            else if (!pLevel.getBlockState(pPos.relative(Direction.Axis.X,-6).relative(Direction.Axis.Z, i)).is(toCheck)) {
                hasProperLargeCircle=false;
                return;
            }
        }
        for (int i = -5; i <=5; i+=10) {
            for (int j = -4; j<=4; j+=8) {
                if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j)).is(toCheck)) {
                    hasProperLargeCircle=false;
                    return;
                }
            }
        }
        for (int i = -5; i <=5; i+=10) {
            for (int j = -4; j<=4; j+=8) {
                if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,j).relative(Direction.Axis.X, i)).is(toCheck)) {
                    hasProperLargeCircle=false;
                    return;
                }
            }
        }
        hasProperLargeCircle=true;
        largeCircle=type;
    }

    private void checkForMediumCircle(Level pLevel, BlockPos pPos, CircleType type) {
        Block toCheck = Blocks.AIR;
        switch (type) {
            case NATURAL -> {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
            case NETHER ->  {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
            case WHITE -> {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
            case ENDER ->  {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
        }
        for (int i = -2; i <= 2; i++) {
            if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,4).relative(Direction.Axis.X, i)).is(toCheck)) {
                hasProperMediumCircle=false;
                return;
            }
            else if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,-4).relative(Direction.Axis.X, i)).is(toCheck)) {
                hasProperMediumCircle=false;
                return;
            }
            else if (!pLevel.getBlockState(pPos.relative(Direction.Axis.X,4).relative(Direction.Axis.Z, i)).is(toCheck)) {
                hasProperMediumCircle=false;
                return;
            }
            else if (!pLevel.getBlockState(pPos.relative(Direction.Axis.X,-4).relative(Direction.Axis.Z, i)).is(toCheck)) {
                hasProperMediumCircle=false;
                return;
            }
        }
        for (int i = -3; i <=3; i+=6) {
            for (int j = -3; j<=3; j+=6) {
                if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j)).is(toCheck)) {
                    hasProperMediumCircle=false;
                    return;
                }
            }
        }
        hasProperMediumCircle=true;
        mediumCircle=type;
    }
    private void checkForSmallCircle(Level pLevel, BlockPos pPos, CircleType type) {
        Block toCheck = Blocks.AIR;
        switch (type) {
            case NATURAL -> {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
            case NETHER ->  {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
            case WHITE -> {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
            case ENDER ->  {
                toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
            }
        }
        for (int i = -1; i <= 1; i++) {
            if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,2).relative(Direction.Axis.X, i)).is(toCheck)) {
                hasProperSmallCircle=false;
                return;
            }
            else if (!pLevel.getBlockState(pPos.relative(Direction.Axis.Z,-2).relative(Direction.Axis.X, i)).is(toCheck)) {
                hasProperSmallCircle=false;
                return;
            }
            else if (!pLevel.getBlockState(pPos.relative(Direction.Axis.X,2).relative(Direction.Axis.Z, i)).is(toCheck)) {
                hasProperSmallCircle=false;
                return;
            }
            else if (!pLevel.getBlockState(pPos.relative(Direction.Axis.X,-2).relative(Direction.Axis.Z, i)).is(toCheck)) {
                hasProperSmallCircle=false;
                return;
            }
        }
        hasProperSmallCircle=true;
        smallCircle=type;
    }
    public void checkForCircles(Level pLevel, BlockPos pPos) {
        checkForSmallCircle(pLevel, pPos, currentSmallType);
        checkForMediumCircle(pLevel, pPos, currentMediumType);
        checkForLargeCircle(pLevel, pPos, currentLargeType);
        checkForJoints(pLevel, pPos);
        hasAllProperCircles = hasProperSmallCircle && hasProperMediumCircle && hasProperLargeCircle && hasAllProperJoints;
    }
    private boolean canSurvive(Level pLevel, BlockPos pPos) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j<=1; j++) {
                BlockPos pos = pPos.relative(Direction.Axis.X, i).relative(Direction.Axis.Z,j);
                if ((pLevel.getBlockState(pos.below()).is(Blocks.AIR) || !pLevel.getBlockState(pos).is(Blocks.AIR)) && (pos!=pPos)) {
                    return false;
                }
            }
        }
        return true;
    }
    public void tick(Level level, BlockPos pPos, BlockState pState) {
        if (!canSurvive(level, pPos)) {
            level.removeBlock(pPos,false);
        }

    }

}

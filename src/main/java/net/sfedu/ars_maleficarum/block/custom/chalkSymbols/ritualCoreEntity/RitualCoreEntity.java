package net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.RitualCircleCore;
import net.sfedu.ars_maleficarum.block.custom.entity.ModBlockEntities;
import net.sfedu.ars_maleficarum.ritual.RitualType;
import net.sfedu.ars_maleficarum.ritual.RitualTypes;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RitualCoreEntity extends BlockEntity {

    public enum CircleSize implements StringRepresentable {SMALL, MEDIUM, LARGE, CORE;
        @Override
        public @NotNull String getSerializedName() {
            switch (this) {
                case SMALL -> {
                    return "small";
                }
                case MEDIUM -> {
                    return "medium";
                }
                case LARGE -> {
                    return "large";
                }
                default -> {
                    return "core";
                }
            }
        }
    };

    public enum ChalkType implements StringRepresentable {WHITE,NETHER,ENDER,NATURAL,ANY,NONE;
        @Override
        public @NotNull String getSerializedName() {
            switch (this) {
                case WHITE -> {
                    return "white";
                }
                case NATURAL -> {
                    return "green";
                }
                case NETHER -> {
                    return "crimson";
                }
                case ENDER -> {
                    return "ender";
                }
                case NONE -> {
                    return "none";
                }
                default -> {
                    return "any";
                }
            }
        }
    };


    public boolean hasProperSmallCircle;
    public ChalkType smallCircle;
    public boolean hasProperMediumCircle;
    public ChalkType mediumCircle;
    public boolean hasProperLargeCircle;
    public ChalkType largeCircle;

    public boolean hasProperCore;
    public ChalkType core;

    private CircleRitual ritual;
    private Player player;

    private boolean executingRitual = false;

    public boolean hasAllProperJoints;
    public boolean hasAllProperCircles;

    private ChalkType currentSmallType = ChalkType.WHITE;
    private ChalkType currentMediumType = ChalkType.WHITE;
    private ChalkType currentLargeType = ChalkType.WHITE;
    private ChalkType currentCoreType = ChalkType.WHITE;

    private boolean isSmallRequired = false;
    private boolean isMediumRequired = false;
    private boolean isLargeRequired = false;

    private final List<BlockPos> smallCirclePositions = new ArrayList<>(12);
    private final List<BlockPos> smallCircleJointsPositions = new ArrayList<>(8);

    private final List<BlockPos> mediumCirclePositions = new ArrayList<>(24);
    private final List<BlockPos> mediumCircleJointsPositions = new ArrayList<>(8);

    private final List<BlockPos> largeCirclePositions = new ArrayList<>(36);




    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    //Чуть-чуть костыльно
    public final ItemStackHandler itemHandler = new ItemStackHandler(6);

    public RitualCoreEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.RITUAL_CORE_ENTITY.get(),pPos, pBlockState);
    }

    private void checkForJoints(Level pLevel, BlockPos pPos) {
        smallCircleJointsPositions.clear();
        mediumCircleJointsPositions.clear();
        Direction[] dirs = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        if (hasProperSmallCircle && hasProperMediumCircle && isSmallRequired && isMediumRequired) {
            for (int i = -2; i <=2; i+=4) {
                for (int j = -2; j<=2; j+=4) {
                    BlockPos toCheck = pPos.relative(Direction.Axis.Z, i).relative(Direction.Axis.X, j);
                    smallCircleJointsPositions.add(toCheck);
                    if (!checkForGlyphType(pLevel, toCheck,smallCircle, -1)) {
                        hasAllProperJoints=false;
                        return;
                    }
                }
            }
            for (Direction dir: dirs) {
                BlockPos toCheck = pPos.relative(dir, 3);
                smallCircleJointsPositions.add(toCheck);
                if (!checkForGlyphType(pLevel, toCheck,smallCircle, -1)) {
                    hasAllProperJoints=false;
                    return;
                }
            }
        }
        if (hasProperMediumCircle && hasProperLargeCircle && isMediumRequired && isLargeRequired) {
            for (int i = -4; i <=4; i+=8) {
                for (int j = -4; j<=4; j+=8) {
                    BlockPos toCheck = pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j);
                    mediumCircleJointsPositions.add(toCheck);
                    if (!checkForGlyphType(pLevel,toCheck,mediumCircle, -1)) {
                        hasAllProperJoints=false;
                        return;
                    }
                }
            }
            for (Direction dir: dirs) {
                BlockPos toCheck = pPos.relative(dir,5);
                mediumCircleJointsPositions.add(toCheck);
                if (!checkForGlyphType(pLevel,toCheck,mediumCircle, -1)) {
                    hasAllProperJoints=false;
                    return;
                }
            }
        }
        hasAllProperJoints = true;
    }

    private void checkForLargeCircle(Level pLevel, BlockPos pPos) {
        largeCirclePositions.clear();
        for (int i = -3; i <= 3; i++) {
            BlockPos pos1 = pPos.relative(Direction.Axis.Z,6).relative(Direction.Axis.X, i);
            BlockPos pos2 = pPos.relative(Direction.Axis.Z,-6).relative(Direction.Axis.X, i);
            BlockPos pos3 = pPos.relative(Direction.Axis.X,6).relative(Direction.Axis.Z, i);
            BlockPos pos4 = pPos.relative(Direction.Axis.X,-6).relative(Direction.Axis.Z, i);
            largeCirclePositions.add(pos1);
            largeCirclePositions.add(pos2);
            largeCirclePositions.add(pos3);
            largeCirclePositions.add(pos4);
            if (!checkForGlyphType(pLevel, pos1, currentLargeType, 2)) {
                hasProperLargeCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel, pos2, currentLargeType, 2)) {
                hasProperLargeCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel, pos3, currentLargeType, 2)) {
                hasProperLargeCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel, pos4, currentLargeType, 2)) {
                hasProperLargeCircle=false;
                return;
            }
        }
        for (int i = -5; i <=5; i+=10) {
            for (int j = -4; j<=4; j+=8) {
                BlockPos pos5 = pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j);
                largeCirclePositions.add(pos5);
                if (!checkForGlyphType(pLevel, pos5, currentLargeType, 2)) {
                    hasProperLargeCircle=false;
                    return;
                }
            }
        }
        for (int i = -5; i <=5; i+=10) {
            for (int j = -4; j<=4; j+=8) {
                BlockPos pos6 = pPos.relative(Direction.Axis.Z,j).relative(Direction.Axis.X, i);
                largeCirclePositions.add(pos6);
                if (!checkForGlyphType(pLevel, pos6, currentLargeType,2)) {
                    hasProperLargeCircle=false;
                    return;
                }
            }
        }
        hasProperLargeCircle=true;
        largeCircle=currentLargeType;
    }

    private void checkForMediumCircle(Level pLevel, BlockPos pPos) {
        mediumCirclePositions.clear();
        for (int i = -2; i <= 2; i++) {
            BlockPos pos1 = pPos.relative(Direction.Axis.Z,4).relative(Direction.Axis.X, i);
            BlockPos pos2 = pPos.relative(Direction.Axis.Z,-4).relative(Direction.Axis.X, i);
            BlockPos pos3 = pPos.relative(Direction.Axis.X,4).relative(Direction.Axis.Z, i);
            BlockPos pos4 = pPos.relative(Direction.Axis.X,-4).relative(Direction.Axis.Z, i);
            mediumCirclePositions.add(pos1);
            mediumCirclePositions.add(pos2);
            mediumCirclePositions.add(pos3);
            mediumCirclePositions.add(pos4);
            if (!checkForGlyphType(pLevel, pos1, currentMediumType, 1)) {
                hasProperMediumCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel, pos2, currentMediumType, 1)) {
                hasProperMediumCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel, pos3, currentMediumType, 1)) {
                hasProperMediumCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel, pos4, currentMediumType, 1)) {
                hasProperMediumCircle=false;
                return;
            }
        }
        for (int i = -3; i <=3; i+=6) {
            for (int j = -3; j<=3; j+=6) {
                BlockPos pos5 = pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j);
                mediumCirclePositions.add(pos5);
                if (!checkForGlyphType(pLevel, pos5, currentMediumType, 1)) {
                    hasProperMediumCircle=false;
                    return;
                }
            }
        }
        hasProperMediumCircle=true;
        mediumCircle=currentMediumType;
    }
    private void checkForSmallCircle(Level pLevel, BlockPos pPos) {
        smallCirclePositions.clear();
        for (int i = -1; i <= 1; i++) {
            BlockPos pos1 = pPos.relative(Direction.Axis.Z,2).relative(Direction.Axis.X, i);
            BlockPos pos2 = pPos.relative(Direction.Axis.Z,-2).relative(Direction.Axis.X, i);
            BlockPos pos3 = pPos.relative(Direction.Axis.X,2).relative(Direction.Axis.Z, i);
            BlockPos pos4 = pPos.relative(Direction.Axis.X,-2).relative(Direction.Axis.Z, i);
            smallCirclePositions.add(pos1);
            smallCirclePositions.add(pos2);
            smallCirclePositions.add(pos3);
            smallCirclePositions.add(pos4);
            if (!checkForGlyphType(pLevel, pos1, currentSmallType, 0)) {
                hasProperSmallCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel, pos2, currentSmallType, 0)) {
                hasProperSmallCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel, pos3, currentSmallType, 0)) {
                hasProperSmallCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel, pos4, currentSmallType, 0)) {
                hasProperSmallCircle=false;
                return;
            }
        }

        hasProperSmallCircle=true;
        smallCircle=currentSmallType;
    }

    //todo добавить остальные типы и убрать редстоун
    private ChalkType getTypeFromBlock(Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockState(pPos).is(ModBlocks.WHITE_CHALK_SYMBOL.get())) return ChalkType.WHITE;
        else if (pLevel.getBlockState(pPos).is(ModBlocks.GREEN_CHALK_SYMBOL.get())) return ChalkType.NATURAL;
        else if (pLevel.getBlockState(pPos).is(ModBlocks.CRIMSON_CHALK_SYMBOL.get())) return ChalkType.NETHER;
        return ChalkType.ANY;
    }

    //TODO: добавить все остальные по мере добавления (и убрать редстоун как бы)
    private boolean checkForGlyphType(Level pLevel, BlockPos pPos, ChalkType type, int id /*-1 = joints, 0 = small, 1 = medium, 2 = large*/) {
        Block toCheck = Blocks.AIR;
        if (type != ChalkType.ANY) {
            switch (type) {
                case NATURAL -> {
                    toCheck = ModBlocks.GREEN_CHALK_SYMBOL.get();
                }
                case NETHER ->  {
                    toCheck = ModBlocks.CRIMSON_CHALK_SYMBOL.get();
                }
                case WHITE -> {
                    toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                }
                case ENDER ->  {
                    toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                }
            }
            return pLevel.getBlockState(pPos).is(toCheck);
        } else {
            //TODO: добавить все остальные по мере добавления (и убрать редстоун как бы)
            Block[] allSymbols = {ModBlocks.GREEN_CHALK_SYMBOL.get(), ModBlocks.WHITE_CHALK_SYMBOL.get(), ModBlocks.CRIMSON_CHALK_SYMBOL.get()};
            for (Block symbol : allSymbols) {
                if (pLevel.getBlockState(pPos).is(symbol)) {
                    switch (id) {
                        case 0 ->{
                            currentSmallType=getTypeFromBlock(pLevel,pPos);
                        }
                        case 1 ->{
                            currentMediumType=getTypeFromBlock(pLevel,pPos);
                        }
                        case 2 ->{
                            currentLargeType=getTypeFromBlock(pLevel,pPos);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void checkForCore(Level pLevel, BlockPos pPos, ChalkType type) {
        ChalkType coreType = pLevel.getBlockState(pPos).getValue(RitualCircleCore.CIRCLETYPE);
        switch (type) {
            case WHITE -> {
                hasProperCore = coreType== ChalkType.WHITE;
                if (hasProperCore) core = ChalkType.WHITE;
            }
            case NATURAL -> {
                hasProperCore = coreType== ChalkType.NATURAL;
                if (hasProperCore) core = ChalkType.NATURAL;

            }
            case NETHER -> {
                hasProperCore = coreType== ChalkType.NETHER;
                if (hasProperCore) core = ChalkType.NETHER;

            }
            case ANY -> {
                hasProperCore = true;
            }
            default ->  {
                hasProperCore = false;
            }
        }

    }
    public boolean checkForCircles(Level pLevel, BlockPos pPos) {
        checkForSmallCircle(pLevel, pPos);
        checkForMediumCircle(pLevel, pPos);
        checkForLargeCircle(pLevel, pPos);
        checkForJoints(pLevel, pPos);
        checkForCore(pLevel, pPos, currentCoreType);

        hasAllProperCircles = hasProperCore;
        if (isSmallRequired) hasAllProperCircles &= hasProperSmallCircle;
        if (isMediumRequired) hasAllProperCircles &= hasProperMediumCircle;
        if (isLargeRequired) hasAllProperCircles &= hasProperLargeCircle;
        hasAllProperCircles &= hasAllProperJoints;
        if (!hasAllProperCircles && executingRitual) executingRitual = false;
        return hasAllProperCircles;
    }
    private boolean canSurvive(Level pLevel, BlockPos pPos) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j<=1; j++) {
                BlockPos pos = pPos.relative(Direction.Axis.X, i).relative(Direction.Axis.Z,j);
                if ((pLevel.getBlockState(pos.below()).is(Blocks.AIR) || !(pLevel.getBlockState(pos).is(Blocks.AIR) || pLevel.getBlockState(pos).is(Blocks.FIRE)  || pLevel.getBlockState(pos).is(BlockTags.CANDLES)) && (pos!=pPos))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isExecutingRitual() {
        return executingRitual;
    }

    public List<BlockPos> smallCircle() {
        return smallCirclePositions;
    }

    public List<BlockPos> smallJoints() {
        return smallCircleJointsPositions;
    }

    public List<BlockPos> mediumCircle() {
        return mediumCirclePositions;
    }
    public List<BlockPos> mediumJoints() {
        return mediumCircleJointsPositions;
    }

    public List<BlockPos> largeCircle() {
        return largeCirclePositions;
    }

    public List<BlockPos> getAllPositions() {
        List<BlockPos> toReturn = new ArrayList<>();
        toReturn.addAll(smallCirclePositions);
        toReturn.addAll(smallCircleJointsPositions);
        toReturn.addAll(mediumCirclePositions);
        toReturn.addAll(mediumCircleJointsPositions);
        toReturn.addAll(largeCirclePositions);
        return toReturn;
    }



    public void tryStartRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {

        List<ItemEntity> items = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pPos.relative(Direction.Axis.Z,-5).relative(Direction.Axis.X,-5).relative(Direction.Axis.Y,-5),pPos.relative(Direction.Axis.Z,5).relative(Direction.Axis.X,5).relative(Direction.Axis.Y,5)));
        SimpleContainer container = new SimpleContainer(items.size());
        for (ItemEntity item : items) {
            container.addItem(item.getItem());
        }

        for (RitualType<?> ritualType : RitualTypes.getEntries()) {
            ritual = ritualType.create();
            currentSmallType = ritual.doesRequireSmallCircle() ?  ritual.getSmallCircleType() : ChalkType.ANY;
            currentMediumType = ritual.doesRequireMediumCircle() ?  ritual.getMediumCircleType() : ChalkType.ANY;
            currentLargeType = ritual.doesRequireLargeCircle() ?  ritual.getLargeCircleType() : ChalkType.ANY;
            currentCoreType = ritual.getCoreType();

            isLargeRequired = ritual.doesRequireLargeCircle();
            isMediumRequired = ritual.doesRequireMediumCircle();
            isSmallRequired = ritual.doesRequireSmallCircle();
            if (checkForCircles(pLevel, pPos) && ritual.doesMatch(container)) {
                executingRitual = true;
                player = pPlayer;
                break;

            }
        }

    }
    public void stopRitual(){
        executingRitual = false;
        ritual = null;
        player = null;
    }
    public void tick(Level level, BlockPos pPos, BlockState pState) {
        if (!canSurvive(level, pPos)) {
            level.removeBlock(pPos,false);
        }
        if (hasAllProperCircles && executingRitual && ritual!=null) {
            ritual.executeRitual(pState, level, pPos, player, this);
        }

    }


    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putBoolean("hasAllProperCircles",this.hasAllProperCircles);
        pTag.putBoolean("hasAllProperJoints",this.hasAllProperCircles);
        pTag.putBoolean("hasProperSmall",this.hasProperSmallCircle);
        pTag.putBoolean("hasProperMedium",this.hasProperMediumCircle);
        pTag.putBoolean("hasProperLarge",this.hasProperLargeCircle);
        super.saveAdditional(pTag);
    }
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.hasAllProperCircles = pTag.getBoolean("hasAllProperCircles");
        this.hasProperSmallCircle = pTag.getBoolean("hasProperSmall");
        this.hasProperMediumCircle = pTag.getBoolean("hasProperMedium");
        this.hasProperLargeCircle = pTag.getBoolean("hasProperLarge");
        this.hasAllProperJoints = pTag.getBoolean("hasAllProperJoints");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net,pkt);
    }

}

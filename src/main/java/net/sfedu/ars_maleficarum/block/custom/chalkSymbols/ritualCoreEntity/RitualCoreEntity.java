package net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ChalkSymbol;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.RitualCircleCore;
import net.sfedu.ars_maleficarum.block.custom.entity.InfusingAltarBlockEntity;
import net.sfedu.ars_maleficarum.block.custom.entity.ModBlockEntities;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.ritual.CircleRitual;
import net.sfedu.ars_maleficarum.ritual.RisingSunRitual;
import org.apache.logging.log4j.core.Core;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class RitualCoreEntity extends BlockEntity {

    public enum CircleType {WHITE,NETHER,ENDER,NATURAL,ANY};

    public enum CircleColor implements StringRepresentable {WHITE,GREEN, /*RED, PURPLE, BLACK TODO: добавить остальные по мере реализации*/;

        @Override
        public @NotNull String getSerializedName() {
            switch (this) {
                case WHITE -> {
                    return "white";
                }
                case GREEN -> {
                    return "green";
                }
                /* TODO: добавить остальные по мере реализации
                case RED -> {
                    return "red";
                }
                case PURPLE -> {
                    return "purple";
                }
                case BLACK -> {
                    return "black";
                }
                */
            }
            return "";
        }
    };


    public boolean hasProperSmallCircle;
    public CircleType smallCircle;
    public boolean hasProperMediumCircle;
    public CircleType mediumCircle;
    public boolean hasProperLargeCircle;
    public CircleType largeCircle;

    public boolean hasProperCore;
    public CircleType core;

    private CircleRitual ritual;
    private Player player;

    private boolean executingRitual = false;

    public boolean hasAllProperJoints;
    public boolean hasAllProperCircles;

    private CircleType currentSmallType = CircleType.WHITE;
    private CircleType currentMediumType = CircleType.WHITE;
    private CircleType currentLargeType = CircleType.WHITE;
    private CircleType currentCoreType = CircleType.WHITE;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    //Чуть-чуть костыльно
    public final ItemStackHandler itemHandler = new ItemStackHandler(6);

    public RitualCoreEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.RITUAL_CORE_ENTITY.get(),pPos, pBlockState);
    }

    private void checkForJoints(Level pLevel, BlockPos pPos) {
        Direction[] dirs = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        if (hasProperSmallCircle && hasProperMediumCircle) {
            for (int i = -2; i <=2; i+=4) {
                for (int j = -2; j<=2; j+=4) {
                    if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j),smallCircle)) {
                        hasAllProperJoints=false;
                        return;
                    }
                }
            }
            for (Direction dir: dirs) {
                if (!checkForGlyphType(pLevel,pPos.relative(dir,3),smallCircle)) {
                    hasAllProperJoints=false;
                    return;
                }
            }
        }
        if (hasProperMediumCircle && hasProperLargeCircle) {
            for (int i = -4; i <=4; i+=8) {
                for (int j = -4; j<=4; j+=8) {
                    if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j),mediumCircle)) {
                        hasAllProperJoints=false;
                        return;
                    }
                }
            }
            for (Direction dir: dirs) {
                if (!checkForGlyphType(pLevel,pPos.relative(dir,5),mediumCircle)) {
                    hasAllProperJoints=false;
                    return;
                }
            }
        }
        hasAllProperJoints = true;
    }

    private void checkForLargeCircle(Level pLevel, BlockPos pPos, CircleType type) {
        for (int i = -3; i <= 3; i++) {
            if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,6).relative(Direction.Axis.X, i),type)) {
                hasProperLargeCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,-6).relative(Direction.Axis.X, i),type)) {
                hasProperLargeCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.X,6).relative(Direction.Axis.Z, i),type)) {
                hasProperLargeCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.X,-6).relative(Direction.Axis.Z, i),type)) {
                hasProperLargeCircle=false;
                return;
            }
        }
        for (int i = -5; i <=5; i+=10) {
            for (int j = -4; j<=4; j+=8) {
                if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j),type)) {
                    hasProperLargeCircle=false;
                    return;
                }
            }
        }
        for (int i = -5; i <=5; i+=10) {
            for (int j = -4; j<=4; j+=8) {
                if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,j).relative(Direction.Axis.X, i),type)) {
                    hasProperLargeCircle=false;
                    return;
                }
            }
        }
        hasProperLargeCircle=true;
        largeCircle=getTypeFromBlock(pLevel,pPos.relative(Direction.Axis.Z,6).relative(Direction.Axis.X, 0));
    }

    private void checkForMediumCircle(Level pLevel, BlockPos pPos, CircleType type) {

        for (int i = -2; i <= 2; i++) {
            if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,4).relative(Direction.Axis.X, i),type)) {
                hasProperMediumCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,-4).relative(Direction.Axis.X, i),type)) {
                hasProperMediumCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.X,4).relative(Direction.Axis.Z, i),type)) {
                hasProperMediumCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.X,-4).relative(Direction.Axis.Z, i),type)) {
                hasProperMediumCircle=false;
                return;
            }
        }
        for (int i = -3; i <=3; i+=6) {
            for (int j = -3; j<=3; j+=6) {
                if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,i).relative(Direction.Axis.X, j),type)) {
                    hasProperMediumCircle=false;
                    return;
                }
            }
        }
        hasProperMediumCircle=true;
        mediumCircle=getTypeFromBlock(pLevel,pPos.relative(Direction.Axis.Z,4).relative(Direction.Axis.X, 0));
    }
    private void checkForSmallCircle(Level pLevel, BlockPos pPos, CircleType type) {
        for (int i = -1; i <= 1; i++) {
            if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,2).relative(Direction.Axis.X, i),type)) {
                hasProperSmallCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.Z,-2).relative(Direction.Axis.X, i),type)) {
                hasProperSmallCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.X,2).relative(Direction.Axis.Z, i),type)) {
                hasProperSmallCircle=false;
                return;
            }
            else if (!checkForGlyphType(pLevel,pPos.relative(Direction.Axis.X,-2).relative(Direction.Axis.Z, i),type)) {
                hasProperSmallCircle=false;
                return;
            }
        }

        hasProperSmallCircle=true;
        smallCircle=getTypeFromBlock(pLevel,pPos.relative(Direction.Axis.Z,2).relative(Direction.Axis.X, 0));
    }

    //todo добавить остальные типы и убрать редстоун
    private CircleType getTypeFromBlock(Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockState(pPos).is(ModBlocks.WHITE_CHALK_SYMBOL.get())) return CircleType.WHITE;
        else if (pLevel.getBlockState(pPos).is(ModBlocks.GREEN_CHALK_SYMBOL.get())) return CircleType.NATURAL;
        else if (pLevel.getBlockState(pPos).is(Blocks.REDSTONE_WIRE)) return CircleType.NETHER;
        return CircleType.ANY;
    }

    //TODO: добавить все остальные по мере добавления (и убрать редстоун как бы)
    private boolean checkForGlyphType(Level pLevel, BlockPos pPos, CircleType type) {
        Block toCheck = Blocks.AIR;
        if (type != CircleType.ANY) {
            switch (type) {
                case NATURAL -> {
                    toCheck = ModBlocks.GREEN_CHALK_SYMBOL.get();
                }
                case NETHER ->  {
                    //toCheck = ModBlocks.WHITE_CHALK_SYMBOL.get();
                    toCheck = Blocks.REDSTONE_WIRE;
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
            Block allSymbols[] = {ModBlocks.GREEN_CHALK_SYMBOL.get(), ModBlocks.WHITE_CHALK_SYMBOL.get(), Blocks.REDSTONE_WIRE};
            for (Block symbol : allSymbols) {
                if (pLevel.getBlockState(pPos).is(symbol)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkForCore(Level pLevel, BlockPos pPos, CircleType type) {
        CircleColor CoreColor = pLevel.getBlockState(pPos).getValue(RitualCircleCore.CIRCLETYPE);
        switch (type) {
            case WHITE -> {
                if (CoreColor==CircleColor.WHITE) {
                    hasProperCore = true;
                    core = CircleType.WHITE;
                    return true;
                }
            }
            case NATURAL -> {
                if (CoreColor==CircleColor.GREEN) {
                    hasProperCore = true;
                    core = CircleType.NATURAL;
                    return true;
                }
            }
            case ANY -> {
                hasProperCore = true;
                return true;
            }
        }
        hasProperCore = false;
        return false;
    }
    public boolean checkForCircles(Level pLevel, BlockPos pPos) {
        checkForSmallCircle(pLevel, pPos, currentSmallType);
        checkForMediumCircle(pLevel, pPos, currentMediumType);
        checkForLargeCircle(pLevel, pPos, currentLargeType);
        checkForJoints(pLevel, pPos);
        checkForCore(pLevel, pPos, currentCoreType);
        hasAllProperCircles = hasProperSmallCircle && hasProperMediumCircle && hasProperLargeCircle && hasAllProperJoints && hasProperCore;
        if (!hasAllProperCircles && executingRitual) executingRitual = false;
        return hasAllProperCircles;
    }
    private boolean canSurvive(Level pLevel, BlockPos pPos) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j<=1; j++) {
                BlockPos pos = pPos.relative(Direction.Axis.X, i).relative(Direction.Axis.Z,j);
                if ((pLevel.getBlockState(pos.below()).is(Blocks.AIR) || !(pLevel.getBlockState(pos).is(Blocks.AIR) || pLevel.getBlockState(pos).is(Blocks.FIRE))) && (pos!=pPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isExecutingRitual() {
        return executingRitual;
    }


    public void tryStartRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {

        //TODO: каким-то магическим образом засунуть в ritual желаемый ритуал и сунуть в container все предметы, что лежат в кругу
        //SimpleContainer container = new SimpleContainer(Items.SUNFLOWER.getDefaultInstance(), ModItems.SUNLIGHT_FLOWER.get().getDefaultInstance(), ModItems.RING_OF_MORNING_DEW.get().getDefaultInstance());


        List<ItemEntity> items = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pPos.relative(Direction.Axis.Z,-5).relative(Direction.Axis.X,-5).relative(Direction.Axis.Y,-5),pPos.relative(Direction.Axis.Z,5).relative(Direction.Axis.X,5).relative(Direction.Axis.Y,5)));
        //items.get(0).
        SimpleContainer container = new SimpleContainer(items.size());
        for (ItemEntity item : items) {
            container.addItem(item.getItem());
        }
        ritual = new RisingSunRitual();
        ritual.addItemEntities(items);


        currentSmallType=ritual.getSmallCircleType();
        currentMediumType=ritual.getMediumCircleType();
        currentLargeType=ritual.getLargeCircleType();
        currentCoreType=ritual.getCoreType();
        if (checkForCircles(pLevel, pPos) && ritual.doesMatch(container)) {
            executingRitual = true;
            player = pPlayer;
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
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net,pkt);
    }

}

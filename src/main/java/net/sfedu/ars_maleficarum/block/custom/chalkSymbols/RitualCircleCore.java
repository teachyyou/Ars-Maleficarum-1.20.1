package net.sfedu.ars_maleficarum.block.custom.chalkSymbols;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.block.custom.entity.ModBlockEntities;
import net.sfedu.ars_maleficarum.item.ModItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class RitualCircleCore extends BaseEntityBlock {
    public RitualCircleCore(Properties pProperties) {
        super(pProperties.destroyTime(5));
    }

    //Фильтр того, что тип белый, адский или природный (для blockstate датаген билдера)
    private static final Predicate<RitualCoreEntity.ChalkType> coreColor = (type) ->(type.ordinal() <= 2);

    public static final EnumProperty<RitualCoreEntity.ChalkType> CIRCLE_TYPE = EnumProperty.create("circletype", RitualCoreEntity.ChalkType.class, coreColor);

    public static final VoxelShape SHAPE = Block.box(-8,0,-8,24,1,24);

    @Override
    @ParametersAreNonnullByDefault
    public float getDestroyProgress(BlockState blockState, Player player, BlockGetter level, BlockPos blockPos) {
        return (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CHALK_BRUSH.get()) ? 5 : 1)*super.getDestroyProgress(blockState, player, level, blockPos);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState oldState, boolean pIsMoving) {
        RitualCoreEntity riteCore = (RitualCoreEntity) level.getBlockEntity(blockPos);
        if (riteCore == null) return;
        riteCore.checkForCircles(level,blockPos);
        super.onPlace(blockState, level, blockPos, oldState, pIsMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CIRCLE_TYPE);
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {
        return SHAPE;
    }



    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public BlockState updateShape(BlockState blockState, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return !this.canSurvive(blockState, level, currentPos) ? Blocks.AIR.defaultBlockState() : blockState;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        if (!level.isClientSide()) {
            return !level.getBlockState(blockPos.below()).is(Blocks.AIR);
        }
        return false;
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (player.getItemInHand(interactionHand).is(ModItems.CHALK_BRUSH.get())) {
                level.removeBlock(blockPos,false);
                player.getItemInHand(interactionHand).hurtAndBreak(1,player,(p_150686_) -> p_150686_.broadcastBreakEvent(interactionHand));
            } else {
                RitualCoreEntity riteCore = (RitualCoreEntity) level.getBlockEntity(blockPos);
                if (riteCore == null) return InteractionResult.FAIL;
                else if (riteCore.isExecutingRitual()) {
                    riteCore.stopRitual();
                } else {
                    riteCore.tryStartRitual(level,blockPos,player);
                }

            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RitualCoreEntity(blockPos,blockState);
    }


    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlockEntities.RITUAL_CORE_ENTITY.get(),
                (level1, blockPos, blockState1, pBlockEntity) -> pBlockEntity.tick(level1,blockPos,blockState1));
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}

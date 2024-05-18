package net.sfedu.ars_maleficarum.block.custom.chalkSymbols;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
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
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class RitualCircleCore extends BaseEntityBlock {
    public RitualCircleCore(Properties pProperties) {
        super(pProperties.destroyTime(5));
    }

    //Фильтр того, что тип белый, адский или природный (для blockstate датаген билдера)
    private static Predicate<RitualCoreEntity.ChalkType> coreColor = (type) ->(type.ordinal() <= 2);

    public static final EnumProperty<RitualCoreEntity.ChalkType> CIRCLETYPE = EnumProperty.create("circletype", RitualCoreEntity.ChalkType.class, coreColor);

    public static final VoxelShape SHAPE = Block.box(-8,0,-8,24,1,24);

    @Override
    public float getDestroyProgress(BlockState pState, Player pPlayer, BlockGetter pLevel, BlockPos pPos) {
        return (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CHALK_BRUSH.get()) ? 5 : 1)*super.getDestroyProgress(pState, pPlayer, pLevel, pPos);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        RitualCoreEntity riteCore = (RitualCoreEntity) pLevel.getBlockEntity(pPos);
        riteCore.checkForCircles(pLevel,pPos);
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CIRCLETYPE);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }



    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !this.canSurvive(pState, pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : pState;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (!pLevel.isClientSide()) {
            return !pLevel.getBlockState(pPos.below()).is(Blocks.AIR);
        }
        return false;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.getItemInHand(pHand).is(ModItems.CHALK_BRUSH.get())) {
                pLevel.removeBlock(pPos,false);
                pPlayer.getItemInHand(pHand).hurtAndBreak(1,pPlayer,(p_150686_) -> {
                    p_150686_.broadcastBreakEvent(pHand);
                });
            } else {
                RitualCoreEntity riteCore = (RitualCoreEntity) pLevel.getBlockEntity(pPos);
                if (riteCore.isExecutingRitual()) {
                    riteCore.stopRitual();
                } else try {
                    riteCore.tryStartRitual(pState,pLevel,pPos,pPlayer);
                } catch (Exception e) {
                    System.out.println("something went wrong...");
                }

            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RitualCoreEntity(blockPos,blockState);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.RITUAL_CORE_ENTITY.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1,pPos,pState1));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}

package net.sfedu.ars_maleficarum.block.custom.chalkSymbols;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;

public class ChalkSymbol extends Block {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant",0,4);
    public ChalkSymbol(Properties pProperties) {
        super(pProperties.destroyTime(5));
    }

    @Override
    public float getDestroyProgress(BlockState pState, Player pPlayer, BlockGetter pLevel, BlockPos pPos) {
        return (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CHALK_BRUSH.get()) ? 5 : 1)*super.getDestroyProgress(pState, pPlayer, pLevel, pPos);
    }
    public static final VoxelShape SHAPE = Block.box(3,0,3,13,1.125,13);
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    //При разрушении либо создании глифа найти ближайший центр и заставить его отсканировать целостность кругов, чтобы не делать это каждый тик.
    private void notifyNearestCircleCenter(Level pLevel, BlockPos pPos) {
        for (int i = -6; i<=6; i++) {
            for (int j = -6; j<=6; j++) {
                BlockPos pos = pPos.relative(Direction.Axis.X, i).relative(Direction.Axis.Z,j);
                BlockEntity blockEntity = pLevel.getBlockEntity(pos);
                if (blockEntity instanceof RitualCoreEntity) {
                    ((RitualCoreEntity) blockEntity).checkForCircles(pLevel, pos);
                }
            }
        }
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        notifyNearestCircleCenter(pLevel,pPos);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        notifyNearestCircleCenter(pLevel,pPos);
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(VARIANT);
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
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.SUCCESS;
    }
}

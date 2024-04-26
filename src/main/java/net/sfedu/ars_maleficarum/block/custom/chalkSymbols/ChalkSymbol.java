package net.sfedu.ars_maleficarum.block.custom.chalkSymbols;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.entity.custom.MortalSinDemonEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ChalkSymbol extends HorizontalDirectionalBlock {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant",0,10);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    //0 - not working, 1 - undead, 2 - demons
    public static final IntegerProperty IMPRISONMENT = IntegerProperty.create("imprisonment",0,2);

    private static final VoxelShape BARRIER_SHAPE = Shapes.box(0, -4, 0, 1, 16, 1);


    public static final RitualCoreEntity.CircleType type = RitualCoreEntity.CircleType.ANY;
    public ChalkSymbol(Properties pProperties) {
        super(pProperties.destroyTime(5));
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        return !blockstate.is(Blocks.AIR);
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return canSurvive(pState,pLevel,pCurrentPos) ? pState : Blocks.AIR.defaultBlockState();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING,Direction.getRandom(pContext.getLevel().getRandom())).setValue(IMPRISONMENT,0);
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
    public boolean collisionExtendsVertically(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
        //TODO ADD TYPE CHECK if (state.getValue(IMPRISONMENT))
        return isBlocked(entity, state.getValue(IMPRISONMENT));
    }

    boolean isBlocked(Entity entity,int imprisonType) {
        if (!(entity instanceof LivingEntity)) return false;
        return switch (imprisonType) {
            case 1 -> ((LivingEntity) entity).isInvertedHealAndHarm();
            case 2 -> entity instanceof MortalSinDemonEntity;
            default -> false;
        };

    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return state.getValue(IMPRISONMENT)!=0 && ctx instanceof EntityCollisionContext
                && ((EntityCollisionContext) ctx).getEntity() != null
                && isBlocked(((EntityCollisionContext) ctx).getEntity(),state.getValue(IMPRISONMENT)) ? BARRIER_SHAPE : super.getCollisionShape(state, world, pos, ctx);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(VARIANT);
        pBuilder.add(FACING);
        pBuilder.add(IMPRISONMENT);
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
        pLevel.playSound(null,pPos, ModSounds.BRUSH_USE.get(), SoundSource.PLAYERS);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pState.getValue(IMPRISONMENT)!=0) {
            double d0 = pPos.getCenter().x;
            double d1 = pPos.getCenter().y;
            double d2 = pPos.getCenter().z;
            if (pRandom.nextFloat() < 0.2D) pLevel.addParticle(ParticleTypes.ENCHANT,true, d0, d1, d2, 0.0D, 0.1D, 0.0D);

        }

    }
}

package net.sfedu.ars_maleficarum.block.custom.decorative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

public class Сhandelier extends HorizontalDirectionalBlock {

    protected static final VoxelShape EAST_WEST = Block.box(3,0,5,13,16,11);
    protected static final VoxelShape NORTH_SOUTH = Block.box(5,0,3,11,16,13);
    public Сhandelier(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT,false));
    }

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH, SOUTH -> NORTH_SOUTH;
            case EAST, WEST -> EAST_WEST;
            default -> null;
        };
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
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite().getClockWise());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(LIT);

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide() && !pPlayer.isCreative() && pPlayer.isShiftKeyDown() && pHand==InteractionHand.MAIN_HAND && pPlayer.getItemInHand(pHand).getCount()==0 && !pState.getValue(LIT)) {
            pLevel.playSound(null,pPos, SoundEvents.LANTERN_PLACE, SoundSource.PLAYERS);
            pLevel.removeBlock(pPos, false);
            pPlayer.setItemInHand(pHand, ModBlocks.CHANDELIER.get().asItem().getDefaultInstance());
        }
        else if (!pLevel.isClientSide() && !pPlayer.isCreative() && pPlayer.isShiftKeyDown() && pHand==InteractionHand.MAIN_HAND && pPlayer.getItemInHand(pHand).getCount()==0 && pState.getValue(LIT)) {
            pLevel.playSound(null,pPos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.PLAYERS);
            if (pState.getValue(FACING) == Direction.WEST || pState.getValue(FACING) == Direction.EAST) {
                double d0 = pPos.getCenter().x;
                double d1 = pPos.getCenter().y+0.55D;
                double d2 = pPos.getCenter().z;
                ((ServerLevel)pLevel).sendParticles(ParticleTypes.SMOKE, d0, d1, d2, 5, 0,0.5D,0,0.1F);
                ((ServerLevel)pLevel).sendParticles(ParticleTypes.SMOKE, d0-0.25D, d1-0.05D, d2, 5, 0,0.5D,0,0.1F);
                ((ServerLevel)pLevel).sendParticles(ParticleTypes.SMOKE, d0+0.25D, d1-0.05D, d2, 5, 0,0.5D,0,0.1F);
            } else {
                double d0 = pPos.getCenter().x;
                double d1 = pPos.getCenter().y+0.55D;
                double d2 = pPos.getCenter().z;
                ((ServerLevel)pLevel).sendParticles(ParticleTypes.SMOKE, d0, d1, d2, 5, 0,0.5D,0,0.1F);
                ((ServerLevel)pLevel).sendParticles(ParticleTypes.SMOKE, d0, d1-0.05D, d2-0.25D, 5, 0,0.5D,0,0.1F);
                ((ServerLevel)pLevel).sendParticles(ParticleTypes.SMOKE, d0, d1-0.05D, d2+0.25D, 5, 0,0.5D,0,0.1F);
            }
            pLevel.setBlock(pPos,pState.setValue(LIT,false),3);
        }
        else if (!pLevel.isClientSide() && pPlayer.getItemInHand(pHand).is(Items.FLINT_AND_STEEL) && !pState.getValue(LIT)) {
            pLevel.playSound(null,pPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS);
            pLevel.setBlock(pPos,pState.setValue(LIT,true),3);
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pState.getValue(LIT)) return;
        if (pState.getValue(FACING) == Direction.WEST || pState.getValue(FACING) == Direction.EAST) {
            double d0 = pPos.getCenter().x;
            double d1 = pPos.getCenter().y+0.55D;
            double d2 = pPos.getCenter().z;
            pLevel.addParticle(ParticleTypes.SMOKE,true, d0, d1, d2, 0.0D, 0.00D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, true,d0, d1, d2, 0.0D, 0.00D, 0.0D);

            pLevel.addParticle(ParticleTypes.SMOKE,true, d0-0.25D, d1-0.1D, d2, 0.0D, 0.00D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, true,d0-0.25D, d1-0.1D, d2, 0.0D, 0.00D, 0.0D);

            pLevel.addParticle(ParticleTypes.SMOKE,true, d0+0.25D, d1-0.1D, d2, 0.0D, 0.00D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, true,d0+0.25D, d1-0.1D, d2, 0.0D, 0.00D, 0.0D);
            if (pRandom.nextDouble() < 0.2D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        } else {
            double d0 = pPos.getCenter().x;
            double d1 = pPos.getCenter().y+0.55D;
            double d2 = pPos.getCenter().z;
            pLevel.addParticle(ParticleTypes.SMOKE,true, d0, d1, d2, 0.0D, 0.01D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, true,d0, d1, d2, 0.0D, 0.01D, 0.0D);

            pLevel.addParticle(ParticleTypes.SMOKE,true, d0, d1-0.1D, d2-0.25D, 0.0D, 0.01D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, true,d0, d1-0.1D, d2-0.25D, 0.0D, 0.01D, 0.0D);

            pLevel.addParticle(ParticleTypes.SMOKE,true, d0, d1-0.1D, d2+0.25D, 0.0D, 0.01D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, true,d0, d1-0.1D, d2+0.25D, 0.0D, 0.01D, 0.0D);
            if (pRandom.nextDouble() < 0.2D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }
    }
}

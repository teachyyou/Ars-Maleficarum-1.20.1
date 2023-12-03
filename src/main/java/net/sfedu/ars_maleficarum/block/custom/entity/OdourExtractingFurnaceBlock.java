package net.sfedu.ars_maleficarum.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class OdourExtractingFurnaceBlock extends BaseEntityBlock {

    public OdourExtractingFurnaceBlock(Properties pProperties) {
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
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,12,16);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING,pContext.getHorizontalDirection().getCounterClockWise());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(LIT);
    }

    /*Ниже все что относится к Entity*/

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof OdourExtractingFurnaceBlockEntity) {
                ((OdourExtractingFurnaceBlockEntity) blockEntity).drops();
                ((OdourExtractingFurnaceBlockEntity) blockEntity).resetLitLevel();
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof OdourExtractingFurnaceBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer),(OdourExtractingFurnaceBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Container Provider is Missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType,ModBlockEntities.ODOUR_EXTRACTING_FURNACE_BE.get(),(pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1,pPos,pState1));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OdourExtractingFurnaceBlockEntity(pPos,pState);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {

            if (pState.getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST) {
                double d0 = (double)pPos.getX() + 0.4D;
                double d1 = (double)pPos.getY() + 1D;
                double d2 = (double)pPos.getZ() + 0.5D;
                pLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,true, d0, d1, d2, 0.0D, 0.1D, 0.0D);
                if (pRandom.nextDouble() < 0.1D) {
                    pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }
            else if (pState.getValue(HorizontalDirectionalBlock.FACING)==Direction.WEST){

                double d0 = (double)pPos.getX() + 0.7D;
                double d1 = (double)pPos.getY() + 1D;
                double d2 = (double)pPos.getZ() + 0.5D;
                pLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,true, d0, d1, d2, 0.0D, 0.1D, 0.0D);
                if (pRandom.nextDouble() < 0.1D) {
                    pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }

            }
            else if (pState.getValue(HorizontalDirectionalBlock.FACING)==Direction.NORTH){
                double d0 = (double)pPos.getX() + 0.6D;
                double d1 = (double)pPos.getY() + 1D;
                double d2 = (double)pPos.getZ() + 0.6D;
                pLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,true, d0, d1, d2, 0.0D, 0.1D, 0.0D);
                if (pRandom.nextDouble() < 0.1D) {
                    pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }
            else {
                double d0 = (double)pPos.getX() + 0.6D;
                double d1 = (double)pPos.getY() + 1D;
                double d2 = (double)pPos.getZ() + 0.4D;
                pLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,true, d0, d1, d2, 0.0D, 0.1D, 0.0D);
                if (pRandom.nextDouble() < 0.1D) {
                    pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }
        }
    }
}

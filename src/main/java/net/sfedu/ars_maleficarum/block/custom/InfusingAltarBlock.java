package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.sfedu.ars_maleficarum.block.custom.entity.InfusingAltarBlockEntity;
import net.sfedu.ars_maleficarum.block.custom.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfusingAltarBlock extends BaseEntityBlock {

    public InfusingAltarBlock(Properties pProperties) {
        super(pProperties);
    }
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty COLOR = IntegerProperty.create("color",0,15);

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,11,16);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING,pContext.getHorizontalDirection().getOpposite()).setValue(COLOR,14);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(COLOR);
    }

    /*Ниже все что относится к Entity*/

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity= pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof InfusingAltarBlockEntity) {
                ((InfusingAltarBlockEntity) blockEntity).drops();
            }

        }
        
        super.onRemove(pState,pLevel,pPos,pNewState,pIsMoving);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return super.getDrops(pState, pParams);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof InfusingAltarBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer),(InfusingAltarBlockEntity)entity,pPos);
            } else {
                throw new IllegalStateException("Out Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new InfusingAltarBlockEntity(pPos,pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType,ModBlockEntities.INFUSING_ALTAR_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1,pPos,pState1));
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST) {
            double d0 = (double)pPos.getX() + 0.8D;
            double d1 = (double)pPos.getY() + 1D;
            double d2 = (double)pPos.getZ() + 0.85D;
            pLevel.addParticle(ParticleTypes.SMOKE,true, d0, d1, d2, 0.0D, 0.025D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, true,d0, d1, d2, 0.0D, 0.025D, 0.0D);
        }
        else if (pState.getValue(HorizontalDirectionalBlock.FACING)==Direction.WEST){
            double d0 = (double)pPos.getX() + 0.2D;
            double d1 = (double)pPos.getY() + 1D;
            double d2 = (double)pPos.getZ() + 0.15D;
            pLevel.addParticle(ParticleTypes.SMOKE,true, d0, d1, d2, 0.0D, 0.025D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, true,d0, d1, d2, 0.0D, 0.025D, 0.0D);

        }
        else if (pState.getValue(HorizontalDirectionalBlock.FACING)==Direction.NORTH){
            double d0 = (double)pPos.getX() + 0.85D;
            double d1 = (double)pPos.getY() + 1D;
            double d2 = (double)pPos.getZ() + 0.15D;
            pLevel.addParticle(ParticleTypes.SMOKE,true, d0, d1, d2, 0.0D, 0.025D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, true,d0, d1, d2, 0.0D, 0.025D, 0.0D);
        }
        else {

            double d0= (double)pPos.getX() + 0.2D;
            double d1 = (double)pPos.getY() + 1D;
            double d2 = (double)pPos.getZ() + 0.85D;
            pLevel.addParticle(ParticleTypes.SMOKE,true, d0, d1, d2, 0.0D, 0.025D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, true,d0, d1, d2, 0.0D, 0.025D, 0.0D);
        }
    }
}
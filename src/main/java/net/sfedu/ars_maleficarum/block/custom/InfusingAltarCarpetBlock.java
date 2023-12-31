package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfusingAltarCarpetBlock extends Block {
    public InfusingAltarCarpetBlock(Properties pProperties) {
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
        return this.defaultBlockState().setValue(FACING,pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(COLOR);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.getItemInHand(pHand).getItem()== Items.BLACK_DYE && (pState.getValue(InfusingAltarCarpetBlock.COLOR)==0 || pState.getValue(InfusingAltarCarpetBlock.COLOR)==8)) {
                pLevel.setBlock(pPos, ModBlocks.INFUSING_ALTAR_PENTA_BLOCK.get().defaultBlockState().setValue(FACING,pState.getValue(FACING)).setValue(InfusingAltarBlock.COLOR,pState.getValue(InfusingAltarCarpetBlock.COLOR)), 2);
                if (!pPlayer.isCreative()) pPlayer.getItemInHand(pHand).shrink(1);
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
            else if (pPlayer.getItemInHand(pHand).getItem()== Items.WHITE_DYE && pState.getValue(InfusingAltarCarpetBlock.COLOR)!=0 && pState.getValue(InfusingAltarCarpetBlock.COLOR)!=8) {
                pLevel.setBlock(pPos, ModBlocks.INFUSING_ALTAR_PENTA_BLOCK.get().defaultBlockState().setValue(FACING,pState.getValue(FACING)).setValue(InfusingAltarBlock.COLOR,pState.getValue(InfusingAltarCarpetBlock.COLOR)), 2);
                if (!pPlayer.isCreative()) pPlayer.getItemInHand(pHand).shrink(1);
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }

        }
        return InteractionResult.PASS;
    }
}

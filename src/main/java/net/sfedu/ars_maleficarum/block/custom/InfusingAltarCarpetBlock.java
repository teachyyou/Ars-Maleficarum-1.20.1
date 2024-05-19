package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

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
        boolean used = false;
        if (!pLevel.isClientSide()) {
            if (pPlayer.getItemInHand(pHand).getItem()== ModItems.BLACK_CHALK.get() && (pState.getValue(InfusingAltarCarpetBlock.COLOR)==0 || pState.getValue(InfusingAltarCarpetBlock.COLOR)==8)) {
                pLevel.setBlock(pPos, ModBlocks.INFUSING_ALTAR_PENTA_BLOCK.get().defaultBlockState().setValue(FACING,pState.getValue(FACING)).setValue(InfusingAltarBlock.COLOR,pState.getValue(InfusingAltarCarpetBlock.COLOR)), 2);
                if (!pPlayer.isCreative()) {
                    if (pPlayer.getItemInHand(pHand).getCount()>1) {
                        int dif = pPlayer.getItemInHand(pHand).getCount()-1;
                        ItemStack remaining = pPlayer.getItemInHand(pHand).copy();
                        remaining.setCount(dif);
                        pPlayer.getItemInHand(pHand).setCount(1);
                        pPlayer.getItemInHand(pHand).hurtAndBreak(1,pPlayer,
                                p->{});
                        if (!pPlayer.getInventory().add(remaining)) {
                            Vec3 vec3 = pPlayer.position();
                            pLevel.addFreshEntity(new ItemEntity(pLevel, vec3.x,vec3.y,vec3.z, remaining));
                        }
                    } else {
                        pPlayer.getItemInHand(pHand).hurtAndBreak(1,pPlayer,
                                p->{});
                    }

                    used = true;
                }


            }
            else if (pPlayer.getItemInHand(pHand).getItem()== ModItems.WHITE_CHALK.get() && pState.getValue(InfusingAltarCarpetBlock.COLOR)!=0 && pState.getValue(InfusingAltarCarpetBlock.COLOR)!=8) {
                pLevel.setBlock(pPos, ModBlocks.INFUSING_ALTAR_PENTA_BLOCK.get().defaultBlockState().setValue(FACING,pState.getValue(FACING)).setValue(InfusingAltarBlock.COLOR,pState.getValue(InfusingAltarCarpetBlock.COLOR)), 2);
                if (!pPlayer.isCreative()) {
                    if (pPlayer.getItemInHand(pHand).getCount()>1) {
                        int dif = pPlayer.getItemInHand(pHand).getCount()-1;
                        ItemStack remaining = pPlayer.getItemInHand(pHand).copy();
                        remaining.setCount(dif);
                        pPlayer.getItemInHand(pHand).setCount(1);
                        pPlayer.getItemInHand(pHand).hurtAndBreak(1,pPlayer,
                                p->{});
                        if (!pPlayer.getInventory().add(remaining)) {
                            Vec3 vec3 = pPlayer.position();
                            pLevel.addFreshEntity(new ItemEntity(pLevel, vec3.x,vec3.y,vec3.z, remaining));
                        }
                    } else {
                        pPlayer.getItemInHand(pHand).hurtAndBreak(1,pPlayer,
                                p->{});
                    }

                    used = true;
                }

            }

        }
        if (used) pLevel.playSound(null,pPos, ModSounds.CHALK_USE.get(), SoundSource.PLAYERS);
        return InteractionResult.sidedSuccess(!pLevel.isClientSide);

    }
}

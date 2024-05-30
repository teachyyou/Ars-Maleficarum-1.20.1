package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.sfedu.ars_maleficarum.block.custom.entity.InfusingAltarBlockEntity;
import net.sfedu.ars_maleficarum.block.custom.entity.ModBlockEntities;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.item.custom.ritualCircleItems.ChalkItem;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfusingAltarBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty COLOR = IntegerProperty.create("color",0,15);
    //0 - stone basement, 1 - basement with a carpet on, 2 - with drawn pentagram, 3 - with candles put on
    public static final IntegerProperty STAGE = IntegerProperty.create("stage",0,3);


    public static final VoxelShape SHAPE = Block.box(0,0,0,16,11,16);

    private static final List<Item> colorCarpets = List.of(Blocks.WHITE_CARPET.asItem(),Blocks.ORANGE_CARPET.asItem(),Blocks.MAGENTA_CARPET.asItem(),Blocks.LIGHT_BLUE_CARPET.asItem(),Blocks.YELLOW_CARPET.asItem(),
            Blocks.LIME_CARPET.asItem(),Blocks.PINK_CARPET.asItem(),Blocks.GRAY_CARPET.asItem(),Blocks.LIGHT_GRAY_CARPET.asItem(),Blocks.CYAN_CARPET.asItem(),Blocks.PURPLE_CARPET.asItem(),
            Blocks.BLUE_CARPET.asItem(),Blocks.BROWN_CARPET.asItem(),Blocks.GREEN_CARPET.asItem(),Blocks.RED_CARPET.asItem(),Blocks.BLACK_CARPET.asItem());


    public InfusingAltarBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STAGE);
        pBuilder.add(FACING);
        pBuilder.add(COLOR);
    }

    @NotNull
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }
    @NotNull
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,context.getHorizontalDirection().getOpposite()).setValue(COLOR,14).setValue(STAGE,0);
    }


    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newBlockState, boolean isMoving) {
        if (blockState.getValue(STAGE)<3) {
            BlockEntity blockEntity= level.getBlockEntity(blockPos);
            if (blockEntity instanceof InfusingAltarBlockEntity) {
                ((InfusingAltarBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(blockState,level,blockPos,newBlockState,isMoving);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            switch (blockState.getValue(STAGE)) {
                case 0 -> {
                    return putCarpetOn(blockState,level,blockPos,player,interactionHand,blockHitResult);
                }
                case 1 -> {
                    return drawPentagram(blockState,level,blockPos,player,interactionHand,blockHitResult);
                }
                case 2 -> {
                    return putCandles(blockState,level,blockPos,player,interactionHand,blockHitResult);
                }
                case 3-> {
                    BlockEntity entity = level.getBlockEntity(blockPos);
                    if (entity instanceof InfusingAltarBlockEntity) {
                        NetworkHooks.openScreen(((ServerPlayer) player),(InfusingAltarBlockEntity)entity,blockPos);
                    } else {
                        throw new IllegalStateException("Out Container provider is missing!");
                    }
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private InteractionResult putCarpetOn(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        for (int i = 0; i < 16; i++) {
            if (player.getItemInHand(interactionHand).getItem()==colorCarpets.get(i)) {
                level.setBlock(blockPos, blockState.setValue(FACING,blockState.getValue(FACING)).setValue(InfusingAltarBlock.COLOR,i).setValue(STAGE,1), 2);
                if (!player.isCreative()) player.getItemInHand(interactionHand).shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    private InteractionResult drawPentagram(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        boolean used = false;
        ItemStack itemStack = player.getItemInHand(interactionHand);
        Item item = itemStack.getItem();
        int color = blockState.getValue(InfusingAltarBlock.COLOR);
        Direction facing = blockState.getValue(FACING);

        if (item == ModItems.BLACK_CHALK.get() && (color == 0 || color == 8)) {
            level.setBlock(blockPos, blockState.setValue(FACING, facing).setValue(COLOR, color).setValue(STAGE,2), 2);
            ChalkItem.chalkUse(player, itemStack, level, blockPos);
            used = true;
        }
        else if (item == ModItems.WHITE_CHALK.get() && color != 0 && color != 8) {
            level.setBlock(blockPos, blockState.setValue(FACING, facing).setValue(COLOR, color).setValue(STAGE,2), 2);
            ChalkItem.chalkUse(player, itemStack, level, blockPos);
            used = true;
        }

        if (used) {
            level.playSound(null, blockPos, ModSounds.CHALK_USE.get(), SoundSource.PLAYERS);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL   ;
    }

    private InteractionResult putCandles(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player.getItemInHand(interactionHand).is(ItemTags.CANDLES)) {
            level.setBlock(blockPos, blockState.setValue(FACING,blockState.getValue(FACING)).setValue(InfusingAltarBlock.COLOR,blockState.getValue(InfusingAltarBlock.COLOR)).setValue(STAGE,3), 2);
            if (!player.isCreative()) player.getItemInHand(interactionHand).shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(STAGE) < 3) return null;
        return new InfusingAltarBlockEntity(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (blockState.getValue(STAGE) < 3 || level.isClientSide()) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlockEntities.INFUSING_ALTAR_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1,pPos,pState1));
    }

    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(STAGE) < 3) return;
        if (blockState.getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST) {
            double d0 = (double)blockPos.getX() + 0.8D;
            double d1 = (double)blockPos.getY() + 1D;
            double d2 = (double)blockPos.getZ() + 0.85D;
            level.addParticle(ParticleTypes.SMOKE,true, d0, d1, d2, 0.0D, 0.025D, 0.0D);
            level.addParticle(ParticleTypes.SMALL_FLAME, true,d0, d1, d2, 0.0D, 0.025D, 0.0D);
        }
        else if (blockState.getValue(HorizontalDirectionalBlock.FACING)==Direction.WEST){
            double d0 = (double)blockPos.getX() + 0.2D;
            double d1 = (double)blockPos.getY() + 1D;
            double d2 = (double)blockPos.getZ() + 0.15D;
            level.addParticle(ParticleTypes.SMOKE,true, d0, d1, d2, 0.0D, 0.025D, 0.0D);
            level.addParticle(ParticleTypes.SMALL_FLAME, true,d0, d1, d2, 0.0D, 0.025D, 0.0D);

        }
        else if (blockState.getValue(HorizontalDirectionalBlock.FACING)==Direction.NORTH){
            double d0 = (double)blockPos.getX() + 0.85D;
            double d1 = (double)blockPos.getY() + 1D;
            double d2 = (double)blockPos.getZ() + 0.15D;
            level.addParticle(ParticleTypes.SMOKE,true, d0, d1, d2, 0.0D, 0.025D, 0.0D);
            level.addParticle(ParticleTypes.SMALL_FLAME, true,d0, d1, d2, 0.0D, 0.025D, 0.0D);
        }
        else {
            double d0= (double)blockPos.getX() + 0.2D;
            double d1 = (double)blockPos.getY() + 1D;
            double d2 = (double)blockPos.getZ() + 0.85D;
            level.addParticle(ParticleTypes.SMOKE,true, d0, d1, d2, 0.0D, 0.025D, 0.0D);
            level.addParticle(ParticleTypes.SMALL_FLAME, true,d0, d1, d2, 0.0D, 0.025D, 0.0D);
        }
    }

}

package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sfedu.ars_maleficarum.block.custom.entity.BrewingCauldronBlockEntity;
import net.sfedu.ars_maleficarum.block.custom.entity.ModBlockEntities;
import net.sfedu.ars_maleficarum.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BrewingCauldronBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final BooleanProperty BOILING = BooleanProperty.create("boiling");
    public static final IntegerProperty FUEL = IntegerProperty.create("fuel", 0, 3);
    public static final IntegerProperty WATER = IntegerProperty.create("water", 0, 3);
    public BrewingCauldronBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(LIT, false)
                .setValue(BOILING, false)
                .setValue(FUEL, 0)
                .setValue(WATER, 0));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    public static VoxelShape SHAPE = null;

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (SHAPE == null) {
            SHAPE = Stream.of(
                    Block.box(1, 0, 1, 15, 4, 15),
                    Block.box(14, 0, 1, 15, 11, 15),
                    Block.box(1, 0, 1, 2, 11, 15),
                    Block.box(1, 0, 1, 15, 11, 2),
                    Block.box(1, 0, 14, 15, 11, 15)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        }
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(LIT);
        pBuilder.add(BOILING);
        pBuilder.add(FUEL);
        pBuilder.add(WATER);
    }


    //region BlockEntity

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    // Временно дропает содержимое - в финальной версии не будет.
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity= pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof BrewingCauldronBlockEntity) {
                ((BrewingCauldronBlockEntity) blockEntity).drops();
            }

        }

        super.onRemove(pState,pLevel,pPos,pNewState,pIsMoving);
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BrewingCauldronBlockEntity blockentity = (BrewingCauldronBlockEntity) pLevel.getBlockEntity(pPos);
        if (!pLevel.isClientSide) {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
                pLevel.playSound(null, pPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS);
                pLevel.setBlock(pPos, pState.setValue(LIT, true), 3);
            }
            else if (itemstack.isEmpty() && pPlayer.isShiftKeyDown())
            {
                pLevel.playSound(null, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);
                pLevel.setBlock(pPos, pState.setValue(LIT, false), 3);
            }
            else if (itemstack.is(ItemTags.LOGS_THAT_BURN))
            {
                if (blockentity != null)
                {
                    if (blockentity.addFuel(pState, pLevel, pPos)) itemstack.setCount(itemstack.getCount()-1);
                }
            }
            else if (itemstack.getItem() == Items.BUCKET)
            {
                if (blockentity != null)
                {
                    if (pState.getValue(WATER) == 3)
                    {
                        pLevel.playSound(null, pPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS);
                        pLevel.setBlock(pPos, pState.setValue(WATER, 0), 3);
                        pPlayer.setItemInHand(pHand, new ItemStack(Items.WATER_BUCKET));

                        if ((pLevel.getBlockEntity(pPos)) != null)
                            ((BrewingCauldronBlockEntity) pLevel.getBlockEntity(pPos)).clearInventory();
                    }
                }
            }
            else if (itemstack.getItem() == Items.WATER_BUCKET)
            {
                if (blockentity != null)
                {
                    if (pState.getValue(WATER) == 0)
                    {
                        pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS);
                        pLevel.setBlock(pPos, pState.setValue(WATER, 3), 3);
                        pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
                    }
                }
            }
            else if (itemstack.getItem() == ModItems.EMPTY_VIAL.get())
            {
                if (blockentity != null)
                {
                    if (blockentity.crafted != null && blockentity.craftedType == 1)
                    {
                        if (pState.getValue(WATER) == 1)
                        {
                            if ((pLevel.getBlockEntity(pPos)) != null)
                                ((BrewingCauldronBlockEntity) pLevel.getBlockEntity(pPos)).clearInventory();
                        }
                        pLevel.playSound(null, pPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS);
                        pLevel.setBlock(pPos, pState.setValue(WATER, pState.getValue(WATER)-1), 3);
                        pPlayer.getItemInHand(pHand).setCount(pPlayer.getItemInHand(pHand).getCount()-1);
                        if (pState.getValue(BOILING))
                            pPlayer.addItem(blockentity.crafted.copy());
                        else
                            pPlayer.addItem(new ItemStack(Items.DIRT));
                    }

                }
            }
            else if (itemstack.getItem() == Items.BOWL)
            {
                if (blockentity != null)
                {
                    if (blockentity.crafted != null && blockentity.craftedType == 2)
                    {
                        if (pState.getValue(WATER) == 1)
                        {
                            if ((pLevel.getBlockEntity(pPos)) != null)
                                ((BrewingCauldronBlockEntity) pLevel.getBlockEntity(pPos)).clearInventory();
                        }
                        pLevel.playSound(null, pPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS);
                        pLevel.setBlock(pPos, pState.setValue(WATER, pState.getValue(WATER)-1), 3);
                        pPlayer.getItemInHand(pHand).setCount(pPlayer.getItemInHand(pHand).getCount()-1);
                        if (pState.getValue(BOILING))
                            pPlayer.addItem(blockentity.crafted.copy());
                        else
                            pPlayer.addItem(new ItemStack(Items.DIRT));
                    }

                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide())
        {
            return createTickerHelper(pBlockEntityType, ModBlockEntities.BREWING_CAULDRON_BE.get(),
                    (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.clientTick(pLevel1, pPos, pState1));
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.BREWING_CAULDRON_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BrewingCauldronBlockEntity(pPos, pState);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        double dx = pPos.getX();
        double dy = pPos.getY();
        double dz = pPos.getZ();
        if (pState.getValue(LIT)) {
            pLevel.addParticle(ParticleTypes.FLAME, true, dx+0.5D+(pRandom.nextDouble()/3D-0.166D), dy + (pRandom.nextDouble()-0.5D)/10D+0.11D,dz+0.5D+(pRandom.nextDouble()/3D-0.166D), (pRandom.nextDouble()-0.5D)/35D, 0D, (pRandom.nextDouble()-0.5D)/35D);
            pLevel.addParticle(ParticleTypes.FLAME, true, dx+0.5D+(pRandom.nextDouble()/3D-0.166D), dy + (pRandom.nextDouble()-0.5D)/10D+0.11D,dz+0.5D+(pRandom.nextDouble()/3D-0.166D), (pRandom.nextDouble()-0.5D)/35D, 0D, (pRandom.nextDouble()-0.5D)/35D);
            pLevel.addParticle(ParticleTypes.SMALL_FLAME, true, dx+0.5D+(pRandom.nextDouble()/3D-0.166D), dy + (pRandom.nextDouble()-0.5D)/10D+0.11D,dz+0.5D+(pRandom.nextDouble()/3D-0.166D), (pRandom.nextDouble()-0.5D)/35D, 0D, (pRandom.nextDouble()-0.5D)/35D);
            if (pRandom.nextDouble() < 0.2F) {
                pLevel.playLocalSound(pPos, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }

    }

    //endregion BlockEntity
}

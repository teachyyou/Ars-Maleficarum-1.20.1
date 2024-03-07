package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.material.MaterialRuleList;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import net.sfedu.ars_maleficarum.block.custom.entity.BrewingCauldronBlockEntity;
import net.sfedu.ars_maleficarum.block.custom.entity.InfusingAltarBlockEntity;
import net.sfedu.ars_maleficarum.block.custom.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
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
    public BrewingCauldronBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(LIT, false)
                .setValue(BOILING, false)
                .setValue(FUEL, 0));
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

        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide())
        {
            return null;
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
        if (pState.getValue(LIT)) {
            double dx = pPos.getX();
            double dy = pPos.getY();
            double dz = pPos.getZ();
            pLevel.addParticle(ParticleTypes.FLAME, true, dx+0.5D+(pRandom.nextDouble()/3D-0.166D), dy + (pRandom.nextDouble()-0.5D)/10D+0.11D,dz+0.5D+(pRandom.nextDouble()/3D-0.166D), (pRandom.nextDouble()-0.5D)/35D, 0D, (pRandom.nextDouble()-0.5D)/35D);
            pLevel.addParticle(ParticleTypes.FLAME, true, dx+0.5D+(pRandom.nextDouble()/3D-0.166D), dy + (pRandom.nextDouble()-0.5D)/10D+0.11D,dz+0.5D+(pRandom.nextDouble()/3D-0.166D), (pRandom.nextDouble()-0.5D)/35D, 0D, (pRandom.nextDouble()-0.5D)/35D);
            pLevel.addParticle(ParticleTypes.SMALL_FLAME, true, dx+0.5D+(pRandom.nextDouble()/3D-0.166D), dy + (pRandom.nextDouble()-0.5D)/10D+0.11D,dz+0.5D+(pRandom.nextDouble()/3D-0.166D), (pRandom.nextDouble()-0.5D)/35D, 0D, (pRandom.nextDouble()-0.5D)/35D);
            if (pRandom.nextDouble() < 0.1F) {
                pLevel.playLocalSound(pPos, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }
        if (pState.getValue(BOILING))
        {
            if (pRandom.nextDouble() < 0.1F) {
                pLevel.playLocalSound(pPos, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }
    }

    //endregion BlockEntity
}

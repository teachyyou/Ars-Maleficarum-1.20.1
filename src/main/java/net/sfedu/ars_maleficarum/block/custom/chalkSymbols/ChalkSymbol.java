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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
public class ChalkSymbol extends HorizontalDirectionalBlock {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant",0,10);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    //0 - not working, 1 - undead, 2 - demons
    public static final IntegerProperty IMPRISONMENT = IntegerProperty.create("imprisonment",0,2);

    private static final VoxelShape BARRIER_SHAPE = Shapes.box(0, -4, 0, 1, 16, 1);


    public ChalkSymbol() {
        super(BlockBehaviour.Properties.copy(Blocks.REDSTONE_WIRE).noOcclusion().noCollission().noLootTable().destroyTime(5));
    }

    @Override
    public boolean canSurvive(@NotNull BlockState blockState, LevelReader level, BlockPos blockPos) {
        BlockState blockstate = level.getBlockState(blockPos.below());
        return !blockstate.is(Blocks.AIR);
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        return canSurvive(blockState,level,currentPos) ? blockState : Blocks.AIR.defaultBlockState();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,Direction.getRandom(context.getLevel().getRandom())).setValue(IMPRISONMENT,0);
    }

    @Override
    @ParametersAreNonnullByDefault
    public float getDestroyProgress(BlockState blockState, Player player, BlockGetter level, BlockPos blockPos) {
        return (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CHALK_BRUSH.get()) ? 5 : 1)*super.getDestroyProgress(blockState, player, level, blockPos);
    }
    public static final VoxelShape SHAPE = Block.box(3,0,3,13,1.125,13);
    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {
        return SHAPE;
    }

    //При разрушении либо создании глифа найти ближайший центр и заставить его отсканировать целостность кругов, чтобы не делать это каждый тик.
    private void notifyNearestCircleCenter(Level level, BlockPos blockPos) {
        for (int i = -6; i<=6; i++) {
            for (int j = -6; j<=6; j++) {
                BlockPos pos = blockPos.relative(Direction.Axis.X, i).relative(Direction.Axis.Z,j);
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof RitualCoreEntity) {
                    ((RitualCoreEntity) blockEntity).checkForCircles(level, pos);
                }
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState pOldState, boolean pIsMoving) {
        notifyNearestCircleCenter(level,blockPos);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState pNewState, boolean pIsMoving) {
        notifyNearestCircleCenter(level,blockPos);
        super.onRemove(blockState, level, blockPos, pNewState, pIsMoving);
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
    @NotNull
    @ParametersAreNonnullByDefault
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        if (!level.isClientSide()) {
            if (player.getItemInHand(pHand).is(ModItems.CHALK_BRUSH.get())) {
                level.removeBlock(blockPos,false);
                player.getItemInHand(pHand).hurtAndBreak(1,player,(p_150686_) -> p_150686_.broadcastBreakEvent(pHand));
            } else {
                return InteractionResult.FAIL;
            }
        }
        level.playSound(null,blockPos, ModSounds.BRUSH_USE.get(), SoundSource.PLAYERS);
        return InteractionResult.SUCCESS;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
        super.animateTick(blockState, level, blockPos, random);
        if (blockState.getValue(IMPRISONMENT)!=0) {
            double d0 = blockPos.getCenter().x;
            double d1 = blockPos.getCenter().y;
            double d2 = blockPos.getCenter().z;
            if (random.nextFloat() < 0.2D) level.addParticle(ParticleTypes.ENCHANT,true, d0, d1, d2, 0.0D, 0.1D, 0.0D);

        }

    }
}

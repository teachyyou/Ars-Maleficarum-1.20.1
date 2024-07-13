package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
public class SwampRotfiendMushroom extends HorizontalDirectionalBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    protected static final VoxelShape[] EAST_AABB = new VoxelShape[]{Block.box(13.0D, 0.0D, 8.0D, 16.0D, 5.0D, 9.0D), Block.box(11.0D, 0.0D, 6.0D, 16.0D, 8.0D, 11.0D), Block.box(11.0D, 0.0D, 6.0D, 16.0D, 8.0D, 11.0D),Block.box(9.0D, 0.0D, 5.0D, 16.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] WEST_AABB = new VoxelShape[]{Block.box(0.0D, 0.0D, 7.0D, 3.0D, 5.0D, 8.0D), Block.box(0.0D, 0.0D, 5.0D, 5.0D, 8.0D, 10.0D), Block.box(0.0D, 0.0D, 5.0D, 5.0D, 8.0D, 10.0D),Block.box(0.0D, 0.0D, 4.0D, 7.0D, 12.0D, 11.0D)};
    protected static final VoxelShape[] NORTH_AABB = new VoxelShape[]{Block.box(8.0D, 0.0D, 0.0D, 9.0D, 5.0D, 3.0D), Block.box(6.0D, 0.0D, 0.0D, 11.0D, 8.0D, 5.0D), Block.box(6.0D, 0.0D, 0.0D, 11.0D, 8.0D, 5.0D),Block.box(5.0D, 0.0D, 0.0D, 12.0D, 12.0D, 7.0D)};
    protected static final VoxelShape[] SOUTH_AABB = new VoxelShape[]{Block.box(7.0D, 0.0D, 13.0D, 8.0D, 5.0D, 16.0D), Block.box(5.0D, 0.0D, 11.0D, 10.0D, 8.0D, 16.0D), Block.box(5.0D, 0.0D, 11.0D, 10.0D, 8.0D, 16.0D),Block.box(4.0D, 0.0D, 9.0D, 11.0D, 12.0D, 16.0D)};

    public SwampRotfiendMushroom(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0));
    }
    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(AGE) < 3;
    }
    @Override
    @ParametersAreNonnullByDefault
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < 3;
    }
    @Override
    @ParametersAreNonnullByDefault
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int i = pState.getValue(AGE);
        if (i < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pLevel.random.nextInt(5) == 0)) {
            pLevel.setBlock(pPos, pState.setValue(AGE, i + 1), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
        }
    }
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.relative(pState.getValue(FACING)));
        return blockstate.is(ModBlocks.DEAD_TREE_LOG.get());
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        System.out.println(pLevel.isClientSide());
        if(!pLevel.isClientSide()  && pHand == InteractionHand.MAIN_HAND && pState.getValue(AGE) == 3 && pPlayer.getItemInHand(pHand).is(ModItems.FLINT_KNIFE.get()))
        {
            pLevel.playSeededSound(null, pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(), ModSounds.MUSHROOM_CUT.get(),
                    SoundSource.BLOCKS,1f,1f,0);
            pLevel.setBlock(pPos,pState.setValue(AGE,0),2);
            pLevel.addFreshEntity(new ItemEntity(pLevel,pPos.getX(),pPos.getY(),pPos.getZ(), new ItemStack(
                    ModItems.SWAMP_ROTFIEND_INGREDIENT.get())));
            pPlayer.getItemInHand(pHand).hurtAndBreak(1,pPlayer,(p_150686_) ->
                    p_150686_.broadcastBreakEvent(pHand));
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int i = pState.getValue(AGE);
        return switch (pState.getValue(FACING)) {
            case WEST -> WEST_AABB[i];
            case EAST -> EAST_AABB[i];
            case SOUTH -> SOUTH_AABB[i];
            default -> NORTH_AABB[i];

        };
    }
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = this.defaultBlockState();
        LevelReader levelreader = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();

        for(Direction direction : pContext.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                blockstate = blockstate.setValue(FACING, direction);
                if (blockstate.canSurvive(levelreader, blockpos)) {
                    return blockstate;
                }
            }
        }

        return null;
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == pState.getValue(FACING) && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
    @Override
    @ParametersAreNonnullByDefault
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, AGE);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

}

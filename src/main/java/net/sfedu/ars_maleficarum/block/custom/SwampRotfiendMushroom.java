package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

import java.io.PrintStream;

public class SwampRotfiendMushroom extends HorizontalDirectionalBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    protected static final VoxelShape[] EAST_AABB = new VoxelShape[]{Block.box(13.0D, 0.0D, 8.0D, 16.0D, 5.0D, 9.0D), Block.box(11.0D, 0.0D, 6.0D, 16.0D, 8.0D, 11.0D), Block.box(11.0D, 0.0D, 6.0D, 16.0D, 8.0D, 11.0D),Block.box(9.0D, 0.0D, 5.0D, 16.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] WEST_AABB = new VoxelShape[]{Block.box(0.0D, 0.0D, 7.0D, 3.0D, 5.0D, 8.0D), Block.box(0.0D, 0.0D, 5.0D, 5.0D, 8.0D, 10.0D), Block.box(0.0D, 0.0D, 5.0D, 5.0D, 8.0D, 10.0D),Block.box(0.0D, 0.0D, 4.0D, 7.0D, 12.0D, 11.0D)};
    protected static final VoxelShape[] NORTH_AABB = new VoxelShape[]{Block.box(8.0D, 0.0D, 0.0D, 9.0D, 5.0D, 3.0D), Block.box(6.0D, 0.0D, 0.0D, 11.0D, 8.0D, 5.0D), Block.box(6.0D, 0.0D, 0.0D, 11.0D, 8.0D, 5.0D),Block.box(5.0D, 0.0D, 0.0D, 12.0D, 12.0D, 7.0D)};
    protected static final VoxelShape[] SOUTH_AABB = new VoxelShape[]{Block.box(7.0D, 0.0D, 13.0D, 8.0D, 5.0D, 16.0D), Block.box(5.0D, 0.0D, 11.0D, 10.0D, 8.0D, 16.0D), Block.box(5.0D, 0.0D, 11.0D, 10.0D, 8.0D, 16.0D),Block.box(4.0D, 0.0D, 9.0D, 11.0D, 12.0D, 16.0D)};

    public SwampRotfiendMushroom(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, Integer.valueOf(0)));
    }
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(AGE) < 3;
    }
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < 3;
    }
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (true) {
            int i = pState.getValue(AGE);
            if (i < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pLevel.random.nextInt(5) == 0)) {
                pLevel.setBlock(pPos, pState.setValue(AGE, i + 1), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
            }
        }
    }
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.relative(pState.getValue(FACING)));
        return blockstate.is(ModBlocks.DEAD_TREE_LOG.get());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        System.out.println(pLevel.isClientSide());
        if(!pLevel.isClientSide()  && pHand == InteractionHand.MAIN_HAND && pState.getValue(AGE) == 3 && pPlayer.getItemInHand(pHand).is(ModItems.FLINT_KNIFE.get()))
        {
            pLevel.setBlock(pPos,pState.setValue(AGE,0),2);
            pLevel.addFreshEntity(new ItemEntity(pLevel,pPos.getX(),pPos.getY(),pPos.getZ(), new ItemStack(
                    ModItems.SWAMP_ROTFIEND_INGREDIENT.get())));
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int i = pState.getValue(AGE);
        switch ((Direction)pState.getValue(FACING)) {
            case SOUTH:
                return SOUTH_AABB[i];
            case NORTH:
            default:
                return NORTH_AABB[i];
            case WEST:
                return WEST_AABB[i];
            case EAST:
                return EAST_AABB[i];
        }
    }
    public IntegerProperty getAgeProperty()
    {
        return AGE;
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
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == pState.getValue(FACING) && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, AGE);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

}

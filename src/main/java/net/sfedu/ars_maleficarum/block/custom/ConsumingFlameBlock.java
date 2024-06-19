package net.sfedu.ars_maleficarum.block.custom;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.entity.CustomFireEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class ConsumingFlameBlock extends BaseFireBlock implements EntityBlock{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty UP = PipeBlock.UP;

    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((p_53467_) ->
            p_53467_.getKey() != Direction.DOWN).collect(Util.toMap());
    private static final VoxelShape UP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    private final Map<BlockState, VoxelShape> shapesCache;
    private final Object2IntMap<Block> igniteOdds = new Object2IntOpenHashMap<>();
    private final Object2IntMap<Block> burnOdds = new Object2IntOpenHashMap<>();
    public ConsumingFlameBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties, 6.0F);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(UP, Boolean.FALSE));
        this.shapesCache = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().filter((p_53497_) ->
                p_53497_.getValue(AGE) == 0).collect(Collectors.toMap(Function.identity(), ConsumingFlameBlock::calculateShape)));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide && this.canSurvive(pState,pLevel,pPos.below())) {
            CustomFireEntity fire_entity = (CustomFireEntity) pLevel.getBlockEntity(pPos);
            if (pPlacer!=null && fire_entity!=null) fire_entity.setOwner(pPlacer);
        }
    }
    @Override
    @ParametersAreNonnullByDefault
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {

        if (!pLevel.isClientSide) {
            CustomFireEntity fire_entity = (CustomFireEntity) pLevel.getBlockEntity(pPos);
            if(fire_entity!=null && pEntity.getUUID().equals(fire_entity.getOwnerUUID()))
                return;
        }
        super.entityInside(pState,pLevel,pPos,pEntity);
    }

    private static VoxelShape calculateShape(BlockState p_53491_) {
        VoxelShape voxelshape = Shapes.empty();
        if (p_53491_.getValue(UP)) {
            voxelshape = UP_AABB;
        }

        if (p_53491_.getValue(NORTH)) {
            voxelshape = Shapes.or(voxelshape, NORTH_AABB);
        }

        if (p_53491_.getValue(SOUTH)) {
            voxelshape = Shapes.or(voxelshape, SOUTH_AABB);
        }

        if (p_53491_.getValue(EAST)) {
            voxelshape = Shapes.or(voxelshape, EAST_AABB);
        }

        if (p_53491_.getValue(WEST)) {
            voxelshape = Shapes.or(voxelshape, WEST_AABB);
        }

        return voxelshape.isEmpty() ? DOWN_AABB : voxelshape;
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return this.canSurvive(pState, pLevel, pCurrentPos) ? this.getStateWithAge(pLevel, pCurrentPos, pState.getValue(AGE)) : Blocks.AIR.defaultBlockState();
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shapesCache.get(pState.setValue(AGE, 0));
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.getStateForPlacement(pContext.getLevel(), pContext.getClickedPos());
    }

    protected BlockState getStateForPlacement(BlockGetter pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (!this.canCatchFire(pLevel, pPos, Direction.UP) && !blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP)) {
            BlockState blockstate1 = this.defaultBlockState();

            for(Direction direction : Direction.values()) {
                BooleanProperty booleanproperty = PROPERTY_BY_DIRECTION.get(direction);
                if (booleanproperty != null) {
                    blockstate1 = blockstate1.setValue(booleanproperty, this.canCatchFire(pLevel, pPos.relative(direction), direction.getOpposite()));
                }
            }

            return blockstate1;
        } else {
            return this.defaultBlockState();
        }
    }

    private BlockState getStateWithAge(LevelAccessor pLevel, BlockPos pPos, int pAge) {
        BlockState blockstate = getState(pLevel, pPos);
        return blockstate.is(ModBlocks.CONSIMING_FLAME.get()) ? blockstate.setValue(AGE, pAge) : blockstate;
    }

    public static void bootStrap() {
        ConsumingFlameBlock fireblock = (ConsumingFlameBlock) ModBlocks.CONSIMING_FLAME.get();
        fireblock.setFlammable(Blocks.OAK_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.SPRUCE_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.BIRCH_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.JUNGLE_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.ACACIA_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.CHERRY_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.DARK_OAK_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.MANGROVE_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.BAMBOO_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.BAMBOO_MOSAIC, 5, 20);
        fireblock.setFlammable(Blocks.OAK_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.SPRUCE_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.BIRCH_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.JUNGLE_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.ACACIA_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.CHERRY_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.DARK_OAK_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.MANGROVE_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.BAMBOO_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.BAMBOO_MOSAIC_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.OAK_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.SPRUCE_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.BIRCH_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.JUNGLE_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.ACACIA_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.CHERRY_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.MANGROVE_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.BAMBOO_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.OAK_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.SPRUCE_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.BIRCH_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.JUNGLE_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.ACACIA_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.CHERRY_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.DARK_OAK_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.MANGROVE_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.BAMBOO_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.OAK_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.BIRCH_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.SPRUCE_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.JUNGLE_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.ACACIA_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.CHERRY_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.DARK_OAK_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.MANGROVE_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.BAMBOO_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.BAMBOO_MOSAIC_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.OAK_LOG, 5, 5);
        fireblock.setFlammable(Blocks.SPRUCE_LOG, 5, 5);
        fireblock.setFlammable(Blocks.BIRCH_LOG, 5, 5);
        fireblock.setFlammable(Blocks.JUNGLE_LOG, 5, 5);
        fireblock.setFlammable(Blocks.ACACIA_LOG, 5, 5);
        fireblock.setFlammable(Blocks.CHERRY_LOG, 5, 5);
        fireblock.setFlammable(Blocks.DARK_OAK_LOG, 5, 5);
        fireblock.setFlammable(Blocks.MANGROVE_LOG, 5, 5);
        fireblock.setFlammable(Blocks.BAMBOO_BLOCK, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_OAK_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_SPRUCE_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_BIRCH_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_JUNGLE_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_ACACIA_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_CHERRY_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_DARK_OAK_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_MANGROVE_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_BAMBOO_BLOCK, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_OAK_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_SPRUCE_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_BIRCH_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_JUNGLE_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_ACACIA_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_CHERRY_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_DARK_OAK_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_MANGROVE_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.OAK_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.SPRUCE_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.BIRCH_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.JUNGLE_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.ACACIA_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.CHERRY_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.DARK_OAK_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.MANGROVE_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.MANGROVE_ROOTS, 5, 20);
        fireblock.setFlammable(Blocks.OAK_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.SPRUCE_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.BIRCH_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.JUNGLE_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.ACACIA_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.CHERRY_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.DARK_OAK_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.MANGROVE_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.BOOKSHELF, 30, 20);
        fireblock.setFlammable(Blocks.TNT, 15, 100);
        fireblock.setFlammable(Blocks.GRASS, 60, 100);
        fireblock.setFlammable(Blocks.FERN, 60, 100);
        fireblock.setFlammable(Blocks.DEAD_BUSH, 60, 100);
        fireblock.setFlammable(Blocks.SUNFLOWER, 60, 100);
        fireblock.setFlammable(Blocks.LILAC, 60, 100);
        fireblock.setFlammable(Blocks.ROSE_BUSH, 60, 100);
        fireblock.setFlammable(Blocks.PEONY, 60, 100);
        fireblock.setFlammable(Blocks.TALL_GRASS, 60, 100);
        fireblock.setFlammable(Blocks.LARGE_FERN, 60, 100);
        fireblock.setFlammable(Blocks.DANDELION, 60, 100);
        fireblock.setFlammable(Blocks.POPPY, 60, 100);
        fireblock.setFlammable(Blocks.BLUE_ORCHID, 60, 100);
        fireblock.setFlammable(Blocks.ALLIUM, 60, 100);
        fireblock.setFlammable(Blocks.AZURE_BLUET, 60, 100);
        fireblock.setFlammable(Blocks.RED_TULIP, 60, 100);
        fireblock.setFlammable(Blocks.ORANGE_TULIP, 60, 100);
        fireblock.setFlammable(Blocks.WHITE_TULIP, 60, 100);
        fireblock.setFlammable(Blocks.PINK_TULIP, 60, 100);
        fireblock.setFlammable(Blocks.OXEYE_DAISY, 60, 100);
        fireblock.setFlammable(Blocks.CORNFLOWER, 60, 100);
        fireblock.setFlammable(Blocks.LILY_OF_THE_VALLEY, 60, 100);
        fireblock.setFlammable(Blocks.TORCHFLOWER, 60, 100);
        fireblock.setFlammable(Blocks.PITCHER_PLANT, 60, 100);
        fireblock.setFlammable(Blocks.WITHER_ROSE, 60, 100);
        fireblock.setFlammable(Blocks.PINK_PETALS, 60, 100);
        fireblock.setFlammable(Blocks.WHITE_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.ORANGE_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.MAGENTA_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.LIGHT_BLUE_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.YELLOW_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.LIME_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.PINK_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.GRAY_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.LIGHT_GRAY_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.CYAN_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.PURPLE_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.BLUE_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.BROWN_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.GREEN_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.RED_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.BLACK_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.VINE, 15, 100);
        fireblock.setFlammable(Blocks.COAL_BLOCK, 5, 5);
        fireblock.setFlammable(Blocks.HAY_BLOCK, 60, 20);
        fireblock.setFlammable(Blocks.TARGET, 15, 20);
        fireblock.setFlammable(Blocks.WHITE_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.ORANGE_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.MAGENTA_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.LIGHT_BLUE_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.YELLOW_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.LIME_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.PINK_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.GRAY_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.LIGHT_GRAY_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.CYAN_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.PURPLE_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.BLUE_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.BROWN_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.GREEN_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.RED_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.BLACK_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.DRIED_KELP_BLOCK, 30, 60);
        fireblock.setFlammable(Blocks.BAMBOO, 60, 60);
        fireblock.setFlammable(Blocks.SCAFFOLDING, 60, 60);
        fireblock.setFlammable(Blocks.LECTERN, 30, 20);
        fireblock.setFlammable(Blocks.COMPOSTER, 5, 20);
        fireblock.setFlammable(Blocks.SWEET_BERRY_BUSH, 60, 100);
        fireblock.setFlammable(Blocks.BEEHIVE, 5, 20);
        fireblock.setFlammable(Blocks.BEE_NEST, 30, 20);
        fireblock.setFlammable(Blocks.AZALEA_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.FLOWERING_AZALEA_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.CAVE_VINES, 15, 60);
        fireblock.setFlammable(Blocks.CAVE_VINES_PLANT, 15, 60);
        fireblock.setFlammable(Blocks.SPORE_BLOSSOM, 60, 100);
        fireblock.setFlammable(Blocks.AZALEA, 30, 60);
        fireblock.setFlammable(Blocks.FLOWERING_AZALEA, 30, 60);
        fireblock.setFlammable(Blocks.BIG_DRIPLEAF, 60, 100);
        fireblock.setFlammable(Blocks.BIG_DRIPLEAF_STEM, 60, 100);
        fireblock.setFlammable(Blocks.SMALL_DRIPLEAF, 60, 100);
        fireblock.setFlammable(Blocks.HANGING_ROOTS, 30, 60);
        fireblock.setFlammable(Blocks.GLOW_LICHEN, 15, 100);
    }

    @NotNull
    public static BlockState getState(BlockGetter pReader, BlockPos pPos) {
        return ((ConsumingFlameBlock)ModBlocks.CONSIMING_FLAME.get()).getStateForPlacement(pReader, pPos);
    }


    @Override
    @ParametersAreNonnullByDefault
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return pLevel.getBlockState(blockpos).isFaceSturdy(pLevel, blockpos, Direction.UP) || this.isValidFireLocation(pLevel, pPos);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        pLevel.scheduleTick(pPos, this, getFireTickDelay(pLevel.random));
        if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {

            BlockState blockstate = pLevel.getBlockState(pPos.below());
            boolean flag = blockstate.isFireSource(pLevel, pPos, Direction.UP);
            int i = pState.getValue(AGE);
            if (!flag && pLevel.isRaining() && this.isNearRain(pLevel, pPos) && pRandom.nextFloat() < 0.2F + (float) i * 0.03F) {
                pLevel.removeBlock(pPos, false);
                pLevel.removeBlock(pPos, false);
            } else {
                int j = Math.min(15, i + pRandom.nextInt(3) / 2);
                if (i != j) {
                    pState = pState.setValue(AGE, j);
                    pLevel.setBlock(pPos, pState, 4);
                }

                if (!flag) {
                    if (!this.isValidFireLocation(pLevel, pPos)) {
                        BlockPos blockpos = pPos.below();
                        if (!pLevel.getBlockState(blockpos).isFaceSturdy(pLevel, blockpos, Direction.UP) || i > 3) {
                            pLevel.removeBlock(pPos, false);
                        }

                        return;
                    }

                    if (i == 15 && pRandom.nextInt(4) == 0 && !this.canCatchFire(pLevel, pPos.below(), Direction.UP)) {
                        pLevel.removeBlock(pPos, false);
                    }
                }

            }
        }

    }

    protected boolean isNearRain(Level pLevel, BlockPos pPos) {
        return pLevel.isRainingAt(pPos) || pLevel.isRainingAt(pPos.west()) || pLevel.isRainingAt(pPos.east()) || pLevel.isRainingAt(pPos.north()) || pLevel.isRainingAt(pPos.south());
    }

    @Deprecated //Forge: Use IForgeBlockState.getFlammability, Public for default implementation only.
    public int getBurnOdds(BlockState pState) {
        return pState.hasProperty(BlockStateProperties.WATERLOGGED) && pState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.burnOdds.getInt(pState.getBlock());
    }

    @Deprecated //Forge: Use IForgeBlockState.getFireSpreadSpeed
    public int getIgniteOdds(BlockState pState) {
        return pState.hasProperty(BlockStateProperties.WATERLOGGED) && pState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.igniteOdds.getInt(pState.getBlock());
    }

    private boolean isValidFireLocation(BlockGetter pLevel, BlockPos pPos) {
        for(Direction direction : Direction.values()) {
            if (this.canCatchFire(pLevel, pPos.relative(direction), direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }


    @Deprecated //Forge: Use canCatchFire with more context
    @Override
    @ParametersAreNonnullByDefault
    protected boolean canBurn(BlockState pState) {
        return this.getIgniteOdds(pState) > 0;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        pLevel.scheduleTick(pPos, this, getFireTickDelay(pLevel.random));
    }

    private static int getFireTickDelay(RandomSource pRandom) {
        return 30 + pRandom.nextInt(10);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
    }

    private void setFlammable(Block pBlock, int pEncouragement, int pFlammability) {
        if (pBlock == Blocks.AIR) throw new IllegalArgumentException("Tried to set air on fire... This is bad.");
        this.igniteOdds.put(pBlock, pEncouragement);
        this.burnOdds.put(pBlock, pFlammability);
    }

    public boolean canCatchFire(BlockGetter world, BlockPos pos, Direction face) {
        return world.getBlockState(pos).isFlammable(world, pos, face);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CustomFireEntity(pPos,pState);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return EntityBlock.super.getTicker(pLevel, pState, pBlockEntityType);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel pLevel, T pBlockEntity) {
        return EntityBlock.super.getListener(pLevel, pBlockEntity);
    }


}

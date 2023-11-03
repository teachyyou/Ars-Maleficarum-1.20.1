package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.world.level.block.state.properties.Property;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;

public class SunlightFlower extends CropBlock {
    public  static final  int FIRST_STAGE_MAX_AGE = 3;
    public  static final  int SECOND_STAGE_MAX_AGE = 3;
    public static final IntegerProperty AGE = IntegerProperty.create("age",0,6);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)};
    public SunlightFlower(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
    @Override
    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState) {
        int currentAge = this.getAge(pState);
        boolean flag = true;
        if(currentAge<FIRST_STAGE_MAX_AGE)
        {
            pLevel.setBlock(pPos,this.getStateForAge(currentAge+1),2);
        }
        else if(currentAge==FIRST_STAGE_MAX_AGE && pLevel.getBlockState(pPos.above(1)).is(Blocks.AIR))
        {
                pLevel.setBlock(pPos.above(1),this.getStateForAge(4),2);
        }
        else
        {
            if(!flag)
            {
                pLevel.setBlock(pPos.above(1),this.getStateForAge(currentAge+2),2);
                flag =true;
            }
            else {
                pLevel.setBlock(pPos.above(1),this.getStateForAge(currentAge+3),2);
                flag = false;
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return super.canSurvive(pState, pLevel, pPos) || (pLevel.getBlockState(pPos.below(1)).is(this)
        && pLevel.getBlockState(pPos.below(1)).getValue(AGE)==3);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return super.mayPlaceOn(state,world,pos);
    }

    @Override
    public int getMaxAge() {
        return FIRST_STAGE_MAX_AGE+SECOND_STAGE_MAX_AGE;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.SUNLIGHT_FLOWER_SEED.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }
    //public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        //if (!pLevel.isAreaLoaded(pPos, 1)) return;
        //if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            //int currentAge = this.getAge(pState);

            //if (currentAge < this.getMaxAge()) {
                //float growthSpeed = getGrowthSpeed(this, pLevel, pPos);

                //if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / growthSpeed) + 1) == 0)) {
                   // if(currentAge == FIRST_STAGE_MAX_AGE) {
                       // if(pLevel.getBlockState(pPos.above(1)).is(Blocks.AIR)) {
                          //  pLevel.setBlock(pPos.above(1), this.getStateForAge(currentAge + 1), 2);
                       // }
                   // } else {
                      //  pLevel.setBlock(pPos, this.getStateForAge(currentAge + 1), 2);
                    //}

                   // net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                //}
           // }
        //}
    //}

}

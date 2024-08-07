package net.sfedu.ars_maleficarum.block.custom;

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
import net.minecraftforge.common.IPlantable;
import net.sfedu.ars_maleficarum.item.ModItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;


public class SunlightFlower extends CropBlock {
    public  static final  int FIRST_STAGE_MAX_AGE = 3;
    public  static final  int SECOND_STAGE_MAX_AGE = 3;

    public static final IntegerProperty AGE = IntegerProperty.create("age",0,6);
    public SunlightFlower(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
    @Override
    @ParametersAreNonnullByDefault
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(Blocks.GRASS_BLOCK) || pState.is(Blocks.DIRT);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState) {
        int nextAge = this.getAge(pState) + 1;
        int maxAge=this.getMaxAge();
        if(nextAge>maxAge) {
            nextAge=maxAge;
        }
        for(int i=2;i<150;i++){
            if(!pLevel.getBlockState(pPos.above(i)).is(Blocks.AIR)){
                return;
            }
        }
        if (this.getAge(pState)==FIRST_STAGE_MAX_AGE && (pLevel.getBlockState(pPos.above(1)).is(Blocks.AIR) && pLevel.isDay())){
            pLevel.setBlock(pPos.above(1),this.getStateForAge(4),2);
        }

        else if ((nextAge==FIRST_STAGE_MAX_AGE+1) && (this.getAge(pLevel.getBlockState(pPos.above(1)))==4)&& pLevel.isDay()) {
            pLevel.setBlock(pPos.above(1),this.getStateForAge(nextAge+1),2);
        }
        else if ((nextAge>FIRST_STAGE_MAX_AGE) && (this.getAge(pLevel.getBlockState(pPos.above(1)))>=5)&& pLevel.isDay()) {
            pLevel.setBlock(pPos.above(1),this.getStateForAge(nextAge+2),2);
        }
        else {
            if(pLevel.isDay() && pLevel.getBlockState(pPos.above(1)).is(Blocks.AIR))
                pLevel.setBlock(pPos,this.getStateForAge(nextAge),2);
        }
    }
    @Override
    @ParametersAreNonnullByDefault
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if(!Is_Blocks_Above(pLevel,pPos,pState))
        {
            return false;
        }
        return super.canSurvive(pState, pLevel, pPos) || (pLevel.getBlockState(pPos.below(1)).is(this) && pLevel.getBlockState(pPos.below(1)).getValue(AGE)==3);
    }
    public boolean Is_Blocks_Above(LevelReader pLevel, BlockPos pPos, BlockState pState)
    {
        if(this.getAge(pState)==0)
            return true;
        for(int i=1;i<=150;i++)
            if(!pLevel.getBlockState(pPos.above(i)).is(Blocks.AIR) && !pLevel.getBlockState(pPos.above(1)).is(this))
                return false;
        return true;
    }
    @Override
    @ParametersAreNonnullByDefault
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return this.mayPlaceOn(state,world,pos);
    }

    @Override
    public int getMaxAge() {
        return FIRST_STAGE_MAX_AGE+SECOND_STAGE_MAX_AGE;
    }

    @Override
    @NotNull
    protected ItemLike getBaseSeedId() {
        return ModItems.SUNLIGHT_FLOWER_SEED.get();
    }

    @Override
    @NotNull
    public IntegerProperty getAgeProperty() {
        return AGE;
    }
    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState pState, @NotNull ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;
        canSurvive(pState,pLevel,pPos);
        if(pLevel.getRawBrightness(pPos,1)<8)
            return;
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            int currentAge = this.getAge(pState);
            if (!canSurvive(pState,pLevel,pPos))
            {
                pLevel.setBlock(pPos,Blocks.AIR.defaultBlockState(),2);
                return;
            }
            if (currentAge < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / growthSpeed) + 1) == 0)) {
                    if(currentAge == FIRST_STAGE_MAX_AGE && pLevel.isDay()) {
                        if(pLevel.getBlockState(pPos.above(1)).is(Blocks.AIR)) {
                            pLevel.setBlock(pPos.above(1), this.getStateForAge(currentAge + 1), 2);
                       }
                   }
                    else {
                        if(pLevel.isDay())
                            pLevel.setBlock(pPos, this.getStateForAge(currentAge + 1), 2);
                    }
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

}

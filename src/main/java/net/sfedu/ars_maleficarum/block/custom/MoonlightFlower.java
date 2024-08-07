package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
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

public class MoonlightFlower extends CropBlock {
    public  static final  int FIRST_STAGE_MAX_AGE = 3;
    public  static final  int SECOND_STAGE_MAX_AGE = 3;

    public static final IntegerProperty AGE = IntegerProperty.create("age",0,6);
    public MoonlightFlower(Properties pProperties) {
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
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (!pLevel.canSeeSky(pPos)) {
            return false;
        }
        return super.canSurvive(pState, pLevel, pPos) || (pLevel.getBlockState(pPos.below(1)).is(this) && pLevel.getBlockState(pPos.below(1)).getValue(AGE)==3);
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
        return ModItems.MOONLIGHT_FLOWER_SEED.get();
    }

    @Override
    @NotNull
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
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
                    if(currentAge == FIRST_STAGE_MAX_AGE && pLevel.isNight()) {
                        if(pLevel.getBlockState(pPos.above(1)).is(Blocks.AIR)) {
                            pLevel.setBlock(pPos.above(1), this.getStateForAge(currentAge + 1), 2);
                        }
                    }
                    else {
                        if(pLevel.isNight())
                            pLevel.setBlock(pPos, this.getStateForAge(currentAge + 1), 2);
                    }
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }
}

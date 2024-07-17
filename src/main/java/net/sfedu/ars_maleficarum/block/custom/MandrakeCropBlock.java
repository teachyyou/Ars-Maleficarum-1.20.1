package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class MandrakeCropBlock extends CropBlock {

    //Максимальная стадия роста (от 0)
    public static final int MAX_AGE = 3;

    //Стадия роста
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    //Конструктор, вызывающий конструктор родителя
    public MandrakeCropBlock(Properties pProperties) {
        super(pProperties);
    }

    //Семена, необходимые для выращивания
    @Override
    @NotNull
    protected ItemLike getBaseSeedId() {
        return ModItems.MANDRAKE_SEED.get();
    }

    //Текущая стадия роста
    @Override
    @NotNull
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    //Максимальная стадия роста
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    private void spawnMandrake(Level pLevel, BlockPos pPos) {
        MandrakeEntity mandrake = ModEntities.MANDRAKE.get().create(pLevel);
        if (mandrake != null) {
            pLevel.setBlock(pPos,Blocks.AIR.defaultBlockState(),2);
            mandrake.moveTo((double)pPos.getX() + 0.5D, pPos.getY(), (double)pPos.getZ() + 0.5D, 0.0F, 0.0F);
            pLevel.addFreshEntity(mandrake);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !pLevel.isClientSide && pState.getValue(AGE)==3) {
            boolean shouldAwake =
                    (pLevel.isDay() && pLevel.getRandom().nextFloat() <= 0.8) ||
                    pLevel.getRandom().nextFloat() <= 0.2;
            if (shouldAwake && pLevel.getDifficulty() != Difficulty.PEACEFUL){
                this.spawnMandrake(pLevel, pPos);
            } else {
                dropResources(pState,pLevel,pPos,pBlockEntity,pPlayer,pTool);
            }

        }
    }
}

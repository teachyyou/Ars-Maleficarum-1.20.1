package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.sfedu.ars_maleficarum.item.ModItems;

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
    protected ItemLike getBaseSeedId() {
        return ModItems.MANDRAKE_SEED.get();
    }

    //Текущая стадия роста
    @Override
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
}

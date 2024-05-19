package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sfedu.ars_maleficarum.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class MarigoldCropBlock extends CropBlock {

    //Максимальная стадия роста (от 0)
    public static final int MAX_AGE = 3;

    //Стадия роста
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),Block.box(0.0, 0.0, 0.0, 16.0, 13.0, 16.0)};

    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    //Конструктор, вызывающий конструктор родителя
    public MarigoldCropBlock(Properties pProperties) {
        super(pProperties);
    }

    //Семена, необходимые для выращивания
    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.MARIGOLD_SEED.get();
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

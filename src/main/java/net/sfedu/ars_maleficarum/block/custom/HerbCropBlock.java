package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HerbCropBlock extends CropBlock {

    // Определяем тип урожая, хранящий только формы для каждой стадии роста
    public enum CropType {
        SAGE("sage",new VoxelShape[]{
                Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 18.0, 16.0)
        }),
        MARIGOLD("marigold", new VoxelShape[]{
                Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 13.0, 16.0)
        });

        private final VoxelShape[] shapes;
        private final String id;

        CropType(String id, VoxelShape[] shapes) {
            this.shapes = shapes;
            this.id = id;
        }

        public VoxelShape[] getShapes() {
            return shapes;
        }
    }

    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private final CropType cropType;

    public HerbCropBlock(Properties properties, CropType cropType) {
        super(properties);
        this.cropType = cropType;
    }

    @Override
    @NotNull
    protected ItemLike getBaseSeedId() {
        // Возвращаем соответствующие семена для каждого типа урожая
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ArsMaleficarum.MOD_ID, cropType.toString() + "_seed")));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return cropType.getShapes()[this.getAge(state)];
    }

    @Override
    @NotNull
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}

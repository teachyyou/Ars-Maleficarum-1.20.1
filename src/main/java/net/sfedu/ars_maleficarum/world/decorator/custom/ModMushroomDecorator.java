package net.sfedu.ars_maleficarum.world.decorator.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.SwampRotfiendMushroom;
import net.sfedu.ars_maleficarum.world.decorator.ModTreeDecoratorTypes;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import java.util.List;

public class ModMushroomDecorator extends TreeDecorator {
    public static final Codec<ModMushroomDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(ModMushroomDecorator::new, (p_69989_) -> {
        return p_69989_.probability;
    }).codec();
    private final float probability;
    public static final ModMushroomDecorator INSTANCE = new ModMushroomDecorator(0.75F);
    public ModMushroomDecorator(float p_69976_) {
        this.probability = p_69976_;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecoratorTypes.SWAMP_ROTFIEND.get();
    }

    public void place(TreeDecorator.Context pContext) {
        RandomSource randomsource = pContext.random();
        if (!(randomsource.nextFloat() >= this.probability)) {
            List<BlockPos> list = pContext.logs();
            int i = list.get(0).getY();
            list.stream().filter((p_69980_) -> {
                return p_69980_.getY() - i <= 2 && p_69980_.getY() - i > 0;
            }).forEach((p_226026_) -> {
                for(Direction direction : Direction.Plane.HORIZONTAL) {
                    if (randomsource.nextFloat() <= 0.2F) {
                        Direction direction1 = direction.getOpposite();
                        BlockPos blockpos = p_226026_.offset(direction1.getStepX(), 0, direction1.getStepZ());
                        if (pContext.isAir(blockpos)) {
                            pContext.setBlock(blockpos, ModBlocks.SWAMP_ROTFIEND.get().defaultBlockState().setValue(SwampRotfiendMushroom.AGE, Integer.valueOf(randomsource.nextInt(3))).setValue(SwampRotfiendMushroom.FACING, direction));
                        }
                    }
                }

            });
        }
    }
}

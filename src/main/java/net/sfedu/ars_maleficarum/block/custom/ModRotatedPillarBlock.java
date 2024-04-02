package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.item.custom.FlintKnife;
import org.jetbrains.annotations.Nullable;

public class ModRotatedPillarBlock extends RotatedPillarBlock {

    public ModRotatedPillarBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return false;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }


}

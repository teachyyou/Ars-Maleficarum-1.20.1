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

public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {

    public ModFlammableRotatedPillarBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem || context.getItemInHand().getItem() instanceof FlintKnife) {
            if (state.is(ModBlocks.ROWAN_LOG.get())) {
                context.getLevel().addFreshEntity(new ItemEntity(context.getLevel(), context.getClickedPos().getX(),context.getClickedPos().getY(),context.getClickedPos().getZ(), new ItemStack(ModItems.ROWAN_BARK.get().asItem(),2)));
                return ModBlocks.STRIPPED_ROWAN_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            } else if (state.is(ModBlocks.ROWAN_WOOD.get())) {
                context.getLevel().addFreshEntity(new ItemEntity(context.getLevel(), context.getClickedPos().getX(),context.getClickedPos().getY(),context.getClickedPos().getZ(), new ItemStack(ModItems.ROWAN_BARK.get().asItem(),2)));
                return ModBlocks.STRIPPED_ROWAN_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }
        return super.getToolModifiedState(state,context,toolAction,simulate);
    }
}

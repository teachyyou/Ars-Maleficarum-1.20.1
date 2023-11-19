package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.ModBlocks;

public class ValuableDetectorItem extends Item {
    public ValuableDetectorItem(Item.Properties pProperties){
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()){
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundblock = false;
            for(int i=0;i<=positionClicked.getY()+64;i++){
                BlockState state_centre = pContext.getLevel().getBlockState(positionClicked.below(i));
                BlockState state_north = pContext.getLevel().getBlockState(positionClicked.north(1).below(i));
                BlockState state_north_east = pContext.getLevel().getBlockState(positionClicked.north(1).east(1).below(i));
                BlockState state_north_west = pContext.getLevel().getBlockState(positionClicked.north(1).west(1).below(i));
                BlockState state_south = pContext.getLevel().getBlockState(positionClicked.south(1).below(i));
                BlockState state_south_east = pContext.getLevel().getBlockState(positionClicked.south(1).east(1).below(i));
                BlockState state_south_west = pContext.getLevel().getBlockState(positionClicked.south(1).west(1).below(i));
                BlockState state_west = pContext.getLevel().getBlockState(positionClicked.west(1).below(i));
                BlockState state_east = pContext.getLevel().getBlockState(positionClicked.east(1).below(i));
                if(IsValuableBlock(state_north) || IsValuableBlock(state_centre) || IsValuableBlock(state_south)|| IsValuableBlock(state_east)
                        || IsValuableBlock(state_west) || IsValuableBlock(state_north_east) || IsValuableBlock(state_north_west) || IsValuableBlock(state_south_east) ||
                        IsValuableBlock(state_south_west)){
                    outputValuableCordinaties(positionClicked.below(i),player);
                    foundblock = true;
                    break;
                }


            }
            if(!foundblock){
                player.sendSystemMessage(Component.translatable("Valuable_not_found_message"));
            }
        }
        pContext.getItemInHand().hurtAndBreak(1,pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));
        return InteractionResult.SUCCESS;
    }

    private void outputValuableCordinaties(BlockPos blockPos, Player player) {
        player.sendSystemMessage(Component.translatable("Valuable_found_message"));
    }

    private boolean IsValuableBlock(BlockState state){
        return state.is(Blocks.REDSTONE_ORE) || state.is(Blocks.EMERALD_ORE) || state.is(Blocks.LAPIS_ORE) || state.is(Blocks.AMETHYST_BLOCK) || state.is(Blocks.NETHER_QUARTZ_ORE);
    }
}

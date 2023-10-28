package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CarbonDetectorItem extends Item {
    public CarbonDetectorItem(Properties pProperties){
        super(pProperties);
    }
    String message = "Found a carbon ore at radius of 3 blocks ";
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
                if(IsCarbonBlock(state_north) || IsCarbonBlock(state_centre) || IsCarbonBlock(state_south)|| IsCarbonBlock(state_east)
                   || IsCarbonBlock(state_west) || IsCarbonBlock(state_north_east) || IsCarbonBlock(state_north_west) || IsCarbonBlock(state_south_east) ||
                   IsCarbonBlock(state_south_west)){
                       outputValuableCordinaties(positionClicked.below(i),player);
                       foundblock = true;
                       break;
                   }


            }
            if(!foundblock){
                player.sendSystemMessage(Component.literal("No Carbon ore Found at radius of 3 blocks"));
            }
        }
        pContext.getItemInHand().hurtAndBreak(1,pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));
        return InteractionResult.SUCCESS;
    }

    private void outputValuableCordinaties(BlockPos blockPos, Player player) {
        player.sendSystemMessage(Component.literal(message));
    }

    private boolean IsCarbonBlock(BlockState state){
        return state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.COAL_ORE);
    }
}

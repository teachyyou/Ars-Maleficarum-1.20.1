package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import net.sfedu.ars_maleficarum.block.ModBlocks;

public class MetalDetectorItem extends Item{
    public MetalDetectorItem(Item.Properties pProperties){
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()){
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            player.getCooldowns().addCooldown(this,200);
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
                if(IsMetalBlock(state_north) || IsMetalBlock(state_centre) || IsMetalBlock(state_south)|| IsMetalBlock(state_east)
                        || IsMetalBlock(state_west) || IsMetalBlock(state_north_east) || IsMetalBlock(state_north_west) ||IsMetalBlock(state_south_east) ||
                        IsMetalBlock(state_south_west)){
                    outputValuableCordinaties(positionClicked.below(i),player);
                    foundblock = true;
                    break;
                }

            }
            if(!foundblock){
                player.sendSystemMessage(Component.translatable("Metal_not_found_message"));
            }
        }
        pContext.getItemInHand().hurtAndBreak(1,pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));
        return InteractionResult.SUCCESS;
    }

    private void outputValuableCordinaties(BlockPos blockPos, Player player) {
        player.sendSystemMessage(Component.translatable("Metal_found_message"));
    }

    private boolean IsMetalBlock(BlockState state){
        return state.is(Blocks.IRON_ORE) || state.is(Blocks.COPPER_ORE) || state.is(Blocks.GOLD_ORE ) || state.is(ModBlocks.SILVER_ORE_BLOCK.get()) || state.is(ModBlocks.CURSED_GOLD_ORE_BLOCK.get()) ;
    }
}

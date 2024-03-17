package net.sfedu.ars_maleficarum.item.custom.ritualCircleItems;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ChalkSymbol;

import java.util.Random;

public class GreenChalk extends Item {

    public GreenChalk(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            Direction[] dirs = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
            Direction glyphDirection = dirs[new Random().nextInt(4)];
            if (pContext.getLevel().getBlockState(pContext.getClickedPos()).is(ModBlocks.GREEN_CHALK_SYMBOL.get())) {
                int variant = pContext.getLevel().getBlockState(pContext.getClickedPos()).getValue(ChalkSymbol.VARIANT);
                pContext.getLevel().setBlock(pContext.getClickedPos(), ModBlocks.GREEN_CHALK_SYMBOL.get().defaultBlockState().setValue(ChalkSymbol.VARIANT,(variant+1)%11).setValue(ChalkSymbol.FACING, glyphDirection),3);
            }
            else if (pContext.getLevel().getBlockState(pContext.getClickedPos()).isCollisionShapeFullBlock(pContext.getLevel(),pContext.getClickedPos())) {
                Random random = new Random();
                pContext.getLevel().setBlock(pContext.getClickedPos().above(), ModBlocks.GREEN_CHALK_SYMBOL.get().defaultBlockState().setValue(ChalkSymbol.VARIANT,random.nextInt(11 )).setValue(ChalkSymbol.FACING, glyphDirection),3);
            }
            else {
                return InteractionResult.FAIL;
            }
        }
        pContext.getItemInHand().hurtAndBreak(1,pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));
        return InteractionResult.SUCCESS;
    }


    @Override
    public int getMaxDamage(ItemStack stack) {
        return 44;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 8;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }


}

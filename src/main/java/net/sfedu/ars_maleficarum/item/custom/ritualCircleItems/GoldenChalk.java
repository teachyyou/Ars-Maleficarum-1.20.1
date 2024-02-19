package net.sfedu.ars_maleficarum.item.custom.ritualCircleItems;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.sfedu.ars_maleficarum.block.ModBlocks;

public class GoldenChalk extends Item {

    public GoldenChalk(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            if (pContext.getLevel().getBlockState(pContext.getClickedPos()).isCollisionShapeFullBlock(pContext.getLevel(),pContext.getClickedPos())) {
                pContext.getLevel().setBlock(pContext.getClickedPos().above(), ModBlocks.WHITE_CHALK_SYMBOL.get().defaultBlockState(),3);
            }
            return InteractionResult.FAIL;
        }
        pContext.getItemInHand().hurtAndBreak(1,pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));
        return InteractionResult.SUCCESS;
    }
}

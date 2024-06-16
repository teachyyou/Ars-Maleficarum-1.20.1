package net.sfedu.ars_maleficarum.item.custom.ritualCircleItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.RitualCircleCore;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.sound.ModSounds;

public class RitualCircleCoreDrawingKit extends Item {
    protected final RitualCoreEntity.ChalkType coreType;

    public RitualCircleCoreDrawingKit(RitualCoreEntity.ChalkType coreType) {
        super(new Item.Properties().stacksTo(1).durability(1));
        this.coreType = coreType;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            if (pContext.getLevel().getBlockState(pContext.getClickedPos()).isCollisionShapeFullBlock(pContext.getLevel(),pContext.getClickedPos()) && canDraw(pContext)) {
                BlockState coreToDraw = ModBlocks.RITUAL_CIRCLE_CORE.get().defaultBlockState().setValue(RitualCircleCore.CIRCLE_TYPE, coreType);
                pContext.getLevel().setBlock(pContext.getClickedPos().above(), coreToDraw,3);
                pContext.getItemInHand().shrink(1);
                pContext.getLevel().playSound(null,pContext.getClickedPos(), ModSounds.CHALK_USE.get(), SoundSource.PLAYERS);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    private boolean canDraw(UseOnContext pContext) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j<=1; j++) {
                BlockPos pos = pContext.getClickedPos().relative(Direction.Axis.X, i).relative(Direction.Axis.Z,j);
                if (pContext.getLevel().getBlockState(pos).is(Blocks.AIR) || !(pContext.getLevel().getBlockState(pos.above()).is(Blocks.AIR) || pContext.getLevel().getBlockState(pos.above()).is(BlockTags.CANDLES))) {
                    return false;
                }
            }
        }
        return true;
    }
}
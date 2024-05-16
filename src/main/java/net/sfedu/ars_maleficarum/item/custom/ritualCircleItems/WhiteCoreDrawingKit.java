package net.sfedu.ars_maleficarum.item.custom.ritualCircleItems;

import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.RitualCircleCore;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;

public class WhiteCoreDrawingKit extends RitualCircleCoreDrawingKit{

    public WhiteCoreDrawingKit(Properties pProperties) {
        super(pProperties);
        coreToDraw = ModBlocks.RITUAL_CIRCLE_CORE.get().defaultBlockState().setValue(RitualCircleCore.CIRCLETYPE, RitualCoreEntity.ChalkType.WHITE);
    }


}

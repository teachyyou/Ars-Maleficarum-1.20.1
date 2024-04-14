package net.sfedu.ars_maleficarum.item.custom.ritualCircleItems;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ChalkSymbol;
import net.sfedu.ars_maleficarum.sound.ModSounds;

import java.util.Random;

public class GreenChalk extends ChalkItem {

    public GreenChalk(Properties pProperties) {
        super(pProperties);
        chalkSymbol = ModBlocks.GREEN_CHALK_SYMBOL.get();
    }

}

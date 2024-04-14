package net.sfedu.ars_maleficarum.item.custom.ritualCircleItems;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ChalkSymbol;
import net.sfedu.ars_maleficarum.sound.ModSounds;

import java.util.Random;

public class BlackChalk extends ChalkItem {

    public BlackChalk(Properties pProperties) {
        super(pProperties);
        //todo переделать
        chalkSymbol = ModBlocks.WHITE_CHALK_SYMBOL.get();
    }

}

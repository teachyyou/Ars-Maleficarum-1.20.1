package net.sfedu.ars_maleficarum.item.custom.ritualCircleItems;

import net.minecraft.core.Direction;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ChalkSymbol;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import net.sfedu.ars_maleficarum.util.ModTags;

import java.util.Random;

public class ChalkItem extends Item {

    public ChalkItem(Properties pProperties) {
        super(pProperties);
    }

    protected Block chalkSymbol;

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            Direction[] dirs = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
            Direction glyphDirection = dirs[new Random().nextInt(4)];
            if (pContext.getLevel().getBlockState(pContext.getClickedPos()).is(ModTags.Blocks.CHALK_SYMBOLS)) {
                int variant = pContext.getLevel().getBlockState(pContext.getClickedPos()).getValue(ChalkSymbol.VARIANT);
                pContext.getLevel().setBlock(pContext.getClickedPos(), chalkSymbol.defaultBlockState().setValue(ChalkSymbol.VARIANT,(variant+1)%11).setValue(ChalkSymbol.FACING, glyphDirection),3);
            }
            else if (pContext.getLevel().getBlockState(pContext.getClickedPos()).isCollisionShapeFullBlock(pContext.getLevel(),pContext.getClickedPos())) {
                Random random = new Random();
                pContext.getLevel().setBlock(pContext.getClickedPos().above(), chalkSymbol.defaultBlockState().setValue(ChalkSymbol.VARIANT,random.nextInt(11 )).setValue(ChalkSymbol.FACING, glyphDirection),3);
            }
            else {
                return InteractionResult.FAIL;
            }
        }

        if (pContext.getItemInHand().getCount()>1) {
            int dif = pContext.getItemInHand().getCount()-1;
            ItemStack remaining = pContext.getItemInHand().copy();
            remaining.setCount(dif);
            System.out.println(dif);
            pContext.getItemInHand().setCount(1);
            pContext.getItemInHand().hurtAndBreak(1,pContext.getPlayer(),
                    p->{});
            if (!pContext.getPlayer().getInventory().add(remaining)) {
                Vec3 vec3 = pContext.getPlayer().position();
                pContext.getLevel().addFreshEntity(new ItemEntity(pContext.getLevel(), vec3.x,vec3.y,vec3.z, remaining));
            }
        } else {
            pContext.getItemInHand().hurtAndBreak(1,pContext.getPlayer(),
                    p->{});
        }
        pContext.getLevel().playSound(null,pContext.getClickedPos(), ModSounds.CHALK_USE.get(), SoundSource.PLAYERS);
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

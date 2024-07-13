package net.sfedu.ars_maleficarum.item.custom.ritualCircleItems;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ChalkSymbol;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import net.sfedu.ars_maleficarum.util.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class ChalkItem extends Item {

    public ChalkItem(Item.Properties properties, Block chalkSymbol) {
        super(properties);
        this.chalkSymbol=chalkSymbol;
    }

    protected Block chalkSymbol;

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 8;
    }

    @Override
    @NotNull
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

        chalkUse(Objects.requireNonNull(pContext.getPlayer()), pContext.getItemInHand(), pContext.getLevel());

        pContext.getLevel().playSound(null,pContext.getClickedPos(), ModSounds.CHALK_USE.get(), SoundSource.PLAYERS);
        return InteractionResult.SUCCESS;
    }

    //divides chalk stack after using
    public static void chalkUse(Player player, ItemStack itemStack, Level level) {
        if (!player.isCreative()) {
            if (itemStack.getCount() > 1) {
                int remainingCount = itemStack.getCount() - 1;
                ItemStack remainingStack = itemStack.copy();
                remainingStack.setCount(remainingCount);
                itemStack.setCount(1);
                itemStack.hurtAndBreak(1, player, p -> {});
                if (!player.getInventory().add(remainingStack)) {
                    Vec3 playerPos = player.position();
                    level.addFreshEntity(new ItemEntity(level, playerPos.x, playerPos.y, playerPos.z, remainingStack));
                }
            } else {
                itemStack.hurtAndBreak(1, player, p -> {});
            }
        }
    }

}

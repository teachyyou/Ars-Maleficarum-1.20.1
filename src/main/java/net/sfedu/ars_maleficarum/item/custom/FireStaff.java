package net.sfedu.ars_maleficarum.item.custom;

import com.ibm.icu.impl.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.entity.custom.FireEssenceEntity;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FireStaff extends Item implements Vanishable {

    private final List<Pair<Integer,Integer>> coordinate_list = new ArrayList<>(Arrays.asList(Pair.of(0,-4),Pair.of(-1,-4),Pair.of(-2,-3),Pair.of(-3,-2),Pair.of(-4,-1),Pair.of(-4,0), Pair.of(-4,1),
            Pair.of(-3,2),Pair.of(-2,3),Pair.of(-1,4),Pair.of(0,4),Pair.of(1,4),Pair.of(2,3),Pair.of(3,2),Pair.of(4,1),Pair.of(4,0),Pair.of(4,-1),Pair.of(3,-2),Pair.of(2,-3),Pair.of(1,-4)));
    public FireStaff(Properties pProperties) {
        super(pProperties);
    }

    private void spawnConsimingFlame(int X, int Z, Level pLevel, BlockPos pPose)
    {
        if(pLevel.isEmptyBlock(pPose.relative(Direction.Axis.X,X).relative(Direction.Axis.Z,Z)) && !pLevel.isEmptyBlock(pPose.relative(Direction.Axis.X,X).relative(Direction.Axis.Z,Z).relative(Direction.Axis.Y,-1)))
            pLevel.setBlock(pPose.relative(Direction.Axis.X,X).relative(Direction.Axis.Z,Z), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(), 3);
        else if(pLevel.isEmptyBlock(pPose.relative(Direction.Axis.X,X).relative(Direction.Axis.Z,Z).relative(Direction.Axis.Y,-1)))
            pLevel.setBlock(pPose.relative(Direction.Axis.X,X).relative(Direction.Axis.Z,Z).relative(Direction.Axis.Y,-1), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(), 3);
        else if(pLevel.isEmptyBlock(pPose.relative(Direction.Axis.X,X).relative(Direction.Axis.Z,Z).relative(Direction.Axis.Y,1)))
            pLevel.setBlock(pPose.relative(Direction.Axis.X,X).relative(Direction.Axis.Z,Z).relative(Direction.Axis.Y,1), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(), 3);

    }
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(player.isCrouching())
        {
            BlockPos pPose = player.getOnPos().above(1);
            coordinate_list.forEach(x-> spawnConsimingFlame(x.first, x.second, level, pPose));
            player.getCooldowns().addCooldown(this, 200);
            player.playSound(ModSounds.FIRE_STAFF_RADIUS_ATTACK.get(), 1.0F, (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
        }
        else {
            player.playSound(SoundEvents.GHAST_SHOOT, 1.0F, (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
            player.getCooldowns().addCooldown(this, 40);
            if (!level.isClientSide()) {
                FireEssenceEntity essenceEntity = new FireEssenceEntity(level, player);
                essenceEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.25F);
                level.addFreshEntity(essenceEntity);
            }
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}

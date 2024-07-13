package net.sfedu.ars_maleficarum.item.custom;

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

public class FireStaff extends Item implements Vanishable {

    public FireStaff(Properties pProperties) {
        super(pProperties);
    }
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(player.isCrouching())
        {
            BlockPos pPose = player.getOnPos().above(1);
            for(int i=0;i<2;++i)
            {
                level.setBlock(pPose.relative(Direction.Axis.X,-1+2*i).relative(Direction.Axis.Z,-4), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
                ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,-1+2*i).relative(Direction.Axis.Z,-4),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

                level.setBlock(pPose.relative(Direction.Axis.X,-2+2*2*i).relative(Direction.Axis.Z,-3), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
                ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,-2+2*2*i).relative(Direction.Axis.Z,-3),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

                level.setBlock(pPose.relative(Direction.Axis.X,-3+3*2*i).relative(Direction.Axis.Z,-2), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
                ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,-3+3*2*i).relative(Direction.Axis.Z,-2),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

                level.setBlock(pPose.relative(Direction.Axis.X,-4+4*2*i).relative(Direction.Axis.Z,-1), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
                ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,-4+4*2*i).relative(Direction.Axis.Z,-1),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

                level.setBlock(pPose.relative(Direction.Axis.X,-4+4*2*i).relative(Direction.Axis.Z,0), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
                ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,-4+4*2*i).relative(Direction.Axis.Z,0),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

                level.setBlock(pPose.relative(Direction.Axis.X,-4+4*2*i).relative(Direction.Axis.Z,1), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
                ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,-4+4*2*i).relative(Direction.Axis.Z,1),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

                level.setBlock(pPose.relative(Direction.Axis.X,-3+3*2*i).relative(Direction.Axis.Z,2), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
                ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,-3+3*2*i).relative(Direction.Axis.Z,2),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

                level.setBlock(pPose.relative(Direction.Axis.X,-2+2*2*i).relative(Direction.Axis.Z,3), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
                ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,-2+2*2*i).relative(Direction.Axis.Z,3),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

                level.setBlock(pPose.relative(Direction.Axis.X,-1+2*i).relative(Direction.Axis.Z,4), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
                ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,-1+2*i).relative(Direction.Axis.Z,4),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

            }
            level.setBlock(pPose.relative(Direction.Axis.X,0).relative(Direction.Axis.Z,4), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
            ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,0).relative(Direction.Axis.Z,4),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

            level.setBlock(pPose.relative(Direction.Axis.X,0).relative(Direction.Axis.Z,-4), ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),3);
            ModBlocks.CONSIMING_FLAME.get().setPlacedBy(level,pPose.relative(Direction.Axis.X,0).relative(Direction.Axis.Z,-4),ModBlocks.CONSIMING_FLAME.get().defaultBlockState(),player,stack);

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

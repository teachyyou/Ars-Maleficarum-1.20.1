package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.custom.PoisonousEssenceEntity;
import net.sfedu.ars_maleficarum.util.ModTags;

import java.util.function.Predicate;

public class PoisonStaff extends Item {
    public PoisonStaff(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
        player.getCooldowns().addCooldown(this, 40);
        if (!level.isClientSide()) {
            PoisonousEssenceEntity essenceEntity = new PoisonousEssenceEntity(level, player);
            essenceEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.25F);
            level.addFreshEntity(essenceEntity);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}

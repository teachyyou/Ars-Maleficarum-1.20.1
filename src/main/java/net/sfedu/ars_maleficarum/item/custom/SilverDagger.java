package net.sfedu.ars_maleficarum.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class SilverDagger extends SwordItem {
    public SilverDagger(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {

        if (entity instanceof LivingEntity livingEntity && livingEntity.isInvertedHealAndHarm()) {
            livingEntity.hurt(entity.damageSources().generic(),8);

            livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER,500,1));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,500,2));
        }
        return super.onLeftClickEntity(stack, player, entity);
    }


}

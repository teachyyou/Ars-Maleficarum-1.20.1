package net.sfedu.ars_maleficarum.entity.ai;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.sfedu.ars_maleficarum.entity.custom.TraderWitchEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.sound.ModSounds;

import java.util.Random;

public class Trader_Witch_AttackGoal extends MeleeAttackGoal {
    TraderWitchEntity witch;
    private int attackDelay = 30;
    private int ticksUntilNextAttack = 10;
    private int blindDelay = 40;
    private int ticksUntilNextBlind = 10;
    private boolean shouldCountTillNextAttack = false;
    private boolean shouldCountTillNextBlind = false;
    public Trader_Witch_AttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        witch = (TraderWitchEntity) pMob;
    }
    @Override
    public void start() {
        super.start();
        attackDelay = 30;
        ticksUntilNextAttack = 35;
        blindDelay = 20;
        ticksUntilNextBlind = 30;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if(pEnemy instanceof Player  &&  enemyHasWitcheryHat(pEnemy))
        {
            witch.setHealth(witch.getMaxHealth());
        }
        else if (isEnemyWithinAttackDistance(pEnemy, pDistToEnemySqr)) {
            shouldCountTillNextAttack = true;
            shouldCountTillNextBlind = true;
            witch.setAttacking(true);
            if(isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performAttack(pEnemy);
                if(isTimeToBlind()){
                    Random rnd = new Random();
                    if(rnd.nextFloat()>0.8)
                        witch.playSound(ModSounds.TRADER_WITCH_ATTACK.get());
                    performCast(pEnemy);
                    performBlind(pEnemy);
                }
                else {
                    resetBlindCooldown();
                    shouldCountTillNextBlind = false;
                }
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            witch.setAttacking(false);
        }
    }
    private boolean enemyHasWitcheryHat(LivingEntity pEnemy)
    {
        Player player = (Player) pEnemy;
        return player.getInventory().getArmor(3).is(ModItems.SIMPLE_WITCH_HAT.get());
    }
    private void performCast(LivingEntity pEnemy) {
        if(witch.getHealth()<40D)
        {
            witch.addEffect(new MobEffectInstance(MobEffects.REGENERATION,200));
            witch.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,25));
            witch.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,25));
            double d0 = (pEnemy.getRandomX(3) - 0.5D);
            double d2 = (pEnemy.getRandomZ(3) - 0.5D);
            witch.teleportTo(d0,witch.getY(),d2);
            witch.playSound(SoundEvents.ENDERMAN_TELEPORT);
        }

    }
    protected void performBlind(LivingEntity pEnemy) {
        resetBlindCooldown();
        pEnemy.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,80));
        pEnemy.addEffect(new MobEffectInstance(MobEffects.DARKNESS,80));
        pEnemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,80));
        witch.playSound(SoundEvents.EVOKER_CAST_SPELL);
    }

    private void resetBlindCooldown() {
        this.ticksUntilNextBlind = adjustedTickDelay(blindDelay);
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr) {
        return pDistToEnemySqr/2 <= this.getAttackReachSqr(pEnemy);
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(attackDelay);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }
    protected boolean isTimeToBlind()
    {
        return this.ticksUntilNextBlind <= 0;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }


    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        Random rnd = new Random();
        if (pEnemy.getHealth()>6 && rnd.nextFloat()>0.6)
            pEnemy.addEffect(new MobEffectInstance(MobEffects.HARM,10));
        if(rnd.nextFloat()>0.8)
            pEnemy.addEffect(new MobEffectInstance(MobEffects.WITHER, 80));
        else {
            pEnemy.addEffect(new MobEffectInstance(MobEffects.POISON, 80));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
        if(shouldCountTillNextBlind) {
            this.ticksUntilNextBlind = Math.max(this.ticksUntilNextBlind - 1, 0);
        }
    }


    @Override
    public void stop() {
        witch.setAttacking(false);
        super.stop();
    }
}

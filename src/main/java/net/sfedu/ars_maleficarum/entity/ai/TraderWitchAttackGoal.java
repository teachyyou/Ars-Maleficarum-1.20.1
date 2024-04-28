package net.sfedu.ars_maleficarum.entity.ai;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.sfedu.ars_maleficarum.entity.custom.TraderWitchEntity;
import net.sfedu.ars_maleficarum.item.ModItems;

public class TraderWitchAttackGoal extends MeleeAttackGoal {

    private final TraderWitchEntity witch;
    private int attackDelay = 40;
    private int castDelay = 40;
    private int ticksUntilnextattack = 40;
    private int ticksUntilnextBlind = 300;
    private int ticksUntilnextcast = 200;
    private boolean shouldCountTillNextAttack = false;
    private boolean shouldCountTillNextCast = false;
    private boolean shouldCountTillNextBlind = false;
    public TraderWitchAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        witch = (TraderWitchEntity) pMob;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        Player enemy = (Player) pEnemy;
        if(isEnemyWithinAttackDistance(pEnemy,pDistToEnemySqr))
        {
            System.out.println("Attack");
            shouldCountTillNextAttack = true;
            shouldCountTillNextCast = true;
            shouldCountTillNextBlind = true;
            if(ticksUntilnextattack<=0)
            {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
            else {
                resetAttackCooldown();
                shouldCountTillNextAttack = false;
            }
            if(ticksUntilnextcast<=0)
            {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performCast(pEnemy);
            }
            else {
                resetCastCooldown();
                shouldCountTillNextCast = false;
            }
            if(ticksUntilnextBlind<=0)
            {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performBlind(pEnemy);
            }
            else {
                resetBlindCooldown();
                shouldCountTillNextBlind = false;
            }
        }
    }

    private void resetBlindCooldown() {
        ticksUntilnextBlind = 300;
    }

    private void performBlind(LivingEntity pEnemy) {
        pEnemy.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,80));
        pEnemy.addEffect(new MobEffectInstance(MobEffects.DARKNESS,80));
        pEnemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,80));
    }

    private void performCast(LivingEntity pEnemy) {
        if(witch.getHealth()<25D)
        {
            witch.addEffect(new MobEffectInstance(MobEffects.REGENERATION,200));
            witch.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,45));
            double d0 = witch.getX() + (pEnemy.getRandomX(1) - 0.5D) * 64.0D;
            double d1 = witch.getY() + (double)(pEnemy.getRandomY() - 32);
            double d2 = witch.getZ() + (pEnemy.getRandomZ(1) - 0.5D) * 64.0D;
            witch.randomTeleport(d0,d1,d2,true);
        }

    }

    protected void performAttack(LivingEntity pEnemy)
    {
        if (pEnemy.getHealth()>6)
            pEnemy.addEffect(new MobEffectInstance(MobEffects.HARM,20));
        pEnemy.addEffect(new MobEffectInstance(MobEffects.POISON, 80));
    }
    protected void resetAttackCooldown() {
        this.ticksUntilnextattack = this.adjustedTickDelay(attackDelay);
    }

    protected void resetCastCooldown() {
        this.ticksUntilnextcast = this.adjustedTickDelay(castDelay);
    }
    @Override
    public void start() {
        super.start();
        attackDelay = 40;
        castDelay = 40;
        ticksUntilnextattack = 40;
        ticksUntilnextcast = 200;
        ticksUntilnextBlind = 300;
    }
    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr) {
        return pDistToEnemySqr <= this.getAttackReachSqr(pEnemy);
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack) {
            this.ticksUntilnextattack = Math.max(this.ticksUntilnextattack - 1, 0);
        }
        if(shouldCountTillNextCast) {
            this.ticksUntilnextcast = Math.max(this.ticksUntilnextcast - 1, 0);
        }
        if(shouldCountTillNextBlind) {
            this.ticksUntilnextBlind = Math.max(this.ticksUntilnextBlind - 1, 0);
        }
    }
}

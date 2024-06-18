package net.sfedu.ars_maleficarum.entity.ai;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.sfedu.ars_maleficarum.entity.custom.GluttonyDemonEntity;
import org.jetbrains.annotations.NotNull;

public class GluttonyAttackGoal extends MeleeAttackGoal {
    private final GluttonyDemonEntity entity;
    private int attackDelay = 20;
    private int ticksUntilNextAttack = 45;
    private boolean shouldCountTillNextAttack = false;

    public GluttonyAttackGoal(PathfinderMob mob, double speedModifier, boolean followTargetEvenIfNotSeen) {
        super(mob, speedModifier, followTargetEvenIfNotSeen);
        this.entity = (GluttonyDemonEntity) mob;
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 20;
        ticksUntilNextAttack = 45;
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity enemy, double distToEnemySqr) {
        if (isEnemyWithinAttackDistance(enemy, distToEnemySqr)) {
            shouldCountTillNextAttack = true;

            if (isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }
            if (isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                performAttack(enemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity enemy, double distToEnemySqr) {
        return distToEnemySqr - 10 <= this.getAttackReachSqr(enemy);
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(attackDelay);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected void performAttack(LivingEntity enemy) {
        this.resetAttackCooldown();
        enemy.knockback(10D, 5D, 5D);
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(enemy);
        float currentHealth = entity.getHealth();
        float healthToAdd = 3F;
        entity.setHealth(Math.min(currentHealth + healthToAdd, entity.getMaxHealth()));
    }

    @Override
    public void tick() {
        super.tick();
        if (shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}

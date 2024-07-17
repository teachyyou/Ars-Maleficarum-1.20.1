package net.sfedu.ars_maleficarum.entity.ai;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.sfedu.ars_maleficarum.entity.custom.SwampDrownedEntity;
import net.sfedu.ars_maleficarum.entity.variant.SwampDrownedVariants;

import java.util.Random;

public class SwampDrownedAttackGoal extends MeleeAttackGoal {
    private final SwampDrownedEntity swamp_drowned;
    private int attack_delay = 0;
    private int time_under_water = 1200;
    private boolean shouldCountTillNextAttack = false;
    private boolean shouldCountTimeUnderWater = false;

    public SwampDrownedAttackGoal(SwampDrownedEntity pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        swamp_drowned = pMob;
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity enemy, double distToEnemySqr) {
        return distToEnemySqr - 10 <= this.getAttackReachSqr(enemy);
    }
    @Override
    public void start() {
        super.start();
        attack_delay = 0;
        time_under_water = 1200;
        shouldCountTillNextAttack = false;
        shouldCountTimeUnderWater = false;
    }
    private boolean isTimeToNextAttack(){return attack_delay<=0;}
    private boolean isTimeToGoUp(){return time_under_water<=0;}

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if(pEnemy instanceof Player && isEnemyWithinAttackDistance(pEnemy,pDistToEnemySqr) && !isTimeToGoUp() && isTimeToNextAttack())
        {
            this.swamp_drowned.setVariant(SwampDrownedVariants.EVIL);
            this.swamp_drowned.setAttacking(true);
            this.swamp_drowned.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
            this.swamp_drowned.getNavigation().moveTo(swamp_drowned.getX(),swamp_drowned.getY()-8,swamp_drowned.getZ(),4D);
            pEnemy.teleportTo(swamp_drowned.getX(), swamp_drowned.getY(),swamp_drowned.getZ());
            pEnemy.addEffect(new MobEffectInstance(MobEffects.POISON,80));
            Random rnd = new Random();
            if(rnd.nextInt(4)==0)
                this.swamp_drowned.doHurtTarget(pEnemy);
            shouldCountTimeUnderWater = true;
            if(swamp_drowned.getMaxHealth() - swamp_drowned.getHealth() > 50F)
            {
                swamp_drowned.setAttacking(false);
                shouldCountTimeUnderWater = false;
                time_under_water = 1200;
                attack_delay = 200;
                swamp_drowned.setHealth(swamp_drowned.getHealth()+20F);
                shouldCountTillNextAttack = true;
            }
        }
        else if(isTimeToGoUp())
        {
            shouldCountTimeUnderWater = false;
            time_under_water = 1200;
            shouldCountTillNextAttack = true;
            swamp_drowned.setAttacking(false);
            swamp_drowned.setHealth(swamp_drowned.getMaxHealth());
            attack_delay = 200;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack)
            attack_delay = Math.max(attack_delay-1,0);
        if(shouldCountTimeUnderWater)
            time_under_water = Math.max(time_under_water-1,0);
    }
}

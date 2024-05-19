package net.sfedu.ars_maleficarum.AI;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class AvoidBlockGoal<T extends Block> extends Goal {
    protected final PathfinderMob mob;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    @Nullable
    protected T toAvoid;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;
    /** Class of entity this behavior seeks to avoid */
    protected final Class<T> avoidClass;
    protected final Predicate<LivingEntity> avoidPredicate;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    private final TargetingConditions avoidEntityTargeting;

    /**
     * Goal that helps mobs avoid mobs of a specific class
     */
    public AvoidBlockGoal(PathfinderMob pMob, Class<T> pEntityClassToAvoid, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier) {
        this(pMob, pEntityClassToAvoid, (p_25052_) -> {
            return true;
        }, pMaxDistance, pWalkSpeedModifier, pSprintSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    /**
     * Goal that helps mobs avoid mobs of a specific class
     */
    public AvoidBlockGoal(PathfinderMob pMob, Class<T> pEntityClassToAvoid, Predicate<LivingEntity> pAvoidPredicate, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier, Predicate<LivingEntity> pPredicateOnAvoidEntity) {
        this.mob = pMob;
        this.avoidClass = pEntityClassToAvoid;
        this.avoidPredicate = pAvoidPredicate;
        this.maxDist = pMaxDistance;
        this.walkSpeedModifier = pWalkSpeedModifier;
        this.sprintSpeedModifier = pSprintSpeedModifier;
        this.predicateOnAvoidEntity = pPredicateOnAvoidEntity;
        this.pathNav = pMob.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.avoidEntityTargeting = TargetingConditions.forCombat().range((double)pMaxDistance).selector(pPredicateOnAvoidEntity.and(pAvoidPredicate));
    }


    public boolean canUse() {
        for (int i = -(int)maxDist; i < maxDist; i++) {
            for (int j=-(int)maxDist;j<maxDist;j++) {
                for (int k=-(int)maxDist; k<maxDist;k++) {
                    int x = (int)mob.position().x+i;
                    int y = (int)mob.position().y+j;
                    int z = (int)mob.position().z+k;
                    if (this.avoidClass.isInstance(mob.level().getBlockState(new BlockPos(x,y,z)).getBlock())) {
                        Vec3 avoidPos = new Vec3(x,y,z);
                        Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, avoidPos);
                        if (vec3 == null) return false;
                        else if (distanceToSqr(x,y,z,vec3.x, vec3.y, vec3.z) < distanceToSqr(x,y,z,(int)mob.position().x,(int)mob.position().y,(int)mob.position().z)) {
                            return false;
                        }
                        this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                        return this.path != null;
                    }
                }
            }
        }
        return false;

    }

    public double distanceToSqr(double qX, double qY, double qZ, double pX, double pY, double pZ) {
        double d0 = qX - pX;
        double d1 = qY - pY;
        double d2 = qZ - pZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }


    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return !this.pathNav.isDone();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.pathNav.moveTo(this.path, this.walkSpeedModifier);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.toAvoid = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.mob.distanceToSqr(this.mob.position().x,this.mob.position().y,this.mob.position().z) < 49.0D) {
            this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        } else {
            this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }

    }
}
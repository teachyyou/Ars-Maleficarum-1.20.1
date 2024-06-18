package net.sfedu.ars_maleficarum.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class RunFromPlayerGoal extends Goal {
    protected final PathfinderMob mob;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;

    public RunFromPlayerGoal(PathfinderMob mob, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier) {
        this.mob = mob;
        this.maxDist = maxDistance;
        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
        this.pathNav = mob.getNavigation();
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        Player nearestPlayer = this.mob.level().getNearestPlayer(this.mob, this.maxDist);
        Vec3 avoidPos, vec3;
        if (nearestPlayer == null) {
            return false;
        }
        avoidPos = nearestPlayer.getPosition(0);
        vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, avoidPos);
        if (vec3 == null) {
            return false;
        } else if (distanceToSqr(avoidPos.x, avoidPos.y, avoidPos.z, vec3.x, vec3.y, vec3.z) < distanceToSqr(avoidPos.x, avoidPos.y, avoidPos.z, (int) mob.position().x, (int) mob.position().y, (int) mob.position().z)) {
            return false;
        }
        this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
        return this.path != null;
    }

    public double distanceToSqr(double qX, double qY, double qZ, double pX, double pY, double pZ) {
        double d0 = qX - pX;
        double d1 = qY - pY;
        double d2 = qZ - pZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public boolean canContinueToUse() {
        return !this.pathNav.isDone();
    }

    public void start() {
        this.pathNav.moveTo(this.path, this.walkSpeedModifier);
    }

    public void tick() {
        if (this.mob.distanceToSqr(this.mob.position().x, this.mob.position().y, this.mob.position().z) < 49.0D) {
            this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        } else {
            this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }
    }
}

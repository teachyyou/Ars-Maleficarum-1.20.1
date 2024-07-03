package net.sfedu.ars_maleficarum.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.phys.Vec3;
import net.sfedu.ars_maleficarum.entity.custom.SwampDrownedEntity;

import javax.annotation.Nullable;

public class SwampDrownedSwimGoal extends RandomSwimmingGoal {

    public SwampDrownedSwimGoal(SwampDrownedEntity pEntity, double pSpeedModifier, int pInterval) {
        super(pEntity, pSpeedModifier, pInterval);
    }
    @Nullable
    protected Vec3 getPosition() {
        return BehaviorUtils.getRandomSwimmablePos(this.mob, 10, 1);
    }
}

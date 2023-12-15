package net.sfedu.ars_maleficarum.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;

public class MandrakePanicGoal extends PanicGoal {
    private final MandrakeEntity entity;

    public MandrakePanicGoal(PathfinderMob pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
        entity = ((MandrakeEntity) pMob);
    }

    @Override
    protected boolean shouldPanic() {
        return entity.isAlive() || super.shouldPanic();
    }
}

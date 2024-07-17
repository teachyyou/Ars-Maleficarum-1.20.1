package net.sfedu.ars_maleficarum.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import net.sfedu.ars_maleficarum.entity.custom.SwampDrownedEntity;

public class SwampDrownedSwimGoal extends Goal {
    private final SwampDrownedEntity swamp_drowned;

    private final int seaLevel;

    private int ticksUntilNextChangePosition = 120;
    private double wantedX;
    private double wantedZ;
    private int timeUnderWater = 120;
    private boolean isGoUp = false;

    public SwampDrownedSwimGoal(SwampDrownedEntity pSwampDrowned, int pSeaLevel)
    {
        swamp_drowned = pSwampDrowned;
        seaLevel = pSeaLevel;
    }
    public boolean canUse()
    {
        return this.swamp_drowned.isInWater();
    }
    public void tick() {
            if(swamp_drowned.isUnderProjectileAttack() && !swamp_drowned.isAttacking())
            {
                if(swamp_drowned.getY()>seaLevel-8 && !isGoUp)
                {
                    swamp_drowned.getNavigation().moveTo(swamp_drowned.getX(), seaLevel-8, swamp_drowned.getZ(),3D);
                }
                this.timeUnderWater = Math.max(this.timeUnderWater - 1, 0);
                if(isTimeToGoUp())
                {
                    isGoUp = true;
                    swamp_drowned.moveTo(swamp_drowned.getX(), (double)seaLevel, swamp_drowned.getZ());
                    if(swamp_drowned.getY() == seaLevel)
                    {
                        swamp_drowned.setIsUnderProjectileAttack(false);
                        isGoUp = false;
                        timeUnderWater = 120;
                    }
                }
            }
            else if(canUse() && !swamp_drowned.isAttacking())
            {
                this.swamp_drowned.moveTo(swamp_drowned.getX(), 63D, swamp_drowned.getZ());
                this.ticksUntilNextChangePosition = Math.max(this.ticksUntilNextChangePosition - 1, 0);
                if(isTimeToChangePosition())
                {
                    Vec3 vec3 = DefaultRandomPos.getPosTowards(this.swamp_drowned, 1, 1, new Vec3(this.swamp_drowned.getX(), (double)(this.seaLevel), this.swamp_drowned.getZ()), (double)((float)Math.PI / 2F));
                    assert vec3 != null;
                    wantedX = vec3.x;
                    wantedZ = vec3.z;
                    this.swamp_drowned.getNavigation().moveTo(swamp_drowned.getX(), 63D, swamp_drowned.getZ(), 1.3D);
                    ticksUntilNextChangePosition = 120;
                }
            }
    }

    public void start()
    {
        wantedX = swamp_drowned.getX();
        wantedZ = swamp_drowned.getZ();
        this.swamp_drowned.getNavigation().moveTo(swamp_drowned.getX(), 63D, swamp_drowned.getZ(), 1.3D);
        ticksUntilNextChangePosition = 120;
        timeUnderWater = 120;
        isGoUp = false;
    }
    protected boolean isTimeToChangePosition() {return this.ticksUntilNextChangePosition <=0;}
    private boolean isTimeToGoUp() {return timeUnderWater <= 0;}
}

package net.sfedu.ars_maleficarum.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

public class MandrakeEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int getAnimationTimeOut = 0;
    public MandrakeEntity(EntityType<? extends  Animal> pEntityType, Level pLevel){
        super(pEntityType,pLevel);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1,new WaterAvoidingRandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(2,new PanicGoal(this,0.40D));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH,20D).add(Attributes.MOVEMENT_SPEED,0.20D)
     .add(Attributes.FOLLOW_RANGE, 24D);
    }

    private void setupAnimationStates() {
        if (this.getAnimationTimeOut <= 0) {
            this.getAnimationTimeOut = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.getAnimationTimeOut;
        }
    }

    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.MANDRAKE.get().create(pLevel);
    }
}

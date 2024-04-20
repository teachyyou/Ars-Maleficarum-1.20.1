package net.sfedu.ars_maleficarum.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.ai.RunFromPlayerGoal;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

import static net.sfedu.ars_maleficarum.sound.ModSounds.MANDRAKE_SCREAM;

public class MandrakeEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    public boolean is_spawned = false;
    private int getAnimationTimeOut = 0;
    public static final Predicate<Entity> CAN_AI_TARGET = (entity) ->
    {
        return !(entity instanceof Player) || !entity.isSpectator() && !((Player)entity).isCreative();
    };
    public MandrakeEntity(EntityType<? extends  Animal> pEntityType, Level pLevel){
        super(pEntityType,pLevel);

    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3,new WaterAvoidingRandomStrollGoal(this,0.3D));
        this.goalSelector.addGoal(1, new RunFromPlayerGoal(this,20,0.7D,0.4D));
    }
    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH,10D).add(Attributes.MOVEMENT_SPEED,1D)
     .add(Attributes.FOLLOW_RANGE, 40D);
    }
    protected void customServerAiStep() {
        super.customServerAiStep();
        MobEffectInstance mobeffectinstance;
        if(!is_spawned){
            mobeffectinstance = new MobEffectInstance(MobEffects.CONFUSION, 400, 2);
            is_spawned = true;
            MobEffectUtil.addEffectToPlayersAround((ServerLevel)this.level(), this, this.position(), 20.0D, mobeffectinstance, 1200);

        }
        else if ((this.tickCount + this.getId()) % 300 == 0) {
            mobeffectinstance = new MobEffectInstance(MobEffects.CONFUSION, 400, 2);
            MobEffectUtil.addEffectToPlayersAround((ServerLevel)this.level(), this, this.position(), 20.0D, mobeffectinstance, 1200);

        }

    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MANDRAKE_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MANDRAKE_SCREAM.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 60;
    }

    private void setupAnimationStates() {

        if (this.getAnimationTimeOut <= 0) {
            this.getAnimationTimeOut = 60;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.getAnimationTimeOut;
        }
    }

    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 25.0F, 1.0F);
        } else {
            System.out.println("ELSE");
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

    public void setAttacking(boolean b) {

    }
}

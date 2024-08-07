package net.sfedu.ars_maleficarum.entity.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.entity.ai.GluttonyAttackGoal;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class GluttonyDemonEntity extends MortalSinDemonEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(Component.translatable("Gluttony_Demon_Bar"), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_20)).setDarkenScreen(true);
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(GluttonyDemonEntity.class, EntityDataSerializers.BOOLEAN);

    public GluttonyDemonEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60), this);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3,new WaterAvoidingRandomStrollGoal(this,0.3D));
        this.goalSelector.addGoal(1,new GluttonyAttackGoal(this,0.5D,true));
        this.goalSelector.addGoal(1,new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH,100D).add(Attributes.MOVEMENT_SPEED,0.7D)
                .add(Attributes.FOLLOW_RANGE, 40D).add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.ATTACK_DAMAGE, 14f).add(Attributes.ARMOR,50D);
    }

    @Override
    public int getExperienceReward() {
        return 50;
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 37; // Length in ticks of your animation
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

    @Override
    protected void dropExperience() {
        super.dropExperience();
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

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.GLUTTONY_DEMON_LAUGH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 100;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.IS_FIRE))
            return false;
        if(pSource.is(DamageTypeTags.IS_PROJECTILE))
            pAmount = pAmount/2;
        return super.hurt(pSource, pAmount);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean doHurtTarget(Entity pEntity) {
        if(!super.doHurtTarget(pEntity))
            return false;
        else {
            if (pEntity instanceof LivingEntity) {
                ((LivingEntity)pEntity).addEffect(new MobEffectInstance(MobEffects.HUNGER, 400), this);
            }
            return true;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.GLUTTONY_DEMON_DIED.get();
    }

    @Override
    @ParametersAreNonnullByDefault
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.GLUTTONY_DEMON_HURT.get();
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        //this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void startSeenByPlayer(ServerPlayer pServerPlayer) {
        super.startSeenByPlayer(pServerPlayer);
        this.bossEvent.addPlayer(pServerPlayer);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void stopSeenByPlayer(ServerPlayer pServerPlayer) {
        super.stopSeenByPlayer(pServerPlayer);
        this.bossEvent.removePlayer(pServerPlayer);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        bossEvent.setProgress(getHealth()/getMaxHealth());
    }
}
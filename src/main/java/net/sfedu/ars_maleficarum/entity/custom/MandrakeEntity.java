package net.sfedu.ars_maleficarum.entity.custom;

import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.entity.OcelotRenderer;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.ai.MandrakePanicGoal;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MandrakeEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    public boolean is_spawned = false;
    private int getAnimationTimeOut = 0;
    public MandrakeEntity(EntityType<? extends  Animal> pEntityType, Level pLevel){
        super(pEntityType,pLevel);

    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1,new NearestAttackableTargetGoal<>(this, Player.class,true));
        this.goalSelector.addGoal(3,new WaterAvoidingRandomStrollGoal(this,0.3D));
        this.goalSelector.addGoal(1,new MandrakePanicGoal(this,1D));
        //this.goalSelector.addGoal(3, new Goal);
    }
    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH,20D).add(Attributes.MOVEMENT_SPEED,0.3D)
     .add(Attributes.FOLLOW_RANGE, 40D);
    }
    protected void customServerAiStep() {
        super.customServerAiStep();
        if ((this.tickCount + this.getId()) % 300 == 0) {
            MobEffectInstance mobeffectinstance = new MobEffectInstance(MobEffects.CONFUSION, 400, 2);
            //is_spawned = true;
            List<ServerPlayer> list = MobEffectUtil.addEffectToPlayersAround((ServerLevel)this.level(), this, this.position(), 20.0D, mobeffectinstance, 1200);
            list.forEach((p_289459_) -> {
                p_289459_.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, this.isSilent() ? 0.0F : 1.0F));
            });
        }
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

        this.walkAnimation.update(f, 0.6F);
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

package net.sfedu.ars_maleficarum.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import org.jetbrains.annotations.NotNull;

public class FireEssenceEntity extends Projectile {

    private static final EntityDataAccessor<Boolean> HIT =
            SynchedEntityData.defineId(FireEssenceEntity.class, EntityDataSerializers.BOOLEAN);
    private int counter = 0;
    public FireEssenceEntity(EntityType<? extends Projectile> pEntityType, Level plevel)
    {
        super(pEntityType,plevel);
    }
    public FireEssenceEntity(Level plevel, Player player)
    {
        super(ModEntities.FIRE_ESSENCE.get(),plevel);
        setOwner(player);
        BlockPos pPose = player.blockPosition();
        double d0 = (double)pPose.getX() + 0.5D;
        double d1 = (double)pPose.getY() + 1.75D;
        double d2 = (double)pPose.getZ() + 0.5D;
        this.moveTo(d0, d1, d2, this.getYRot(), this.getXRot()+90.0F);
    }

    @Override
    @NotNull
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    @Override
    public void tick() {
        super.tick();
        if(this.entityData.get(HIT)) {
            if(this.tickCount >= counter) {
                this.discard();
            }
        }

        if (this.tickCount >= 300) {
            this.remove(RemovalReason.DISCARDED);
        }

        Vec3 vec3 = this.getDeltaMovement();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult))
            this.onHit(hitresult);

        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        double d5 = vec3.x;
        double d6 = vec3.y;
        double d7 = vec3.z;

        for(int i = 1; i < 10; ++i) {
            this.level().addParticle(ParticleTypes.FLAME, d0-(d5*2), d1-(d6*2), d2-(d7*2),
                    -d5, -d6 - 0.1D, -d7);
            this.level().addParticle(ParticleTypes.FLAME, d0-(d5*2), d1-(d6*2)+0.3, d2-(d7*2)+0.3,
                    -d5, -d6 - 0.1D, -d7);
            this.level().addParticle(ParticleTypes.FLAME, d0-(d5*2), d1-(d6*2)+0.3, d2-(d7*2)-0.3,
                    -d5, -d6 - 0.1D, -d7);
            this.level().addParticle(ParticleTypes.FLAME, d0-(d5*2), d1-(d6*2)-0.3, d2-(d7*2)-0.3,
                    -d5, -d6 - 0.1D, -d7);
            this.level().addParticle(ParticleTypes.FLAME, d0-(d5*2), d1-(d6*2)-0.3, d2-(d7*2)+0.3,
                    -d5, -d6 - 0.1D, -d7);
        }

        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isInWaterOrBubble()) {
            this.discard();
        } else {
            this.setDeltaMovement(vec3.scale(0.99F));
            this.setPos(d0, d1, d2);
        }
    }
    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        for(int x = 0; x < 25; ++x) {
            for(int y = 0; y < 25; ++y) {
                this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(),
                        Math.cos(x*20) * 0.15d, Math.cos(y*20) * 0.15d, Math.sin(x*20) * 0.15d);
            }
        }

        if(this.level().isClientSide()) {
            return;
        }

        if(hitResult.getType() == HitResult.Type.ENTITY && hitResult instanceof EntityHitResult entityHitResult) {
            Entity hit = entityHitResult.getEntity();
            Entity owner = this.getOwner();
            if(owner != hit) {
                this.entityData.set(HIT, true);
                counter = this.tickCount + 5;
            }
        } else {
            this.entityData.set(HIT, true);
            counter = this.tickCount + 5;
        }
    }
    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if(!this.level().isClientSide()){
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 15, 0.2D, 0.2D, 0.2D, 0.2D);
            this.level().broadcastEntityEvent(this,((byte) 3));
            this.level().setBlock(blockPosition(), ModBlocks.CUSTOM_FIRE.get().defaultBlockState(),3);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity target = pResult.getEntity();
        LivingEntity owner = this.getOwner() instanceof LivingEntity ? (LivingEntity)this.getOwner() : null;
        boolean flag = target.hurt(this.damageSources().mobProjectile(this, owner), 5.0F);
        if (flag && owner != null) {
            this.doEnchantDamageEffects(owner, target);
            if (target instanceof LivingEntity livingTarget) {
                livingTarget.setRemainingFireTicks(200);
            }
        }
    }


    @Override
    public boolean hurt(DamageSource source, float amount) {
        super.hurt(source, amount);
        if (!this.level().isClientSide() && source.getEntity() != null) {
            Vec3 vec3d = source.getEntity().getLookAngle();
            this.shoot(vec3d.x(), vec3d.y(), vec3d.z(), 1.5F, 0.1F);
            if (source.getDirectEntity() instanceof LivingEntity) {
                this.setOwner(source.getDirectEntity());
            }
            return true;
        }
        return false;
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(HIT, false);
    }
}

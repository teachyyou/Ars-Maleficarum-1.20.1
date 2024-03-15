package net.sfedu.ars_maleficarum.entity.custom;

import com.google.common.base.MoreObjects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.item.ModItems;

public class PoisonousEssenceEntity extends Projectile {
    private static final EntityDataAccessor<Boolean> HIT =
            SynchedEntityData.defineId(PoisonousEssenceEntity.class, EntityDataSerializers.BOOLEAN);
    private int counter = 0;
    public PoisonousEssenceEntity(EntityType<? extends Projectile> pEntityType, Level plevel)
    {
        super(pEntityType,plevel);
    }
    public PoisonousEssenceEntity(Level plevel, Player player)
    {
        super(ModEntities.POISONOUS_ESSENCE.get(),plevel);
        setOwner(player);
        BlockPos pPose = player.blockPosition();
        double d0 = (double)pPose.getX() + 0.5D;
        double d1 = (double)pPose.getY() + 1.75D;
        double d2 = (double)pPose.getZ() + 0.5D;
        this.moveTo(d0, d1, d2, this.getYRot(), this.getXRot());
    }

    @Override
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

        for(int i = 1; i < 5; ++i) {
            this.level().addParticle(ParticleTypes.ITEM_SLIME, d0-(d5*2), d1-(d6*2), d2-(d7*2),
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

    private void makeTrail() {
        for (int i = 0; i < 5; i++) {
            double dx = this.getX() + 0.5D * (this.random.nextDouble() - this.random.nextDouble());
            double dy = this.getY() + 0.5D * (this.random.nextDouble() - this.random.nextDouble());
            double dz = this.getZ() + 0.5D * (this.random.nextDouble() - this.random.nextDouble());

            double s1 = ((this.random.nextFloat() * 0.5F) + 0.5F) * 0.17F;  // color
            double s2 = ((this.random.nextFloat() * 0.5F) + 0.5F) * 0.80F;  // color
            double s3 = ((this.random.nextFloat() * 0.5F) + 0.5F) * 0.69F;  // color

            this.level().addParticle(ParticleTypes.ENTITY_EFFECT, dx, dy, dz, s1, s2, s3);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if(!this.level().isClientSide()){
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.ITEM_SLIME, this.getX(), this.getY(), this.getZ(), 6, 0.2D, 0.2D, 0.2D, 0.2D);
            this.level().broadcastEntityEvent(this,((byte) 3));
            //this.level().setBlock(blockPosition(), Blocks.GOLD_BLOCK.defaultBlockState(),3);
            this.discard();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particle = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.ENDER_PEARL));
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(particle, false, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.05D);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }
    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        for(int x = 0; x < 18; ++x) {
            for(int y = 0; y < 18; ++y) {
                this.level().addParticle(ParticleTypes.ITEM_SLIME, this.getX(), this.getY(), this.getZ(),
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
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        Entity entity1 = this.getOwner();
        LivingEntity livingentity = entity1 instanceof LivingEntity ? (LivingEntity)entity1 : null;
        boolean flag = entity.hurt(this.damageSources().mobProjectile(this, livingentity), 1.0F);
        if (flag) {
            this.doEnchantDamageEffects(livingentity, entity);
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;
                livingentity1.addEffect(new MobEffectInstance(MobEffects.POISON, 200), MoreObjects.firstNonNull(entity1, this));
            }
        }
    }

    protected void defineSynchedData() {
        this.entityData.define(HIT, false);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        super.hurt(source, amount);

        if (!this.level().isClientSide() && source.getEntity() != null) {
            Vec3 vec3d = source.getEntity().getLookAngle();
            // reflect faster and more accurately
            this.shoot(vec3d.x(), vec3d.y(), vec3d.z(), 1.5F, 0.1F);

            if (source.getDirectEntity() instanceof LivingEntity) {
                this.setOwner(source.getDirectEntity());
            }
            return true;
        }

        return false;
    }
}

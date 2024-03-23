package net.sfedu.ars_maleficarum.entity.custom;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class Throwable extends ThrowableItemProjectile {
    public Throwable(EntityType<? extends Throwable> type, Level worldIn) {
        super(type, worldIn);
    }
    public Throwable(EntityType<? extends Throwable> type, Level worldIn, double x, double y, double z) {
        super(type, x, y, z, worldIn);
    }

    public Throwable(EntityType<? extends Throwable> type, Level worldIn, LivingEntity throwerIn) {
        super(type, throwerIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void defineSynchedData() {

    }
    public void makeTrail(ParticleOptions particle, int amount) {
        this.makeTrail(particle, 0.0D, 0.0D, 0.0D, amount);
    }

    public void makeTrail(ParticleOptions particle, double r, double g, double b, int amount) {
        for (int i = 0; i < amount; i++) {
            double dx = this.getX() + 0.5 * (this.random.nextDouble() - this.random.nextDouble());
            double dy = this.getY() + 0.5 * (this.random.nextDouble() - this.random.nextDouble());
            double dz = this.getZ() + 0.5 * (this.random.nextDouble() - this.random.nextDouble());
            this.level().addParticle(particle, dx, dy, dz, r, g, b);
        }
    }
}

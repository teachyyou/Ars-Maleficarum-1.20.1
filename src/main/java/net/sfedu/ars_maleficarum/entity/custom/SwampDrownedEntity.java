package net.sfedu.ars_maleficarum.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.sfedu.ars_maleficarum.entity.ai.RunFromPlayerGoal;
import net.sfedu.ars_maleficarum.entity.ai.SwampDrownedAttackGoal;
import net.sfedu.ars_maleficarum.entity.ai.SwampDrownedSwimGoal;
import net.sfedu.ars_maleficarum.entity.variant.SwampDrownedVariants;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SwampDrownedEntity extends Monster {

    protected final WaterBoundPathNavigation waterNavigation;
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(SwampDrownedEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_UNDER_PROJECTILE_ATTACK =
            SynchedEntityData.defineId(SwampDrownedEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(SwampDrownedEntity.class, EntityDataSerializers.BOOLEAN);
    public SwampDrownedEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.waterNavigation = new WaterBoundPathNavigation(this, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH, 60D).add(Attributes.MOVEMENT_SPEED, 2D)
                .add(Attributes.FOLLOW_RANGE, 40D).add(Attributes.ATTACK_KNOCKBACK, 0.1D).add(Attributes.ATTACK_DAMAGE, 5D).add(Attributes.KNOCKBACK_RESISTANCE);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwampDrownedSwimGoal(this,this.level().getSeaLevel()));
        this.goalSelector.addGoal(1, new SwampDrownedAttackGoal(this, 0.8D, true));
        this.goalSelector.addGoal(2, new TryFindWaterGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F));
    }
    public static boolean checkSwampDrownedSpawnRules(EntityType<SwampDrownedEntity> pSwampDrowned, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) {
        if (!pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER)) {
            return false;
        } else {
            Holder<Biome> holder = pServerLevel.getBiome(pPos);
            boolean flag = pServerLevel.getDifficulty() != Difficulty.PEACEFUL && pServerLevel.getFluidState(pPos).is(FluidTags.WATER);
            if (holder.is(Tags.Biomes.IS_SWAMP) && flag) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public PathNavigation getNavigation() {
        return waterNavigation;
    }

    public void setIsUnderProjectileAttack(boolean attacking) {
        this.entityData.set(IS_UNDER_PROJECTILE_ATTACK, attacking);
    }
    public boolean isUnderProjectileAttack() {
        return this.entityData.get(IS_UNDER_PROJECTILE_ATTACK);
    }
    public void setAttacking(boolean isAttacking){
        this.entityData.set(ATTACKING, isAttacking);
    }
    public boolean isAttacking(){return this.entityData.get(ATTACKING);}
    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    @Override
    public boolean canBreatheUnderwater() {return true;}

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        this.setVariant(SwampDrownedVariants.EVIL);
        if(pSource.is(DamageTypeTags.IS_PROJECTILE))
        {
            this.setIsUnderProjectileAttack(true);
            pAmount = pAmount/2;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT,0);
        this.entityData.define(IS_UNDER_PROJECTILE_ATTACK, false);
        this.entityData.define(ATTACKING,false);
    }

    public SwampDrownedVariants getVariant()
    {
        return SwampDrownedVariants.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant()
    {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }
    public void setVariant(SwampDrownedVariants pVariant)
    {
        this.entityData.set(DATA_ID_TYPE_VARIANT, pVariant.getId() & 255);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SwampDrownedVariants variant = SwampDrownedVariants.PEACEFUL;
        this.setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(DATA_ID_TYPE_VARIANT, pCompound.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant",this.getTypeVariant());
    }
}

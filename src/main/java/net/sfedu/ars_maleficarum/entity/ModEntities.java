package net.sfedu.ars_maleficarum.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.entity.custom.*;


public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ArsMaleficarum.MOD_ID);

    public static final RegistryObject<EntityType<MandrakeEntity>> MANDRAKE =
            ENTITY_TYPES.register("mandrake", () -> EntityType.Builder.of(MandrakeEntity::new, MobCategory.CREATURE)
                    .sized(0.3f, 0.7f).build("mandrake"));

    public static final RegistryObject<EntityType<HermitWitchEntity>> HERMIT_WITCH =
            ENTITY_TYPES.register("hermit_witch", () -> EntityType.Builder.of(HermitWitchEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 2f).build("hermit_witch"));

    public static final RegistryObject<EntityType<PoisonousEssenceEntity>> POISONOUS_ESSENCE =
            ENTITY_TYPES.register("poisonous_essence", () -> EntityType.Builder.<PoisonousEssenceEntity>of(PoisonousEssenceEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("poisonous_essence"));
    public static final RegistryObject<EntityType<FireEssenceEntity>> FIRE_ESSENCE =
            ENTITY_TYPES.register("fire_essence", () -> EntityType.Builder.<FireEssenceEntity>of(FireEssenceEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("fire_essence"));
    public static final RegistryObject<EntityType<GluttonyDemonEntity>> GLUTTONY_DEMON =
            ENTITY_TYPES.register("gluttony_demon", () -> EntityType.Builder.of(GluttonyDemonEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 2f).build("gluttony_demon"));
    public static final RegistryObject<EntityType<SwampDrownedEntity>> SWAMP_DROWNED =
            ENTITY_TYPES.register("swamp_drowned", () -> EntityType.Builder.of(SwampDrownedEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1f).build("swamp_drowned"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

package net.sfedu.ars_maleficarum.ritual;

import net.minecraft.resources.ResourceLocation;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual.GreatRiteOfEmpowering;
import net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual.GreatRiteOfSwiftness;
import net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual.WeakRiteOfEmpowering;
import net.sfedu.ars_maleficarum.ritual.ApplyEffectsRitual.WeakRiteOfSwiftness;
import net.sfedu.ars_maleficarum.ritual.ImprisonmentRituals.RiteOfLargeDemonicImprisonment;
import net.sfedu.ars_maleficarum.ritual.ImprisonmentRituals.RiteOfMediumDemonicImprisonment;
import net.sfedu.ars_maleficarum.ritual.ImprisonmentRituals.RiteOfSmallDemonicImprisonment;
import net.sfedu.ars_maleficarum.ritual.RitesOfSummoning.RiteOfAbyssalFeast;
import net.sfedu.ars_maleficarum.ritual.craftingRituals.RiteOfForgottenNameAwakening;
import net.sfedu.ars_maleficarum.ritual.craftingRituals.RiteOfPoisonStaffCreation;
import net.sfedu.ars_maleficarum.ritual.craftingRituals.RiteOfPoisonStaffRepair;
import net.sfedu.ars_maleficarum.ritual.craftingRituals.RiteOfPoisonStaffRepairWithAliveLarva;
import net.sfedu.ars_maleficarum.ritual.craftingRituals.craftingRitualsThatRequiresDemon.RiteOfKramerTorchCreation;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;
import net.sfedu.ars_maleficarum.util.OrderedHashMap;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RitualTypes<T extends CircleRitual>{

    private static final Map<ResourceLocation, RitualType<?>> RITUAL_TYPES = new OrderedHashMap<>();

    public static final RitualType<RiteOfSmallDemonicImprisonment> SMALL_DEMONIC_IMPRISONMENT = register("small_demonic_imprisonment", RiteOfSmallDemonicImprisonment::new);
    public static final RitualType<RiteOfMediumDemonicImprisonment> MEDIUM_DEMONIC_IMPRISONMENT = register("medium_demonic_imprisonment", RiteOfMediumDemonicImprisonment::new);
    public static final RitualType<RiteOfLargeDemonicImprisonment> LARGE_DEMONIC_IMPRISONMENT = register("large_demonic_imprisonment", RiteOfLargeDemonicImprisonment::new);
    public static final RitualType<RisingSunRitual> RISING_SUN = register("rising_sun", RisingSunRitual::new);
    public static final RitualType<SettingSunRitual> SETTING_SUN = register("setting_sun", SettingSunRitual::new);
    public static final RitualType<RiteOfPoisonStaffCreation> POISON_STAFF_CREATION = register("poison_staff_creation", RiteOfPoisonStaffCreation::new);
    public static final RitualType<RiteOfPoisonStaffRepairWithAliveLarva> POISON_STAFF_REPAIR_LARVA = register("poison_staff_repair_larva", RiteOfPoisonStaffRepairWithAliveLarva::new);
    public static final RitualType<RiteOfPoisonStaffRepair> POISON_STAFF_REPAIR = register("poison_staff_repair", RiteOfPoisonStaffRepair::new);
    public static final RitualType<RiteOfGrassBlockCreation> GRASS_BLOCK_CREATION = register("grass_block_creation", RiteOfGrassBlockCreation::new);
    public static final RitualType<RiteOfMoonlight> RITE_OF_MOONLIGHT = register("rite_of_moonlight", RiteOfMoonlight::new);
    public static final RitualType<RiteOfForgottenNameAwakening> FORGOTTEN_NAME_AWAKENING = register("forgotten_name_awakening", RiteOfForgottenNameAwakening::new);
    public static final RitualType<RiteOfAbyssalFeast> ABYSSAL_FEAST = register("abyssal_feast", RiteOfAbyssalFeast::new);
    public static final RitualType<RiteOfKramerTorchCreation> KRAMER_TORCH_CREATION = register("kramer_torch_creation", RiteOfKramerTorchCreation::new);
    public static final RitualType<WeakRiteOfEmpowering> WEAK_EMPOWERING = register("weak_empowering", WeakRiteOfEmpowering::new);
    public static final RitualType<WeakRiteOfSwiftness> WEAK_SWIFTNESS = register("weak_swiftness", WeakRiteOfSwiftness::new);
    public static final RitualType<GreatRiteOfEmpowering> GREAT_EMPOWERING = register("great_empowering", GreatRiteOfEmpowering::new);
    public static final RitualType<GreatRiteOfSwiftness> GREAT_SWIFTNESS = register("great_swiftness", GreatRiteOfSwiftness::new);


    public static <T extends CircleRitual> RitualType<T> register(ResourceLocation id, RitualType.RitualFactory<T> factory) {
        RitualType<T> riteType = new RitualType<>(id, factory);
        RITUAL_TYPES.put(id, riteType);
        return riteType;
    }

    private static <T extends CircleRitual> RitualType<T> register(String id, RitualType.RitualFactory<T> factory) {
        return register(new ResourceLocation(ArsMaleficarum.MOD_ID, id), factory);
    }

    public static CircleRitual getDefaultByName(ResourceLocation id) {
        RitualType<?> type = RITUAL_TYPES.get(id);
        return type != null ? type.create() : null;
    }

    public static Collection<RitualType<?>> getEntries() {
        return RITUAL_TYPES.values();
    }

    public static RitualType<?> get(ResourceLocation id) {
        return RITUAL_TYPES.get(id);
    }
}
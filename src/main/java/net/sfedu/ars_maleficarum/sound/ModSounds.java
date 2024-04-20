package net.sfedu.ars_maleficarum.sound;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ArsMaleficarum.MOD_ID);

    public static final RegistryObject<SoundEvent> MANDRAKE_SCREAM = registerSoundEvents("mandrake_scream");
    public static final RegistryObject<SoundEvent> FIRE_STAFF_RADIUS_ATTACK = registerSoundEvents("fire_staff_radius_attack");
    public static final RegistryObject<SoundEvent> MUSHROOM_CUT = registerSoundEvents("mushroom_cut");
    public static final RegistryObject<SoundEvent> GLUTTONY_DEMON_LAUGH = registerSoundEvents("gluttony_demon_laugh");

    public static final RegistryObject<SoundEvent> GLUTTONY_DEMON_DIED = registerSoundEvents("gluttony_demon_died");
    public static final RegistryObject<SoundEvent> GLUTTONY_DEMON_SPAWN = registerSoundEvents("gluttony_demon_spawn");
    public static final RegistryObject<SoundEvent> GLUTTONY_DEMON_HURT = registerSoundEvents("gluttony_demon_hurt");

    public static final RegistryObject<SoundEvent> MANDRAKE_DEATH = registerSoundEvents("mandrake_death");
    public static final RegistryObject<SoundEvent> CAULDRON_ADD_LOGS = registerSoundEvents("cauldron_add_logs");
    public static final RegistryObject<SoundEvent> CHALK_USE = registerSoundEvents("chalk_use");
    public static final RegistryObject<SoundEvent> CAULDRON_BOIL = registerSoundEvents("cauldron_boil");
    public static final RegistryObject<SoundEvent> MYSTIC_WHISPERING = registerSoundEvents("mystic_whispering");
    public static final RegistryObject<SoundEvent> BRUSH_USE = registerSoundEvents("brush_use");
    public static final RegistryObject<SoundEvent> MANDRAKE_SPAWN = registerSoundEvents("mandrake_spawn");




    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(ArsMaleficarum.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
    public static void register(IEventBus eventBus) { SOUND_EVENTS.register(eventBus);}


}

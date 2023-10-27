package net.sfedu.ars_maleficarum.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ArsMaleficarum.MOD_ID);

    public static final RegistryObject<SoundEvent> CARBON_DETECTOR_SUCCESSFUL_SOUND = registerSoundEvents("carbon_detector_successful_sound");
    public static final RegistryObject<SoundEvent> VALUABLE_DETECTOR_SUCCESSFUL_SOUND = registerSoundEvents("valuable_detector_successful_sound");
    public static final RegistryObject<SoundEvent> METAL_DETECTOR_SUCCESSFUL_SOUND = registerSoundEvents("metal_detector_successful_sound");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ArsMaleficarum.MOD_ID, name)));
    }


    //Регистрация звуков нашего мода
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}

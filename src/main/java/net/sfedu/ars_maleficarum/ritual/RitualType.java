package net.sfedu.ars_maleficarum.ritual;

import net.minecraft.resources.ResourceLocation;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;

public class RitualType<T extends CircleRitual> {
    private final ResourceLocation id;
    private final RitualFactory<T> factory;

    public RitualType(ResourceLocation id, RitualFactory<T> factory) {
        this.id = id;
        this.factory = factory;
    }

    public ResourceLocation getId() {
        return id;
    }

    public T create() {
        return this.factory.create();
    }

    public interface RitualFactory<T extends CircleRitual> {
        T create();
    }
}

package net.sfedu.ars_maleficarum.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.world.decorator.custom.ModMushroomDecorator;

import java.util.function.Supplier;

public class ModTreeDecoratorTypes<P extends TreeDecorator> {

    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR =
            DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, ArsMaleficarum.MOD_ID);
    public static final RegistryObject<? extends TreeDecoratorType<? extends TreeDecorator>> SWAMP_ROTFIEND = register("swamp_rotfiend",
            ModMushroomDecorator.CODEC);


    private static RegistryObject<? extends TreeDecoratorType<? extends TreeDecorator>> register(String name, Codec<? extends TreeDecorator> codec) {
        return register(name, () -> new TreeDecoratorType<>(codec));
    }

    public static RegistryObject<? extends TreeDecoratorType<? extends TreeDecorator>> register(final String name, final Supplier<? extends TreeDecoratorType<? extends TreeDecorator>> sup) {
        return TREE_DECORATOR.register(name, sup);
    }

    public static void register(IEventBus eventBus) {
        TREE_DECORATOR.register(eventBus);
    }
    private static class RegistryHolder<V> implements Supplier<IForgeRegistry<V>>
    {
        private final ResourceKey<? extends Registry<V>> registryKey;
        private IForgeRegistry<V> registry = null;

        private RegistryHolder(ResourceKey<? extends Registry<V>> registryKey)
        {
            this.registryKey = registryKey;
        }

        @Override
        public IForgeRegistry<V> get()
        {
            // Keep looking up the registry until it's not null
            if (this.registry == null)
                this.registry = RegistryManager.ACTIVE.getRegistry(this.registryKey);

            return this.registry;
        }
    }
}

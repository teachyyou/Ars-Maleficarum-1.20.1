package net.sfedu.ars_maleficarum.world.decorator;

import com.google.common.collect.Maps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.Map;
import java.util.function.Supplier;

public class ModBuiltInRegistries extends BuiltInRegistries {
    private static final Map<ResourceLocation, Supplier<?>> LOADERS = Maps.newLinkedHashMap();
    private static final WritableRegistry<WritableRegistry<?>> WRITABLE_REGISTRY = new MappedRegistry<>(ResourceKey.createRegistryKey(ROOT_REGISTRY_NAME), Lifecycle.stable());
    private static <T> Registry<T> forge(ResourceKey<? extends Registry<T>> key, Lifecycle cycle, ModBuiltInRegistries.RegistryBootstrap<T> def) {
        return internalRegister(key, net.minecraftforge.registries.GameData.getWrapper(key, cycle), def, cycle);
    }
    private static <T, R extends WritableRegistry<T>> R internalRegister(ResourceKey<? extends Registry<T>> pKey, R pRegistry, ModBuiltInRegistries.RegistryBootstrap<T> pBootstrap, Lifecycle pLifecycle) {
        ResourceLocation resourcelocation = pKey.location();
        LOADERS.put(resourcelocation, () -> pBootstrap.run(pRegistry));
        WRITABLE_REGISTRY.register((ResourceKey)pKey, pRegistry, pLifecycle);
        return pRegistry;
    }
    private static <T> Registry<T> forge(ResourceKey<? extends Registry<T>> key, ModBuiltInRegistries.RegistryBootstrap<T> def) {
        return forge(key, Lifecycle.stable(), def);
    }
    @Deprecated
    public static final Registry<TreeDecoratorType<?>> MUSHROOM_DECORATOR_TYPE = forge(Registries.TREE_DECORATOR_TYPE, (p_259122_) -> ModTreeDecoratorTypes.SWAMP_ROTFIEND);
    @FunctionalInterface
    interface RegistryBootstrap<T> {
        T run(Registry<T> p_260128_);
    }
}

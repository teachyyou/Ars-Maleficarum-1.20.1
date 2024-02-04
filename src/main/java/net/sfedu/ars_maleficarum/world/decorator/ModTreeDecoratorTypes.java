package net.sfedu.ars_maleficarum.world.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.world.decorator.custom.ModMushroomDecorator;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModTreeDecoratorTypes<P extends TreeDecorator> extends TreeDecoratorType<P>{

    public static final TreeDecoratorType<ModMushroomDecorator> SWAMP_ROTFIEND = register("swamp_rotfiend",ModMushroomDecorator.CODEC);
    private final Codec<P> codec;

    @Deprecated
    private static <P extends TreeDecorator> TreeDecoratorType<P> register(String pKey, Codec<P> pCodec) {
        return Registry.register(ModBuiltInRegistries.MUSHROOM_DECORATOR_TYPE, pKey, new TreeDecoratorType<>(pCodec));
    }
    public ModTreeDecoratorTypes(Codec<P> pCodec) {
        super(pCodec);
        this.codec = pCodec;
    }
    public Codec<P> codec() {
        return this.codec;
    }
}

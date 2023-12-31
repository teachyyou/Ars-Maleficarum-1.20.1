package net.sfedu.ars_maleficarum.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ArsMaleficarum.MOD_ID);

    public static final RegistryObject<MenuType<OdourExtractorFurnaceMenu>> ODOUR_EXTRACTING_MENU =
            registerMenuType(OdourExtractorFurnaceMenu::new, "odour_extracting_menu");

    public static final RegistryObject<MenuType<InfusingAltarMenu>> INFUSING_ALTAR_MENU =
            registerMenuType(InfusingAltarMenu::new, "infusing_altar_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}


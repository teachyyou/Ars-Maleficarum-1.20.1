package net.sfedu.ars_maleficarum.item.client;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.sfedu.ars_maleficarum.item.ModItems;

import java.util.HashMap;
import java.util.Map;

public class CauldronColorsMap {

    public static final Map<Item, int[]> CAULDRON_COLORS_MAP = new HashMap<>();

    static {
        CAULDRON_COLORS_MAP.put(Items.DIRT, new int[] {20, -40, 30});
        CAULDRON_COLORS_MAP.put(ModItems.ROWAN_BARK.get(), new int[] {-20, 0, -10});
        CAULDRON_COLORS_MAP.put(ModItems.NAMELESS_CHARCOAL.get(), new int[] {0, 0, 30});
        CAULDRON_COLORS_MAP.put(ModItems.ROWAN_BERRIES.get(), new int[] {30, 0, 0});
        CAULDRON_COLORS_MAP.put(ModItems.PETRICHOR.get(), new int[] {0, 30, 0});
        CAULDRON_COLORS_MAP.put(ModItems.SALT.get(), new int[] {-30, -30, -30});
        CAULDRON_COLORS_MAP.put(Items.BONE_MEAL, new int[] {-255, -255, -255});
    }

    public static int[] get(Item item) {
        return CAULDRON_COLORS_MAP.get(item);
    }

    public static boolean hasColorFor(Item item) {
        return CAULDRON_COLORS_MAP.containsKey(item);
    }
}

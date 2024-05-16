package net.sfedu.ars_maleficarum.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.sfedu.ars_maleficarum.ArsMaleficarum;

//Класс кастомных тэгов
public class ModTags {

    //Тэги для блоков
    public static class Blocks {
        public  static final TagKey<Block> CARBON_DETECTOR_VALUABLES = tag("carbon_detector_valuables");
        public  static final TagKey<Block> CHALK_SYMBOLS = tag("chalk_symbols");
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(ArsMaleficarum.MOD_ID,name));
        }
    }

    //Тэги для предметов
    public static class Items {

        public static final TagKey<Item> SKULLS = tag("skulls");
        public static final TagKey<Item> ROWAN_WOOD = tag("rowan_wood");
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(ArsMaleficarum.MOD_ID,name));
        }
    }
}

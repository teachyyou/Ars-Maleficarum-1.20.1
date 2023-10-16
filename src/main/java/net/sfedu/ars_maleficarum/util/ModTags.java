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

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(ArsMaleficarum.MOD_ID,name));
        }
    }

    //Тэги для предметов
    public static class Items {
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(ArsMaleficarum.MOD_ID,name));
        }
    }
}

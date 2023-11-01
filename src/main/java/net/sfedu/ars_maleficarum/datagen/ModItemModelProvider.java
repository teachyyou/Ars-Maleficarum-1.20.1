package net.sfedu.ars_maleficarum.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArsMaleficarum.MOD_ID, existingFileHelper);
    }

    //Создание моделей для перечисленных предметов по шаблону simpleItem
    @Override
    protected void registerModels() {
        simpleItem(ModItems.SAGE_FLOWER);
        simpleItem(ModItems.SAGE_SEED);
        simpleItem(ModItems.SAGE_LEAF);
        simpleItem(ModItems.MARIGOLD_SEED);
        simpleItem(ModItems.MARIGOLD_FLOWER);
        simpleItem(ModItems.CURSED_GOLD);
        simpleItem(ModItems.SILVER_NUGGET);
        simpleItem(ModItems.SILVER);
        simpleItem(ModItems.CURSED_GOLD_NUGGET);
        simpleItem(ModItems.CARBON_DETECTOR);
        simpleItem(ModItems.METAL_DETECTOR);
        simpleItem(ModItems.VALUABLE_DETECTOR);

        simpleItem(ModItems.FLINT_KNIFE);

        saplingItem(ModBlocks.ROWAN_SAPLING);
        simpleItem(ModItems.ROWAN_BERRIES);
        simpleItem(ModItems.ROWAN_BARK);
        simpleItem(ModItems.STONE_PESTLE);
        simpleItem(ModItems.EMPTY_SEAL);
        simpleItem(ModItems.PERCEPTION_CORE);
        simpleItem(ModItems.SALT);
        simpleItem(ModItems.STONE_MORTAR);
        simpleItem(ModItems.STONE_MORTAR_AND_PESTLE);
        simpleItem(ModItems.WOODEN_MORTAR_AND_PESTLE);

        simpleItem(ModItems.BAT_WING);
    }

    //Генерация .json для простого предмета (как, например, цветок шалфея)
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ArsMaleficarum.MOD_ID,"item/"+item.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> block) {
        return withExistingParent(block.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ArsMaleficarum.MOD_ID,"block/"+block.getId().getPath()));
    }
}

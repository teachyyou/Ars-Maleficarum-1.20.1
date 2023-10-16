package net.sfedu.ars_maleficarum.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArsMaleficarum.MOD_ID, existingFileHelper);
    }

    //Создание моделей для перечисленных предметов по шаблону simpleItem
    @Override
    protected void registerModels() {
        //simpleItem(ModItems.MARIGOLD_FLOWER);
        //simpleItem(ModItems.SAGE_FLOWER);
        //simpleItem(ModItems.SAGE_SEED);
    }

    //Генерация .json для простого предмета (как, например, цветок шалфея)
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ArsMaleficarum.MOD_ID,"item/"+item.getId().getPath()));
    }
}

package net.sfedu.ars_maleficarum.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.item.ModItems;

import java.util.Objects;

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
        simpleItem(ModItems.GROUND_MARIGOLD_FLOWERS);
        simpleItem(ModItems.GROUND_SAGE_FLOWERS);

        simpleItem(ModItems.CURSED_GOLD);
        simpleItem(ModItems.SILVER_NUGGET);
        simpleItem(ModItems.SILVER);
        simpleItem(ModItems.CURSED_GOLD_NUGGET);
        simpleItem(ModItems.SILVER_CHUNK);
        simpleItem(ModItems.CURSED_GOLD_CHUNK);

        simpleItem(ModItems.FLINT_KNIFE);

        saplingItem(ModBlocks.ROWAN_SAPLING);
        saplingItem(ModBlocks.KRAMER_SAPLING);
        saplingItem(ModBlocks.NAMELESS_TREE_SAPLING);

        simpleItem(ModItems.ROWAN_BERRIES);
        simpleItem(ModItems.ROWAN_BARK);
        simpleItem(ModItems.STONE_PESTLE);
        simpleItem(ModItems.SALT);
        simpleItem(ModItems.STONE_MORTAR);
        simpleItem(ModItems.STONE_MORTAR_AND_PESTLE);
        simpleItem(ModItems.WOODEN_MORTAR_AND_PESTLE);

        simpleItem(ModItems.BAT_WING);
        simpleItem(ModItems.SUNLIGHT_FLOWER_SEED);
        simpleItem(ModItems.SUNLIGHT_FLOWER);
        simpleItem(ModItems.MOONLIGHT_FLOWER);
        simpleItem(ModItems.MOONLIGHT_FLOWER_SEED);

        simpleItem(ModItems.TREE_LARVA);
        simpleItem(ModItems.DEAD_TREE_BARK);
        simpleItem(ModItems.DEAD_TREE_LARVA);
        simpleItem(ModItems.FERMENTED_TREE_LARVA);

        simpleItem(ModItems.ABSOLUTE_ORDER);
        simpleItem(ModItems.PETRICHOR);
        simpleItem(ModItems.SMELL_OF_HOME);
        simpleItem(ModItems.DESERT_SPIRIT);
        simpleItem(ModItems.SWEET_DREAM);
        simpleItem(ModItems.SOARING_LIGHTNESS);
        simpleItem(ModItems.RING_OF_MORNING_DEW);
        simpleItem(ModItems.WASTELAND_WIND);
        simpleItem(ModItems.CONIFEROUS_OIL);
        simpleItem(ModItems.STINK_OF_SWAMP);
        simpleItem(ModItems.ABSOLUTE_ORDER);
        simpleItem(ModItems.TROPICAL_MONSOON);
        simpleItem(ModItems.WHIFF_OF_TIME);
        simpleItem(ModItems.CHERRY_ETUDE);
        simpleItem(ModItems.TUNE_OF_HARMONY);
        simpleItem(ModItems.SCENT_OF_UNCERTAINTY);
        simpleItem(ModItems.SCENT_OF_UNCERTAINTY);
        simpleItem(ModItems.EMPTY_VIAL);

        simpleItem(ModItems.ASH);

        simpleItem(ModItems.NAMELESS_CHARCOAL);
        simpleItem(ModItems.WOODEN_FIGURE);
        simpleItem(ModItems.POPPET);

        fenceItem(ModBlocks.ROWAN_FENCE, ModBlocks.ROWAN_PLANKS);
        fenceItem(ModBlocks.NAMELESS_TREE_FENCE, ModBlocks.NAMELESS_TREE_PLANKS);

        handheldItem(ModItems.SILVER_DAGGER);

        saplingItem(ModBlocks.DEAD_TREE_SAPLING);

        complexBlock(ModBlocks.ODOUR_EXTRACTING_FURNACE.get());
        complexAltarInfusingBlock();
        customComplexBlock(ModBlocks.WOODEN_CAT_FIGURE);
        complexBlock(ModBlocks.CHANDELIER.get());
        complexBlock(ModBlocks.SKULL_ON_STICK.get());
        complexBlock(ModBlocks.CRYSTAL_BALL.get());

        simpleItem(ModItems.DRY_WOOD);

        simpleItem(ModItems.MANDRAKE_ROOT);
        simpleItem(ModItems.MANDRAKE_SEED);
        simpleItem(ModItems.SWAMP_ROTFIEND_INGREDIENT);

        simpleItem(ModItems.CHALK_BRUSH);
        simpleItem(ModItems.WHITE_CIRCLE_CORE_DRAWING_KIT);
        simpleItem(ModItems.GREEN_CIRCLE_CORE_DRAWING_KIT);
        simpleItem(ModItems.CRIMSON_CIRCLE_CORE_DRAWING_KIT);
        simpleItem(ModItems.GOLDEN_CHALK);
        simpleItem(ModItems.CRIMSON_CHALK);
        simpleItem(ModItems.WHITE_CHALK);
        simpleItem(ModItems.GREEN_CHALK);
        simpleItem(ModItems.BLACK_CHALK);
        withExistingParent(ModItems.MANDRAKE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.HERMIT_WITCH_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        withExistingParent(ModItems.GLUTTONY_DEMON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        complexBlock(ModBlocks.BREWING_CAULDRON.get());


        simpleItem(ModItems.WET_ENCHANTED_LEATHER);
        simpleItem(ModItems.DRIED_ENCHANTED_LEATHER);
        simpleItem(ModItems.EXHAUSTED_SWALLOW_POTION);
        simpleItem(ModItems.SPURIOUS_THUNDERBOLT_POTION);
        simpleItem(ModItems.MAGMACUBE_GALL_POTION);

        simpleItem(ModItems.GOLDEN_CARROT_SOUP);
        simpleItem(ModItems.MANDRAKE_SOUP);
        simpleItem(ModItems.ROWAN_COMPOTE);

    }

    //Когда у блока должна быть иконка, не отрисованная по самому блоку, а кастомная
    private void customComplexBlock(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ArsMaleficarum.MOD_ID, "item/" + block.getId().getPath()));
    }


    private void complexBlock(Block block) {
        withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(), new ResourceLocation(ArsMaleficarum.MOD_ID,
                "block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath()));
    }

    private void complexAltarInfusingBlock() {
        withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ModBlocks.INFUSING_ALTAR.get())).getPath(), new ResourceLocation(ArsMaleficarum.MOD_ID,
                "block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ModBlocks.INFUSING_ALTAR.get())).getPath() + "_stage_0"));
    }

    //Генерация .json для простого предмета (как, например, цветок шалфея)

    private void simpleItem(RegistryObject<Item> item) {
        withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ArsMaleficarum.MOD_ID, "item/" + item.getId().getPath()));
    }

    private void saplingItem(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ArsMaleficarum.MOD_ID, "block/" + block.getId().getPath()));
    }

    //Чтобы предмет в руке отображался как 3д
    private void handheldItem(RegistryObject<Item> item) {
        withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(ArsMaleficarum.MOD_ID, "item/" + item.getId().getPath()));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", new ResourceLocation(ArsMaleficarum.MOD_ID, "block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(baseBlock.get())).getPath()));
    }
}

package net.sfedu.ars_maleficarum.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.*;

import java.util.List;
import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider  {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ArsMaleficarum.MOD_ID, exFileHelper);
    }

    //Регистрация даты для данных блоков
    @Override
    protected void registerStatesAndModels() {

        makeSageCrop((CropBlock) ModBlocks.SAGE_CROP.get(),"sage_stage","sage_stage");
        makeMarigoldCrop((CropBlock) ModBlocks.MARIGOLD_CROP.get(),"marigold_stage","marigold_stage");
        makeMandrakeCrop((CropBlock) ModBlocks.MANDRAKE_CROP.get(), "mandrake_stage", "mandrake_stage");
        blockWithItem(ModBlocks.CURSED_GOLD_BLOCK);
        blockWithItem(ModBlocks.SILVER_BLOCK);
        blockWithItem(ModBlocks.CURSED_GOLD_ORE_BLOCK);
        blockWithItem(ModBlocks.SILVER_ORE_BLOCK);

        logBlock((RotatedPillarBlock) ModBlocks.ROWAN_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.ROWAN_WOOD.get(),blockTexture(ModBlocks.ROWAN_LOG.get()),blockTexture(ModBlocks.ROWAN_LOG.get()));

        blockItem(ModBlocks.ROWAN_LOG);
        blockItem(ModBlocks.ROWAN_WOOD);
        leavesBlock(ModBlocks.ROWAN_LEAVES);
        leavesBlock(ModBlocks.ROWAN_BERRIES_LEAVES);
        blockWithItem(ModBlocks.ROWAN_PLANKS);
        saplingBlock(ModBlocks.ROWAN_SAPLING);


        logBlock((RotatedPillarBlock) ModBlocks.NAMELESS_TREE_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.NAMELESS_TREE_WOOD.get(),blockTexture(ModBlocks.NAMELESS_TREE_LOG.get()),blockTexture(ModBlocks.NAMELESS_TREE_LOG.get()));

        blockItem(ModBlocks.NAMELESS_TREE_LOG);
        blockItem(ModBlocks.NAMELESS_TREE_WOOD);
        leavesBlock(ModBlocks.NAMELESS_TREE_LEAVES);
        blockWithItem(ModBlocks.NAMELESS_TREE_PLANKS);
        saplingBlock(ModBlocks.NAMELESS_TREE_SAPLING);

        makeSunlight_Flower_Crop(((CropBlock) ModBlocks.SUNLIGHT_FLOWER_CROP.get()), "sunlight_flower_stage_", "sunlight_flower_stage_");
        makeMoonlight_Flower_Crop(((CropBlock) ModBlocks.MOONLIGHT_FLOWER_CROP.get()), "moonlight_flower_stage_", "moonlight_flower_stage_");

        blockWithItem(ModBlocks.SALT_BLOCK);

        logBlock((RotatedPillarBlock) ModBlocks.DEAD_TREE_LOG.get());
        blockItem(ModBlocks.DEAD_TREE_LOG);
        saplingBlock(ModBlocks.DEAD_TREE_SAPLING);

        horizontalBlock(ModBlocks.ODOUR_EXTRACTING_FURNACE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/odour_extracting_furnace")));
        //horizontalBlock(ModBlocks.INFUSING_ALTAR.get(),
                //new ModelFile.UncheckedModelFile(modLoc("block/infusing_altar")));
        horizontalBlock(ModBlocks.INFUSING_ALTAR_STONE_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/infusing_altar_stone_block")));

        coloredInfusingAltar();
        coloredInfusingAltarCarpetBlock();
        coloredInfusingAltarPentaBlock();

    }

    private void coloredInfusingAltar() {
        List<String> colors = List.of("white","orange","magenta","light_blue","yellow","lime","pink","gray","light_gray","cyan","purple","blue","brown","green","red","black");
        getVariantBuilder(ModBlocks.INFUSING_ALTAR.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/infusing_altar"+"_"+colors.get(state.getValue(InfusingAltarBlock.COLOR)))))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );

    }
    private void coloredInfusingAltarCarpetBlock() {
        List<String> colors = List.of("white","orange","magenta","light_blue","yellow","lime","pink","gray","light_gray","cyan","purple","blue","brown","green","red","black");
        getVariantBuilder(ModBlocks.INFUSING_ALTAR_CARPET_BLOCK.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/infusing_altar_carpet_block"+"_"+colors.get(state.getValue(InfusingAltarCarpetBlock.COLOR)))))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );

    }

    private void coloredInfusingAltarPentaBlock() {
        List<String> colors = List.of("white","orange","magenta","light_blue","yellow","lime","pink","gray","light_gray","cyan","purple","blue","brown","green","red","black");
        getVariantBuilder(ModBlocks.INFUSING_ALTAR_PENTA_BLOCK.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/infusing_altar_penta_block"+"_"+colors.get(state.getValue(InfusingAltarPentaBlock.COLOR)))))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );

    }



    //Регистрация листвы
    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all",blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    //Регистрация модели и текстуры для предмета привязанного к блоку
    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(),new ModelFile.UncheckedModelFile(ArsMaleficarum.MOD_ID+
                ":block/"+ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }


    //Регистрация модельной и текстурной даты для шалфея (по стадиям)
    public void makeSageCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState,ConfiguredModel[]> function = state -> sageStates(state,block,modelName,textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    //Массив всех стадий роста шалфея
    private ConfiguredModel[] sageStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName+state.getValue(((SageCropBlock) block).getAgeProperty()),
                new ResourceLocation(ArsMaleficarum.MOD_ID,"block/"+textureName+state.getValue(((SageCropBlock) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }

    public void makeMarigoldCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState,ConfiguredModel[]> function = state->marigoldStates(state,block,modelName,textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    public void makeMandrakeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState,ConfiguredModel[]> function = state->mandrakeStates(state,block,modelName,textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] mandrakeStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName+state.getValue(((MandrakeCropBlock) block).getAgeProperty()),
                new ResourceLocation(ArsMaleficarum.MOD_ID,"block/"+textureName+state.getValue(((MandrakeCropBlock) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }
    //Массив всех стадий роста календулы
    private ConfiguredModel[] marigoldStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName+state.getValue(((MarigoldCropBlock) block).getAgeProperty()),
                new ResourceLocation(ArsMaleficarum.MOD_ID,"block/"+textureName+state.getValue(((MarigoldCropBlock) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }


    //Быстрая регистрация даты для блока и предмета
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }

    //Регистрация саженца
    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
    public void makeSunlight_Flower_Crop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> Sunlight_Flower_States(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] Sunlight_Flower_States(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(((SunlightFlower) block).getAgeProperty()),
                new ResourceLocation(ArsMaleficarum.MOD_ID, "block/" + textureName + state.getValue(((SunlightFlower) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }
    public void makeMoonlight_Flower_Crop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> Moonlight_Flower_States(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] Moonlight_Flower_States(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(((MoonlightFlower) block).getAgeProperty()),
                new ResourceLocation(ArsMaleficarum.MOD_ID, "block/" + textureName + state.getValue(((MoonlightFlower) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }
}

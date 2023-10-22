package net.sfedu.ars_maleficarum.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.MarigoldCropBlock;
import net.sfedu.ars_maleficarum.block.custom.SageCropBlock;

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

    //Массив всех стадий роста календулы
    private ConfiguredModel[] marigoldStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName+state.getValue(((MarigoldCropBlock) block).getAgeProperty()),
                new ResourceLocation(ArsMaleficarum.MOD_ID,"block/"+textureName+state.getValue(((MarigoldCropBlock) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }


    //Быстрая регистрация даты для блока и предета
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }
}

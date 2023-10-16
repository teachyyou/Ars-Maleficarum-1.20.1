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
import net.sfedu.ars_maleficarum.block.custom.SageCropBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider  {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ArsMaleficarum.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        makeSageCrop((CropBlock) ModBlocks.SAGE_CROP.get(),"sage_stage","sage_stage");
    }
    public void makeSageCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState,ConfiguredModel[]> function = state -> sageStates(state,block,modelName,textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] sageStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName+state.getValue(((SageCropBlock) block).getAgeProperty()),
                new ResourceLocation(ArsMaleficarum.MOD_ID,"block/"+textureName+state.getValue(((SageCropBlock) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }


    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }
}

package net.sfedu.ars_maleficarum.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
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
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ChalkSymbol;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.RitualCircleCore;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ArsMaleficarum.MOD_ID, exFileHelper);
    }

    //Регистрация даты для данных блоков
    @Override
    protected void registerStatesAndModels() {

        makeSageCrop();
        makeMarigoldCrop();
        makeMandrakeCrop((CropBlock) ModBlocks.MANDRAKE_CROP.get(), "mandrake_stage", "mandrake_stage");
        blockWithItem(ModBlocks.CURSED_GOLD_BLOCK);
        blockWithItem(ModBlocks.SILVER_BLOCK);
        blockWithItem(ModBlocks.CURSED_GOLD_ORE_BLOCK);
        blockWithItem(ModBlocks.SILVER_ORE_BLOCK);
        blockWithItem(ModBlocks.SITE_OF_SUMMONING_CORE_BLOCK);
        blockWithItem(ModBlocks.SILVER_DEEPSLATE_ORE_BLOCK);
        blockWithItem(ModBlocks.CURSED_GOLD_DEEPSLATE_ORE_BLOCK);
        blockWithItem(ModBlocks.CURSED_GOLD_NETHER_ORE_BLOCK);

        logBlock((RotatedPillarBlock) ModBlocks.ROWAN_LOG.get());
        logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ROWAN_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.ROWAN_WOOD.get(), blockTexture(ModBlocks.ROWAN_LOG.get()), blockTexture(ModBlocks.ROWAN_LOG.get()));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ROWAN_WOOD.get(), blockTexture(ModBlocks.STRIPPED_ROWAN_LOG.get()), blockTexture(ModBlocks.STRIPPED_ROWAN_LOG.get()));

        blockItem(ModBlocks.ROWAN_LOG);
        blockItem(ModBlocks.ROWAN_WOOD);
        blockItem(ModBlocks.STRIPPED_ROWAN_LOG);
        blockItem(ModBlocks.STRIPPED_ROWAN_WOOD);
        leavesBlock(ModBlocks.ROWAN_LEAVES);
        leavesBlock(ModBlocks.ROWAN_BERRIES_LEAVES);
        blockWithItem(ModBlocks.ROWAN_PLANKS);
        saplingBlock(ModBlocks.ROWAN_SAPLING);
        stairsBlock((StairBlock) ModBlocks.ROWAN_STAIRS.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()));
        slabBlock((SlabBlock) ModBlocks.ROWAN_SLAB.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()), blockTexture(ModBlocks.ROWAN_PLANKS.get()));
        fenceBlock((FenceBlock) ModBlocks.ROWAN_FENCE.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.ROWAN_FENCE_GATE.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()));
        blockItem(ModBlocks.ROWAN_STAIRS);
        blockItem(ModBlocks.ROWAN_SLAB);
        blockItem(ModBlocks.ROWAN_FENCE_GATE);


        logBlock((RotatedPillarBlock) ModBlocks.NAMELESS_TREE_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.NAMELESS_TREE_WOOD.get(), blockTexture(ModBlocks.NAMELESS_TREE_LOG.get()), blockTexture(ModBlocks.NAMELESS_TREE_LOG.get()));

        blockItem(ModBlocks.NAMELESS_TREE_LOG);
        blockItem(ModBlocks.NAMELESS_TREE_WOOD);
        leavesBlock(ModBlocks.NAMELESS_TREE_LEAVES);
        blockWithItem(ModBlocks.NAMELESS_TREE_PLANKS);
        saplingBlock(ModBlocks.NAMELESS_TREE_SAPLING);
        stairsBlock((StairBlock) ModBlocks.NAMELESS_TREE_STAIRS.get(), blockTexture(ModBlocks.NAMELESS_TREE_PLANKS.get()));
        slabBlock((SlabBlock) ModBlocks.NAMELESS_TREE_SLAB.get(), blockTexture(ModBlocks.NAMELESS_TREE_PLANKS.get()), blockTexture(ModBlocks.NAMELESS_TREE_PLANKS.get()));
        fenceBlock((FenceBlock) ModBlocks.NAMELESS_TREE_FENCE.get(), blockTexture(ModBlocks.NAMELESS_TREE_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.NAMELESS_TREE_FENCE_GATE.get(), blockTexture(ModBlocks.NAMELESS_TREE_PLANKS.get()));
        blockItem(ModBlocks.NAMELESS_TREE_STAIRS);
        blockItem(ModBlocks.NAMELESS_TREE_SLAB);
        blockItem(ModBlocks.NAMELESS_TREE_FENCE_GATE);

        logBlock((RotatedPillarBlock) ModBlocks.KRAMER_TREE_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.KRAMER_TREE_WOOD.get(), blockTexture(ModBlocks.KRAMER_TREE_LOG.get()), blockTexture(ModBlocks.KRAMER_TREE_LOG.get()));

        blockItem(ModBlocks.KRAMER_TREE_LOG);
        blockItem(ModBlocks.KRAMER_TREE_WOOD);
        leavesBlock(ModBlocks.KRAMER_TREE_LEAVES);
        blockWithItem(ModBlocks.KRAMER_TREE_PLANKS);
        saplingBlock(ModBlocks.KRAMER_SAPLING);


        makeSunlight_Flower_Crop(((CropBlock) ModBlocks.SUNLIGHT_FLOWER_CROP.get()), "sunlight_flower_stage_", "sunlight_flower_stage_");
        makeMoonlight_Flower_Crop(((CropBlock) ModBlocks.MOONLIGHT_FLOWER_CROP.get()), "moonlight_flower_stage_", "moonlight_flower_stage_");

        blockWithItem(ModBlocks.SALT_BLOCK);

        logBlock((RotatedPillarBlock) ModBlocks.DEAD_TREE_LOG.get());
        blockItem(ModBlocks.DEAD_TREE_LOG);
        saplingBlock(ModBlocks.DEAD_TREE_SAPLING);
        SwampRotfiendMushroom();

        horizontalBlock(ModBlocks.ODOUR_EXTRACTING_FURNACE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/odour_extracting_furnace")));
        RitualCircleCore();
        horizontalBlock(ModBlocks.WOODEN_CAT_FIGURE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/wooden_cat_figure")));

        horizontalBlock(ModBlocks.CHANDELIER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/chandelier")));

        horizontalBlock(ModBlocks.SKULL_ON_STICK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/skull_on_a_stick")));

        horizontalBlock(ModBlocks.CRYSTAL_BALL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/crystal_ball")));


        buildChalkSymbols(ModBlocks.WHITE_CHALK_SYMBOL.get());
        buildChalkSymbols(ModBlocks.GREEN_CHALK_SYMBOL.get());
        buildChalkSymbols(ModBlocks.CRIMSON_CHALK_SYMBOL.get());

        cauldronFuelVariants();

        InfusingAltarBlock();

    }

    public void RitualCircleCore() {
        Function<BlockState, ConfiguredModel[]> function = this::circleCoreTypes;
        getVariantBuilder(ModBlocks.RITUAL_CIRCLE_CORE.get()).forAllStatesExcept(function);
    }

    private ConfiguredModel[] circleCoreTypes(BlockState state) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        String type = state.getValue(RitualCircleCore.CIRCLE_TYPE).getSerializedName();
        models[0]=new ConfiguredModel(models().withExistingParent(type+"_ritual_circle_core",ArsMaleficarum.MOD_ID+":block/ritual_circle_core")
                .texture("1","block/"+type+"_circle_core_texture")
                .texture("particle","block/"+type+"_circle_core_texture"));
        return models;
    }


    public void buildChalkSymbols(Block chalkSymbol) {
        Function<BlockState, ConfiguredModel[]> function = this::chalkSymbols;

        getVariantBuilder(chalkSymbol).forAllStates(function);

    }
    private ConfiguredModel[] chalkSymbols(BlockState state) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        int variant = state.getValue(ChalkSymbol.VARIANT);
        models[0]=new ConfiguredModel(models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(state.getBlock())).getPath()+variant,ArsMaleficarum.MOD_ID+":block/chalk_symbol")
                .texture("particle","block/"+ Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(state.getBlock())).getPath()+"_"+variant),0,
                ((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360,false);

        return models;


    }

    private void cauldronFuelVariants() {

        getVariantBuilder(ModBlocks.BREWING_CAULDRON.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/brewing_cauldron_" + state.getValue(BrewingCauldronBlock.FUEL))))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );

    }

    private void SwampRotfiendMushroom() {
        getVariantBuilder(ModBlocks.SWAMP_ROTFIEND.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/swamp_rotfiend_" + state.getValue(SwampRotfiendMushroom.AGE)))
                        ).rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build());
    }

    private void InfusingAltarBlock() {
        getVariantBuilder(ModBlocks.INFUSING_ALTAR.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(modLoc(buildModelFile(state))))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );
    }
    private String buildModelFile(BlockState blockState) {
        List<String> colors = List.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black");
        int stage = blockState.getValue(InfusingAltarBlock.STAGE);
        String path = "block/infusing_altar_stage_";
        path+=stage;
        if (stage > 0) path+="_"+colors.get(blockState.getValue(InfusingAltarBlock.COLOR));
        return path;
    }




    //Регистрация листвы
    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get())).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    //Регистрация модели и текстуры для предмета привязанного к блоку
    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(ArsMaleficarum.MOD_ID +
                ":block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get())).getPath()));
    }


    //Регистрация модельной и текстурной даты для шалфея (по стадиям)
    private void makeSageCrop() {
        getVariantBuilder(ModBlocks.SAGE_CROP.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/sage_stage" + state.getValue(HerbCropBlock.AGE))))
                        .build()
                );
    }


    private void makeMarigoldCrop() {
        getVariantBuilder(ModBlocks.MARIGOLD_CROP.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/marigold_stage" + state.getValue(HerbCropBlock.AGE))))
                        .build()
                );
    }

    public void makeMandrakeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> mandrakeStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] mandrakeStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(((MandrakeCropBlock) block).getAgeProperty()),
                new ResourceLocation(ArsMaleficarum.MOD_ID, "block/" + textureName + state.getValue(((MandrakeCropBlock) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }



    //Быстрая регистрация даты для блока и предмета
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    //Регистрация саженца
    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get())).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
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
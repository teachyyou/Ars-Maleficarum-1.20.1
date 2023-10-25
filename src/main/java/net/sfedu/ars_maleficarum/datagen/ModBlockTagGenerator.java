package net.sfedu.ars_maleficarum.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ArsMaleficarum.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //Для добычи каких предметов нужна кирка
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.SILVER_BLOCK.get(),
                ModBlocks.CURSED_GOLD_BLOCK.get(),
                ModBlocks.SILVER_ORE_BLOCK.get(),
                ModBlocks.CURSED_GOLD_ORE_BLOCK.get());
        //Категория предметов, для добычи которых нужны как минимум железные инструменты
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.CURSED_GOLD_BLOCK.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.SILVER_BLOCK.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.CURSED_GOLD_ORE_BLOCK.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.SILVER_ORE_BLOCK.get());
    }
}

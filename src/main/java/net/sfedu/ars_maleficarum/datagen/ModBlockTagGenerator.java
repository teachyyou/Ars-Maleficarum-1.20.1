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
                ModBlocks.CURSED_GOLD_ORE_BLOCK.get(),
                ModBlocks.ODOUR_EXTRACTING_FURNACE.get(),
                ModBlocks.INFUSING_ALTAR_STONE_BLOCK.get(),
                ModBlocks.INFUSING_ALTAR_CARPET_BLOCK.get(),
                ModBlocks.INFUSING_ALTAR_PENTA_BLOCK.get(),
                ModBlocks.INFUSING_ALTAR.get());

        //Категория предметов, для добычи которых нужны как минимум железные инструменты
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                ModBlocks.CURSED_GOLD_BLOCK.get(),
                ModBlocks.SILVER_BLOCK.get(),
                ModBlocks.CURSED_GOLD_BLOCK.get(),
                ModBlocks.SILVER_ORE_BLOCK.get());




        //Категория того, что считается горючим деревом (рядом с этим не исчезает листва))
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROWAN_LOG.get())
                .add(ModBlocks.ROWAN_WOOD.get())
                .add(ModBlocks.DEAD_TREE_LOG.get())
                .add(ModBlocks.NAMELESS_TREE_LOG.get())
                .add(ModBlocks.NAMELESS_TREE_WOOD.get());

        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.ROWAN_PLANKS.get())
                .add(ModBlocks.NAMELESS_TREE_PLANKS.get());
        this.tag(BlockTags.LEAVES)
                .add(ModBlocks.NAMELESS_TREE_LEAVES.get())
                .add(ModBlocks.ROWAN_BERRIES_LEAVES.get())
                .add(ModBlocks.ROWAN_LEAVES.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                ModBlocks.SALT_BLOCK.get()
        );
    }
}

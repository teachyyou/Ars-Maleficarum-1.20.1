package net.sfedu.ars_maleficarum.block.custom.entity;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sfedu.ars_maleficarum.ArsMaleficarum;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ArsMaleficarum.MOD_ID);

    public static final RegistryObject<BlockEntityType<OdourExtractingFurnaceBlockEntity>> ODOUR_EXTRACTING_FURNACE_BE =
            BLOCK_ENTITIES.register("odour_extracting_furnace_block_entity", () ->
                    BlockEntityType.Builder.of(OdourExtractingFurnaceBlockEntity::new,
                            ModBlocks.ODOUR_EXTRACTING_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<InfusingAltarBlockEntity>> INFUSING_ALTAR_BE =
            BLOCK_ENTITIES.register("infusing_altar_block_entity", () ->
                    BlockEntityType.Builder.of(InfusingAltarBlockEntity::new,
                            ModBlocks.INFUSING_ALTAR.get()).build(null));

    public static final RegistryObject<BlockEntityType<RitualCoreEntity>> RITUAL_CORE_ENTITY =
            BLOCK_ENTITIES.register("ritual_core_entity", () ->
                    BlockEntityType.Builder.of(RitualCoreEntity::new,
                            ModBlocks.RITUAL_CIRCLE_CORE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}

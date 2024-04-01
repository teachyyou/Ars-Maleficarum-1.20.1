package net.sfedu.ars_maleficarum.block.custom.entity;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.sfedu.ars_maleficarum.block.custom.BrewingCauldronBlock;
import net.sfedu.ars_maleficarum.recipe.BrewingCauldronRecipe;
import net.sfedu.ars_maleficarum.recipe.InfusingAltarRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.minecraft.world.level.block.Block.popResource;


public class BrewingCauldronBlockEntity extends BlockEntity {

    // Область засасывания предметов
    public final VoxelShape SUCK = Block.box(3.0D, 4.0D, 3.0D, 12.0D, 7.0D, 12.0D);
    public final VoxelShape getSuckShape() {
        return SUCK;
    }

    public double getLevelX() {
        return (double)this.worldPosition.getX() + 0.5D;
    }

    public double getLevelY() {
        return (double)this.worldPosition.getY() + 0.5D;
    }

    public double getLevelZ() {
        return (double)this.worldPosition.getZ() + 0.5D;
    }


    // Количество слотов - максимальное количество предметов в котле. 1 слот - 1 предмет.
    public final int slotsCount = 10;

    private int fuelLevel = 0;
    private int temperature = 0;
    private int boilTick = 0;

    private final int maxTemperature = 1500;
    private final int maxFuel = 1300;


    private final ItemStackHandler itemHandler = new ItemStackHandler(slotsCount) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return true;
        }


    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public BrewingCauldronBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BREWING_CAULDRON_BE.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        pTag.putInt("fuelLevel",this.fuelLevel);
        pTag.putInt("temperature",this.temperature);
        super.saveAdditional(pTag);
    }
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.fuelLevel = pTag.getInt("fuelLevel");
        this.temperature = pTag.getInt("temperature");
    }

    public void drops() {
        // Ничего не выбрасывает
    }

    public boolean addFuel(BlockState pState, Level pLevel, BlockPos pPos)
    {
        if (fuelLevel < maxFuel-180)
        {
            fuelLevel += 180;
            pLevel.playSound(null, pPos, SoundType.SCAFFOLDING.getPlaceSound(), SoundSource.BLOCKS);
            if (fuelLevel > maxFuel) fuelLevel = maxFuel;
            return true;
        }
        return false;
    }

    public void tick(Level level, BlockPos pPos, BlockState pState) {

        suckItems(level, pPos, pState);
        temperatureTick(level, pPos, pState);
        blockStatesChange(level, pPos, pState);

        if (hasRecipe())
        {
            craftItem(level, pPos, pState);
            level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
        }
    }
    public void clientTick(Level level, BlockPos pPos, BlockState pState) {
        boilingAnimation(level, pPos, pState);
    }

    private void boilingAnimation(Level level, BlockPos pPos, BlockState pState)
    {
        double dx = pPos.getX();
        double dy = pPos.getY();
        double dz = pPos.getZ();
        RandomSource pRandom = level.getRandom();
        if (pState.getValue(BrewingCauldronBlock.BOILING))
        {
            boilTick++;
            if (boilTick >= 2)
            {
                boilTick = 0;
                float height = (float) (0.188f+0.125f*pState.getValue(BrewingCauldronBlock.WATER)+dy);
                for (int i = 0; i < 4; i++) {
                    double rx = pRandom.nextDouble();
                    double rz = pRandom.nextDouble();
                    level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_POP, true, dx+0.5D+(rx/3D-0.166D)*1.4f, height,dz+0.5D+(rz/3D-0.166D)*1.4f, 0, 0, 0);
                    level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE, true, dx+0.5D+(rx/3D-0.166D)*1.4f, height,dz+0.5D+(rz/3D-0.166D)*1.4f, 0, 0, 0);
                }
                if (pRandom.nextDouble() < 0.2F) {
                    level.playLocalSound(pPos, SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundSource.BLOCKS, 1, 0, true);
                }
            }
        }
    }
    // Пытается забрать предметы в определённой зоне
    private void suckItems(Level level, BlockPos pPos, BlockState pState)
    {
        if (level.isClientSide()) return;
        if (pState.getValue(BrewingCauldronBlock.LIT)){
            List<LivingEntity> livingEntities = getLivingEntitiesAbove(level, this);
            for (LivingEntity livingEntity : livingEntities)
            {
                livingEntity.hurt(livingEntity.damageSources().inFire(), 1);
            }
        }

        if (pState.getValue(BrewingCauldronBlock.WATER) == 0) return; // Предметы не закидываются, если нет воды.

        List<ItemEntity> itemEntitiesAbove = getItemsAtAndAbove(level, this);
        if (!itemEntitiesAbove.isEmpty()) {
            for (ItemEntity itemEntity : itemEntitiesAbove)
            {
                ItemStack itemStack = itemEntity.getItem();
                if (addItem(this.itemHandler, itemStack.getItem()))
                {
                    itemEntity.setItem(new ItemStack(itemStack.getItem(), itemStack.getCount()-1));
                    level.playSound(null, pPos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS);
                }

            }
        }

    }

    // Отвечает за нагревание и остывание котла
    private void temperatureTick(Level level, BlockPos pPos, BlockState pState)
    {
        if (pState.getValue(BrewingCauldronBlock.LIT) && fuelLevel > 0 && temperature < maxTemperature)
        {
            temperature+=2;
            fuelLevel--;
        }
        else if (!pState.getValue(BrewingCauldronBlock.LIT) && (temperature > 0))
        {
            temperature--;
        }
        if (pState.getValue(BrewingCauldronBlock.WATER)==0) temperature = 0;
    }

    private void blockStatesChange(Level level, BlockPos pPos, BlockState pState)
    {
        if (pState.getValue(BrewingCauldronBlock.LIT) && fuelLevel <= 0)
        {
            level.playSound(null, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);
            level.setBlock(pPos, pState.setValue(BrewingCauldronBlock.LIT, false), 3);
        }
        if (temperature >= 1000)
        {
            if (!pState.getValue(BrewingCauldronBlock.BOILING)) level.setBlock(pPos, pState.setValue(BrewingCauldronBlock.BOILING, true), 3);
        }
        else
        {
            if (pState.getValue(BrewingCauldronBlock.BOILING)) level.setBlock(pPos, pState.setValue(BrewingCauldronBlock.BOILING, false), 3);
        }

        if (fuelLevel == 0)
        {
            if (pState.getValue(BrewingCauldronBlock.FUEL) != 0) level.setBlock(pPos, pState.setValue(BrewingCauldronBlock.FUEL, 0), 3);
        }
        else if (fuelLevel < 300)
        {
            if (pState.getValue(BrewingCauldronBlock.FUEL) != 1) level.setBlock(pPos, pState.setValue(BrewingCauldronBlock.FUEL, 1), 3);
        }
        else if (fuelLevel < 1000)
        {
            if (pState.getValue(BrewingCauldronBlock.FUEL) != 2) level.setBlock(pPos, pState.setValue(BrewingCauldronBlock.FUEL, 2), 3);
        }
        else
        {
            if (pState.getValue(BrewingCauldronBlock.FUEL) != 3) level.setBlock(pPos, pState.setValue(BrewingCauldronBlock.FUEL, 3), 3);
        }

    }

    // Если есть пустой слот, добавляет туда предмет и возвращает true
    public boolean addItem(ItemStackHandler pItemHandler, Item pItem)
    {
        for (int i = 0; i < pItemHandler.getSlots(); i++)
        {
            if (pItemHandler.getStackInSlot(i) == ItemStack.EMPTY)
            {
                pItemHandler.setStackInSlot(i, new ItemStack(pItem));
                return true;
            }
        }

        return false;
    }

    // Возвращает все ItemEntity внутри определенной зоны
    public static List<ItemEntity> getItemsAtAndAbove(Level pLevel, BrewingCauldronBlockEntity BCEntity) {
        return BCEntity.getSuckShape().toAabbs().stream().flatMap((p_155558_) -> {
            return pLevel.getEntitiesOfClass(ItemEntity.class, p_155558_.move(BCEntity.getLevelX() - 0.5D, BCEntity.getLevelY() - 0.5D, BCEntity.getLevelZ() - 0.5D), EntitySelector.ENTITY_STILL_ALIVE).stream();
        }).collect(Collectors.toList());
    }

    // Возвращает все LivingEntity внутри определенной зоны
    public static List<LivingEntity> getLivingEntitiesAbove(Level pLevel, BrewingCauldronBlockEntity BCEntity) {
        return BCEntity.getSuckShape().toAabbs().stream().flatMap((p_155558_) -> {
            return pLevel.getEntitiesOfClass(LivingEntity.class, p_155558_.move(BCEntity.getLevelX() - 0.5D, BCEntity.getLevelY() - 0.5D, BCEntity.getLevelZ() - 0.5D), EntitySelector.ENTITY_STILL_ALIVE).stream();
        }).collect(Collectors.toList());
    }



    private Optional<BrewingCauldronRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i,this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(BrewingCauldronRecipe.Type.INSTANCE,inventory,level);

    }

    private boolean hasRecipe() {
        Optional<BrewingCauldronRecipe> recipe = getCurrentRecipe();
        return (!recipe.isEmpty());

    }

    private void craftItem(Level level, BlockPos pPos, BlockState pState) {
        if (level.isClientSide) return;
        Optional<BrewingCauldronRecipe> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

        for (int i = 0; i<10; i++) {
            this.itemHandler.extractItem(i,1,false);
        }
        level.setBlock(pPos, pState.setValue(BrewingCauldronBlock.WATER, 0), 3);
        popResource(level, pPos, new ItemStack(resultItem.getItem(),1));
        level.playSound(null, pPos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.BLOCKS);

    }
}

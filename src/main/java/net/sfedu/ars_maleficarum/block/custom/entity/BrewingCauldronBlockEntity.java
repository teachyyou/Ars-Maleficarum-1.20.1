package net.sfedu.ars_maleficarum.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;


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
        super.saveAdditional(pTag);
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots();i++) {
            inventory.setItem(i,itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }


    public void tick(Level level, BlockPos pPos, BlockState pState) {
        List<ItemEntity> itemEntitiesAbove = getItemsAtAndAbove(level, this);
        if (!itemEntitiesAbove.isEmpty()) {
            for (ItemEntity itemEntity : itemEntitiesAbove)
            {
                ItemStack itemStack = itemEntity.getItem();
                if (addItem(this.itemHandler, itemStack.getItem()))
                {

                    itemEntity.setItem(new ItemStack(itemStack.getItem(), itemStack.getCount()-1));
                }
            }
        }

        if (pState.getValue(BrewingCauldronBlock.LIT))
        {
            pState.setValue(BrewingCauldronBlock.TEMPERATURE, pState.getValue(BrewingCauldronBlock.TEMPERATURE)+60);
        }
        else
        {
            pState.setValue(BrewingCauldronBlock.TEMPERATURE, pState.getValue(BrewingCauldronBlock.TEMPERATURE)-60);
        }
        System.out.println(pState.getValue(BrewingCauldronBlock.TEMPERATURE));
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

    // Возвращает все ItemEntity в зоне SUCK - внутри варочного котла.
    public static List<ItemEntity> getItemsAtAndAbove(Level pLevel, BrewingCauldronBlockEntity BCEntity) {
        return BCEntity.getSuckShape().toAabbs().stream().flatMap((p_155558_) -> {
            return pLevel.getEntitiesOfClass(ItemEntity.class, p_155558_.move(BCEntity.getLevelX() - 0.5D, BCEntity.getLevelY() - 0.5D, BCEntity.getLevelZ() - 0.5D), EntitySelector.ENTITY_STILL_ALIVE).stream();
        }).collect(Collectors.toList());
    }


}

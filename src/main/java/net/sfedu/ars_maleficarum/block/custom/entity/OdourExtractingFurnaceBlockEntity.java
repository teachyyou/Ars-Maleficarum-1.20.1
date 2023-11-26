package net.sfedu.ars_maleficarum.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.item.ModItems;
import net.sfedu.ars_maleficarum.recipe.OdourExtractingRecipe;
import net.sfedu.ars_maleficarum.screen.OdourExtractorFurnaceMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class OdourExtractingFurnaceBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch(slot) {
                case 0 -> stack.getItem() != Items.CHARCOAL && stack.getItem() != MAGIC_FUEL && stack.getItem() != BOTTLE;
                case 1 -> stack.getItem() == Items.CHARCOAL || stack.getItem() == MAGIC_FUEL;
                case 2 -> stack.getItem() == BOTTLE;
                case 3,4 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    //Топливо ускоряющее перегонку в 2 раза.
    private static final Item MAGIC_FUEL = ModItems.NAMELESS_CHARCOAL.get();

    private static final Item BOTTLE = ModItems.EMPTY_VIAL.get();

    private static final int INPUT_SLOT = 0;
    private static final int FUEL = 1;
    private static final int JARS = 2;
    private static final int OUTPUT = 3;
    private static final int ADDITIONAL = 4;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress=160;
    private int litLevel = 0;

    //private int maxLitLevel = 1280;
    private int maxLitLevel = 480;

    boolean useMagicFuel = false;


    public OdourExtractingFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ODOUR_EXTRACTING_FURNACE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch(pIndex) {
                    case 0 -> OdourExtractingFurnaceBlockEntity.this.progress;
                    case 1 -> OdourExtractingFurnaceBlockEntity.this.maxProgress;
                    case 2 -> OdourExtractingFurnaceBlockEntity.this.litLevel;
                    case 3 -> OdourExtractingFurnaceBlockEntity.this.maxLitLevel;
                    default ->  0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch(pIndex) {
                    case 0 -> OdourExtractingFurnaceBlockEntity.this.progress = pValue;
                    case 1 -> OdourExtractingFurnaceBlockEntity.this.maxProgress = pValue;
                    case 2 -> OdourExtractingFurnaceBlockEntity.this.litLevel = pValue;
                    case 3 -> OdourExtractingFurnaceBlockEntity.this.maxLitLevel = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i <itemHandler.getSlots(); i++) {
            inventory.setItem(i,itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("Odour_Extracting_Furnace_DisplayName");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new OdourExtractorFurnaceMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap== ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap,side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()->itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        pTag.putInt("progress",this.progress);
        pTag.putBoolean("useMagicFuel",this.useMagicFuel);
        pTag.putInt("litLevel",this.litLevel);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.progress = pTag.getInt("progress");
        this.useMagicFuel=pTag.getBoolean("useMagicFuel");
        this.litLevel = pTag.getInt("litLevel");
    }

    public void tick(Level level, BlockPos pPos, BlockState pState) {
        if (isOutputSlotEmptyOrReceivable() && hasRecipe() && hasFuelOrLit()) {
            if (hasFuel() && litLevel == 0) {
                consumeFuel();
            }
            increaseCraftingProcess();

            setChanged(level,pPos,pState);
            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
            decreaseLitLevel();
        }
        else {
            resetProgress();
            if (litLevel>0) decreaseLitLevel();
        }
    }

    public void resetProgress() {
        this.progress=0;
    }

    public void resetLitLevel() {
        this.litLevel=0;
    }

    private void craftItem() {
        Random random = new Random();

        Optional<OdourExtractingRecipe> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.get().getResultItem(null);
        float chance = recipe.get().getChance(null);

        this.itemHandler.setStackInSlot(OUTPUT,new ItemStack(resultItem.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT).getCount()+1));

        if (!recipe.get().getIsBottleRequired(null)
                && random.nextFloat()>(1-chance)) {
           this.itemHandler.setStackInSlot(ADDITIONAL,new ItemStack(recipe.get().getAdditionalItem(null).getItem(),
                   this.itemHandler.getStackInSlot(ADDITIONAL).getCount()+1));

        }

        else if (this.itemHandler.getStackInSlot(JARS).getItem() == BOTTLE
                && recipe.get().getIsBottleRequired(null)
                && random.nextFloat()>(1-chance)) {
                    this.itemHandler.extractItem(JARS,1,false);
                    this.itemHandler.setStackInSlot(ADDITIONAL,new ItemStack(recipe.get().getAdditionalItem(null).getItem(),
                            this.itemHandler.getStackInSlot(ADDITIONAL).getCount()+1));

        }

        this.itemHandler.extractItem(INPUT_SLOT,1,false);

    }

    private void consumeFuel() {
        useMagicFuel = this.itemHandler.getStackInSlot(FUEL).getItem() == MAGIC_FUEL;
        this.itemHandler.extractItem(FUEL,1,false);
        System.out.println("Fuel consumed!");
        litLevel = maxLitLevel;
    }

    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private boolean hasFuelOrLit() {
        return  this.itemHandler.getStackInSlot(FUEL).getItem()== Items.CHARCOAL ||
                this.itemHandler.getStackInSlot(FUEL).getItem()== MAGIC_FUEL ||
                litLevel > 0;
    }
    private boolean hasFuel() {
        return  this.itemHandler.getStackInSlot(FUEL).getItem()== Items.CHARCOAL ||
                this.itemHandler.getStackInSlot(FUEL).getItem()== MAGIC_FUEL;
    }

    private void increaseCraftingProcess() {
        this.progress++;
        if (useMagicFuel) this.progress++;
    }

    private void decreaseLitLevel() {
        this.litLevel--;
    }


    private boolean hasRecipe() {
        Optional<OdourExtractingRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack resultItem = recipe.get().getResultItem(null);
        ItemStack additionalItem = recipe.get().getAdditionalItem(null);

        return canInsertAmountIntoOutputSlot(resultItem.getCount())
                && canInsertItemIntoOutputSlot(resultItem.getItem())
                && canInsertAmountIntoAdditionalSlot()
                && canInsertItemIntoAdditionalSlot(additionalItem.getItem());
    }

    private Optional<OdourExtractingRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i,this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(OdourExtractingRecipe.Type.INSTANCE,inventory,level);
    }


    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT).is(item) || itemHandler.getStackInSlot(OUTPUT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT).getMaxStackSize() >=
                this.itemHandler.getStackInSlot(OUTPUT).getCount()+count;
    }

    private boolean canInsertItemIntoAdditionalSlot(Item item) {
        return this.itemHandler.getStackInSlot(ADDITIONAL).is(item) || itemHandler.getStackInSlot(ADDITIONAL).isEmpty();
    }

    private boolean canInsertAmountIntoAdditionalSlot() {
        return this.itemHandler.getStackInSlot(ADDITIONAL).getMaxStackSize() >=
                this.itemHandler.getStackInSlot(ADDITIONAL).getCount()+1;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.itemHandler.getStackInSlot(OUTPUT).isEmpty() ||
                this.itemHandler.getStackInSlot(OUTPUT).getCount() < this.itemHandler.getStackInSlot(OUTPUT).getMaxStackSize();
    }

}

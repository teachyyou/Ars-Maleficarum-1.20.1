package net.sfedu.ars_maleficarum.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.recipe.InfusingAltarRecipe;
import net.sfedu.ars_maleficarum.screen.InfusingAltarMenu;
import net.sfedu.ars_maleficarum.sound.ModSounds;
import net.sfedu.ars_maleficarum.util.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InfusingAltarBlockEntity extends BlockEntity implements MenuProvider {

    public final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!Objects.requireNonNull(level).isClientSide()) {
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);

            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch(slot) {
                case 0,1,2,3,4 -> true;
                case 5 -> stack.is(ModTags.Items.BLANK_FOCUS);
                default ->  super.isItemValid(slot,stack);
            };
        }
    };

    private static final int OUTPUT_SLOT = 5;

    protected final ContainerData data;
    private int progress;
    private int maxProgress = 400;

    private ResourceKey<DimensionType> dimension;



    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();



    public InfusingAltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.INFUSING_ALTAR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> InfusingAltarBlockEntity.this.progress;
                    case 1 -> InfusingAltarBlockEntity.this.maxProgress;
                    default ->  0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> InfusingAltarBlockEntity.this.progress=pValue;
                    case 1 -> InfusingAltarBlockEntity.this.maxProgress=pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    @NotNull
    public Component getDisplayName() {
        return Component.translatable("Infusing_Altar_DisplayName");
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new InfusingAltarMenu(pContainerId,pPlayerInventory,this,this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots();i++) {
            inventory.setItem(i,itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(Objects.requireNonNull(this.level),this.worldPosition,inventory);
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
        super.saveAdditional(pTag);
    }
    @Override
    @ParametersAreNonnullByDefault
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.progress = pTag.getInt("progress");
    }


    public void tick(Level level, BlockPos pPos, BlockState pState) {
        getRenderStack();
        if (hasRecipe(level) && hasCorrectStructureAround(level, pPos)) {

            increaseCraftingProcess();
            setChanged(level,pPos,pState);
            if (hasProgressFinished()) {
                craftItem(level, pPos);
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
                resetProgress();
            }
        }
        else {
            resetProgress();
        }

    }

    private Optional<InfusingAltarRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i,this.itemHandler.getStackInSlot(i));
        }
        return Objects.requireNonNull(this.level).getRecipeManager().getRecipeFor(InfusingAltarRecipe.Type.INSTANCE,inventory,level);

    }

    private boolean checkDimension(InfusingAltarRecipe recipe, Level level) {
        String s = recipe.getDimension(null);
        if (s.equals("any")) return true;
        ResourceKey<DimensionType> dimType = level.dimensionTypeId();
        dimension = switch(s) {
            case "nether" -> BuiltinDimensionTypes.NETHER;
            case "end" -> BuiltinDimensionTypes.END;
            default -> BuiltinDimensionTypes.OVERWORLD;
        };
        return dimType.equals(dimension);
    }

    private boolean hasRecipe(Level level) {
        Optional<InfusingAltarRecipe> recipe = getCurrentRecipe();
        return (recipe.isPresent() && checkDimension(recipe.get(),level));

    }

    private void increaseCraftingProcess() {
        this.progress++;
    }

    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void craftItem(Level pLevel, BlockPos pPos) {
        if (getCurrentRecipe().isEmpty()) return;
        InfusingAltarRecipe recipe = getCurrentRecipe().get();
        ItemStack resultItem = recipe.getResultItem(Objects.requireNonNull(getLevel()).registryAccess());

        for (int i = 0; i<5; i++) {
            this.itemHandler.extractItem(i,1,false);
        }
        this.itemHandler.extractItem(OUTPUT_SLOT,1,false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT,new ItemStack(resultItem.getItem(),1));

        EntityType.LIGHTNING_BOLT.spawn((ServerLevel) Objects.requireNonNull(level), (ItemStack) null,null,pPos, MobSpawnType.TRIGGERED,true,true);
        pLevel.playSound(null,pPos, ModSounds.MYSTIC_WHISPERING.get(), SoundSource.VOICE);



    }

    private void resetProgress() {
        this.progress=0;
    }

    private boolean hasCorrectStructureAround(Level level, BlockPos pPos) {
        if (dimension.equals(BuiltinDimensionTypes.NETHER)) return level.getBlockState(pPos.below()).is(ModBlocks.SITE_OF_SUMMONING_CORE_BLOCK.get());

        Direction[] dir = {Direction.NORTH,Direction.EAST,Direction.SOUTH,Direction.WEST};
        boolean flag = true;
        for (int h = 0; h<2; h++) {
            for (int i = 0; i<4; i++) {
                BlockState block1 = level.getBlockState(pPos.above(h).relative(dir[i],3).relative(dir[(i+1+4)%4]));
                BlockState block2 = level.getBlockState(pPos.above(h).relative(dir[i],3).relative(dir[(i-1+4)%4]));
                if (block1!= Blocks.OBSIDIAN.defaultBlockState() || block2!=Blocks.OBSIDIAN.defaultBlockState()) {
                    flag = false;
                }
            }
        }

        return flag;
    }

    public List<ItemStack> getRenderStack() {
        //System.out.println(this.itemHandler.getStackInSlot(5).getDisplayName());
        return List.of(this.itemHandler.getStackInSlot(0),
                this.itemHandler.getStackInSlot(1),
                this.itemHandler.getStackInSlot(2),
                this.itemHandler.getStackInSlot(3),
                this.itemHandler.getStackInSlot(4),
                this.itemHandler.getStackInSlot(5));
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    @NotNull
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net,pkt);
    }
}

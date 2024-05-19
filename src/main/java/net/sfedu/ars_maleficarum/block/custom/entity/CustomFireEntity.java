package net.sfedu.ars_maleficarum.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.UUID;

public class CustomFireEntity extends BlockEntity {

    private UUID ownerUUID;
    private boolean hasOwner = false;
    public CustomFireEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CUSTOM_FIRE.get(), pPos, pBlockState);
        ownerUUID = null;
    }
    public CustomFireEntity(BlockPos pPos, BlockState pBlockState, Entity pPlayer) {
        super(ModBlockEntities.CUSTOM_FIRE.get(), pPos, pBlockState);
        setOwner(pPlayer);
    }

    public void setOwner(@Nullable Entity pPlayer)
    {
        if (pPlayer != null) {
            this.ownerUUID = pPlayer.getUUID();
            this.hasOwner = true;
        }
    }
    @Nullable
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        if (this.ownerUUID!=null) pTag.putUUID("owner",this.ownerUUID);
        pTag.putBoolean("hasOwner",hasOwner);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.hasOwner = pTag.getBoolean("hasOwner");
        if (hasOwner) this.ownerUUID = pTag.getUUID("owner");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net,pkt);
    }
}

package net.sfedu.ars_maleficarum.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.sfedu.ars_maleficarum.block.custom.CustomFireBlock;

import javax.annotation.Nullable;
import java.util.UUID;

public class CustomFireEntity extends BlockEntity {

    private UUID ownerUUID;
    private Entity cachedOwner;
    public CustomFireEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CUSTOM_FIRE.get(), pPos, pBlockState);
        ownerUUID = null;
        cachedOwner = null;
    }
    public CustomFireEntity(BlockPos pPos, BlockState pBlockState, Entity pPlayer) {
        super(ModBlockEntities.CUSTOM_FIRE.get(), pPos, pBlockState);
        setOwner(pPlayer);
    }

    public void setOwner(@Nullable Entity pPlayer)
    {
        if (pPlayer != null) {
            this.ownerUUID = pPlayer.getUUID();
            this.cachedOwner = pPlayer;
        }
    }
    @Nullable
    public Entity getOwner() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.cachedOwner;
        }
        return null;
    }
    @Nullable
    public UUID getOwnerUUID() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.ownerUUID;
        }
        return null;
    }
}

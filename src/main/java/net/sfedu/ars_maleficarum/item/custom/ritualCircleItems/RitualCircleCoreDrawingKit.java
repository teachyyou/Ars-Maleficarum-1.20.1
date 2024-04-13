package net.sfedu.ars_maleficarum.item.custom.ritualCircleItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.RitualCircleCore;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.sound.ModSounds;

import java.util.Random;

public class RitualCircleCoreDrawingKit extends Item {



    public RitualCircleCoreDrawingKit(Properties pProperties) {
        super(pProperties);
    }

    protected BlockState coreToDraw = ModBlocks.RITUAL_CIRCLE_CORE.get().defaultBlockState().setValue(RitualCircleCore.CIRCLETYPE, RitualCoreEntity.CircleColor.WHITE);

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            System.out.println(canDraw(pContext));
            if (pContext.getLevel().getBlockState(pContext.getClickedPos()).isCollisionShapeFullBlock(pContext.getLevel(),pContext.getClickedPos()) && canDraw(pContext)) {
                pContext.getLevel().setBlock(pContext.getClickedPos().above(), coreToDraw,3);
                pContext.getItemInHand().shrink(1);
                pContext.getLevel().playSound(null,pContext.getClickedPos(), ModSounds.CHALK_USE.get(), SoundSource.PLAYERS);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }



    private boolean canDraw(UseOnContext pContext) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j<=1; j++) {
                BlockPos pos = pContext.getClickedPos().relative(Direction.Axis.X, i).relative(Direction.Axis.Z,j);
                if (pContext.getLevel().getBlockState(pos).is(Blocks.AIR) || !(pContext.getLevel().getBlockState(pos.above()).is(Blocks.AIR) || pContext.getLevel().getBlockState(pos.above()).is(BlockTags.CANDLES))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 1;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }
}

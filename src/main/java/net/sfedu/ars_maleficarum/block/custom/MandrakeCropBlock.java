package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;
import net.sfedu.ars_maleficarum.item.ModItems;

public class MandrakeCropBlock extends CropBlock {

    //Максимальная стадия роста (от 0)
    public static final int MAX_AGE = 3;

    //Стадия роста
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    //Конструктор, вызывающий конструктор родителя
    public MandrakeCropBlock(Properties pProperties) {
        super(pProperties);
    }

    //Семена, необходимые для выращивания
    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.MANDRAKE_SEED.get();
    }

    //Текущая стадия роста
    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    //Максимальная стадия роста
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
    private void spawnMandrake(ServerLevel pLevel, BlockPos pPos) {
        MandrakeEntity mandrake = ModEntities.MANDRAKE.get().create(pLevel);
        if (mandrake != null) {
            mandrake.moveTo((double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, 0.0F, 0.0F);
            pLevel.addFreshEntity(mandrake);
        }
    }

    public void spawnAfterBreak(BlockState pState, ServerLevel pLevel, BlockPos pPos, ItemStack pStack, boolean pDropExperience) {
        if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, pStack) == 0) {
            this.spawnMandrake(pLevel, pPos);
        }
    }
}

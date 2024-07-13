package net.sfedu.ars_maleficarum.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;
import net.sfedu.ars_maleficarum.item.ModItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class MandrakeCropBlock extends CropBlock {

    public boolean is_entity_spawned = false;
    //Максимальная стадия роста (от 0)
    public static final int MAX_AGE = 3;


    //Стадия роста
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public  static final BooleanProperty IS_SPAWNED = BooleanProperty.create("is_spawned");

    //Конструктор, вызывающий конструктор родителя
    public MandrakeCropBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_SPAWNED,false));
    }

    //Семена, необходимые для выращивания
    @Override
    @NotNull
    protected ItemLike getBaseSeedId() {
        return ModItems.MANDRAKE_SEED.get();
    }

    //Текущая стадия роста
    @Override
    @NotNull
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
        pBuilder.add(IS_SPAWNED);
    }
    private void spawnMandrake(ServerLevel pLevel, BlockPos pPos) {
        MandrakeEntity mandrake = ModEntities.MANDRAKE.get().create(pLevel);
        is_entity_spawned = true;
        if (mandrake != null) {
            pLevel.setBlock(pPos,Blocks.AIR.defaultBlockState(),2);
            mandrake.moveTo((double)pPos.getX() + 0.5D, pPos.getY(), (double)pPos.getZ() + 0.5D, 0.0F, 0.0F);
            pLevel.addFreshEntity(mandrake);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public void spawnAfterBreak(BlockState pState, ServerLevel pLevel, BlockPos pPos, ItemStack pStack, boolean pDropExperience) {
        if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, pStack) == 0) {
            int chance_of_spawn = (int)(Math.random()*10);
            if(pLevel.isDay()){
                if(chance_of_spawn!=9 && chance_of_spawn!=10){
                    this.spawnMandrake(pLevel, pPos);
                }
                else{
                    pLevel.getLevel().addFreshEntity(new ItemEntity(pLevel.getLevel(),pPos.getX(),pPos.getY(),pPos.getZ(), new ItemStack(
                            ModItems.MANDRAKE_SEED.get())));
                    pLevel.getLevel().addFreshEntity(new ItemEntity(pLevel.getLevel(),pPos.getX(),pPos.getY(),pPos.getZ(), new ItemStack(
                            ModItems.MANDRAKE_ROOT.get())));

                }
            }
            else{
                if(chance_of_spawn==1 || chance_of_spawn == 2) {
                    this.spawnMandrake(pLevel, pPos);
                }
                else{
                    pLevel.getLevel().addFreshEntity(new ItemEntity(pLevel.getLevel(),pPos.getX(),pPos.getY(),pPos.getZ(), new ItemStack(
                            ModItems.MANDRAKE_SEED.get())));
                    pLevel.getLevel().addFreshEntity(new ItemEntity(pLevel.getLevel(),pPos.getX(),pPos.getY(),pPos.getZ(), new ItemStack(
                            ModItems.MANDRAKE_ROOT.get())));
                }
            }
        }
    }
}

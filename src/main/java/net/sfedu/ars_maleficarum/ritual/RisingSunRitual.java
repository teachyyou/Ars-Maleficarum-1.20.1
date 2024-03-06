package net.sfedu.ars_maleficarum.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.item.ModItems;

import javax.naming.Context;

public class RisingSunRitual extends CircleRitual{

    public RisingSunRitual() {}

    private final String ritualName = "Rite of Rising Sun";

    private NonNullList<Item> components = NonNullList.of(ModItems.RING_OF_MORNING_DEW.get(), ModItems.SUNLIGHT_FLOWER.get(),Items.SUNFLOWER);

    @Override
    public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        System.out.println("execute start");
        if (((RitualCoreEntity) pLevel.getBlockEntity(pPos)).hasAllProperCircles) {
            System.out.println("execute enter if");
            pLevel.getServer().getLevel(Level.OVERWORLD).setDayTime(13000);
            pPlayer.sendSystemMessage(Component.translatable("Voila!!"));
        }
    }

    public boolean doesMatch(SimpleContainer container) {

        for (int i = 0; i < components.size(); i++) {
            boolean flag = false;
            foundItem: for (int j = 0; j < container.getContainerSize(); j++) {
                if (container.getItem(j).is(components.get(i))) {
                    flag = true;
                    break foundItem;
                }
                //System.out.println("does match false");
                //System.out.println(container.getItem(j));

            }
            if (!flag) return false;
        }
        //System.out.println("does match pass");
        return true;
    }
}

package net.sfedu.ars_maleficarum.ritual.ritualTemplates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.ritual.RitualType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class CircleRitual {

    protected enum Dimension {NETHER, OVERWORLD, END, ANY}

    protected RitualCoreEntity.ChalkType smallCircleType;
    protected RitualCoreEntity.ChalkType mediumCircleType;
    protected RitualCoreEntity.ChalkType largeCircleType;
    protected RitualCoreEntity.ChalkType coreType;


    protected boolean allComponentsConsumed = false;

    protected SimpleParticleType particleType = ParticleTypes.WITCH;
    protected SoundEvent itemConsumeSound = SoundEvents.ITEM_PICKUP;
    protected float itemConsumeParticleSpeed = 0.2f;

    protected int ticks = 0;
    protected Class<? extends Entity> sacrificeEntity;
    protected Map<Item, Integer> components = new HashMap<>();

    protected Component ritualName;
    protected RitualType<?> ritualType;

    protected Dimension dimension;

    //for better constructors readability
    protected static final RitualCoreEntity.ChalkType WHITE = RitualCoreEntity.ChalkType.WHITE;
    protected static final RitualCoreEntity.ChalkType NATURAL = RitualCoreEntity.ChalkType.NATURAL;
    protected static final RitualCoreEntity.ChalkType NETHER = RitualCoreEntity.ChalkType.NETHER;
    protected static final RitualCoreEntity.ChalkType ENDER = RitualCoreEntity.ChalkType.ENDER;
    protected static final RitualCoreEntity.ChalkType ANY = RitualCoreEntity.ChalkType.ANY;
    protected static final RitualCoreEntity.ChalkType NONE = RitualCoreEntity.ChalkType.NONE;

    /**
     * Parameters required after the RitualType are types of:
     * Core, small circle, medium circle, large circle.
     */
    public CircleRitual(RitualType<?> type, RitualCoreEntity.ChalkType coreType, RitualCoreEntity.ChalkType smallType, RitualCoreEntity.ChalkType mediumType, RitualCoreEntity.ChalkType largeType) {
        this.coreType = coreType;
        this.smallCircleType = smallType;
        this.mediumCircleType = mediumType;
        this.largeCircleType = largeType;
        this.ritualType=type;
        ritualName = Component.translatable("ritual.ars_maleficarum.rite_of").append(Component.translatable("ritual.ars_maleficarum."+ ritualType.getId().getPath()));

    }
    abstract public void executeRitual(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore);
    public boolean doesMatch(SimpleContainer container) {
        for (Item item : components.keySet()) {
            boolean flag = false;
            for (int j = 0; j < container.getContainerSize(); j++) {
                if (container.getItem(j).is(item) && container.getItem(j).getCount()>=(components.get(item))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return ritualName.getString();
    }

    public Map<Item, Integer> getComponents() {
        return components;
    }

    public boolean hasNoBlocksAbove3x3(Level pLevel, BlockPos pPos) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j<=1; j++) {
                for (int h = 1; h <320; h++) {
                    if (!pLevel.getBlockState(pPos.relative(Direction.Axis.X,i).relative(Direction.Axis.Z,j).above(h)).is(Blocks.AIR)) {
                        System.out.println(pPos.relative(Direction.Axis.X,i).relative(Direction.Axis.Z,j).above(h));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void tryToContinue(Level pLevel, BlockPos pPos, Player pPlayer, RitualCoreEntity riteCore) {
        riteCore.tryStartRitual(pLevel,pPos,pPlayer);
    }

    public void consumeComponents(Level pLevel, BlockPos pPos, RitualCoreEntity riteCore, Player pPlayer) {
        if (ticks%20==0 && !allComponentsConsumed) {
            for (Map.Entry<Item, Integer> requiredItem : components.entrySet()) {
                int amount = requiredItem.getValue();
                if (amount>=1) {
                    ticks=0;
                    Optional<ItemEntity> possiblyItem = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pPos.relative(Direction.Axis.Z,-5).relative(Direction.Axis.X,-5).relative(Direction.Axis.Y,-5),pPos.relative(Direction.Axis.Z,5).relative(Direction.Axis.X,5).relative(Direction.Axis.Y,5)))
                            .stream().filter(x->x.getItem().is(requiredItem.getKey())).findAny();
                    if (possiblyItem.isEmpty()) {
                        pPlayer.sendSystemMessage(Component.translatable("ritual.rite_interrupt_by_components"));
                        riteCore.stopRitual();
                        return;
                    }
                    ItemEntity item = possiblyItem.get();
                    double d0 = item.position().x;
                    double d1 = item.position().y;
                    double d2 = item.position().z;
                    ((ServerLevel)pLevel).sendParticles(particleType, d0, d1, d2, 20, 0,0.5D,0,itemConsumeParticleSpeed);
                    int toTake = Math.min(amount,item.getItem().getCount());
                    components.computeIfPresent(item.getItem().getItem(),(k,v)->v-toTake);
                    item.getItem().shrink(toTake);
                    pLevel.playSound(null, pPos, itemConsumeSound, SoundSource.PLAYERS,1F,1F);
                    break;
                }
            }
            allComponentsConsumed=components.values().stream().allMatch(x->x==0);
        }
    }

    @Override
    public String toString() {
        return getName();
    }


    public RitualCoreEntity.ChalkType getSmallCircleType() {
        return smallCircleType;
    }
    public RitualCoreEntity.ChalkType getMediumCircleType() {
        return mediumCircleType;
    }
    public RitualCoreEntity.ChalkType getLargeCircleType() {
        return largeCircleType;
    }
    public RitualCoreEntity.ChalkType getCoreType() {
        return coreType;
    }
    public boolean doesRequireSmallCircle() {
        return smallCircleType != RitualCoreEntity.ChalkType.NONE;
    }
    public boolean doesRequireMediumCircle() {
        return mediumCircleType != RitualCoreEntity.ChalkType.NONE;
    }
    public boolean doesRequireLargeCircle() {
        return largeCircleType != RitualCoreEntity.ChalkType.NONE;
    }

}

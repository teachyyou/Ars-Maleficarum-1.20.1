package net.sfedu.ars_maleficarum.entity.custom;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.block.ModBlocks;
import net.sfedu.ars_maleficarum.entity.ModEntities;
import net.sfedu.ars_maleficarum.entity.ai.TraderWitchAttackGoal;
import net.sfedu.ars_maleficarum.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class TraderWitchEntity extends AbstractVillager {
    public static final Int2ObjectMap<VillagerTrades.ItemListing[]> CUSTOM_WITCH_TRADES = toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
            new TraderWitchEntity.CustomTrades(ModBlocks.SITE_OF_SUMMONING_CORE_BLOCK.get(), 16, ModItems.MANDRAKE_ROOT.get(), 2,5,2,0.05F)
    }));
    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> pMap) {
        return new Int2ObjectOpenHashMap<>(pMap);
    }
    public TraderWitchEntity(EntityType<? extends AbstractVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(1, new TraderWitchAttackGoal(this, 0.2D,true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.35D));
        this.goalSelector.addGoal(5, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }
    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH,50D).add(Attributes.MOVEMENT_SPEED,0.7D)
                .add(Attributes.FOLLOW_RANGE, 40D).add(Attributes.ATTACK_KNOCKBACK,5D);
    }
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand)
    {
        if(this.isAlive() && !this.isTrading())
        {
            if (pHand == InteractionHand.MAIN_HAND) {
                pPlayer.awardStat(Stats.TALKED_TO_VILLAGER);
            }
            if (this.getOffers().isEmpty()) {
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            else {
                if (!this.level().isClientSide) {
                    this.setTradingPlayer(pPlayer);
                    this.openTradingScreen(pPlayer, this.getDisplayName(), 1);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    @Override
    protected void rewardTradeXp(MerchantOffer pOffer) {
        if (pOffer.shouldRewardExp()) {
            int i = 3 + this.random.nextInt(4);
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5D, this.getZ(), i));

        }
    }

    @Override
    protected void updateTrades() {
        VillagerTrades.ItemListing[] trade_list = CUSTOM_WITCH_TRADES.get(1);
        if(trade_list != null)
        {
            MerchantOffers merchantoffers = this.getOffers();
            this.addOffersFromItemListings(merchantoffers, trade_list, 1);
            int i = this.random.nextInt(trade_list.length);
            VillagerTrades.ItemListing choose_trade_list = trade_list[i];
            MerchantOffer merchantoffer = choose_trade_list.getOffer(this, this.random);
            if (merchantoffer != null) {
                merchantoffers.add(merchantoffer);
            }
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.TRADER_WITCH.get().create(pLevel);
    }
    static class CustomTrades implements VillagerTrades.ItemListing
    {
        private final Item buying_item;
        private final Item sell_for_item;
        private final int cost;
        private final int count_product;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;
        public CustomTrades(ItemLike buying_item,int pCost, ItemLike sell_for_item, int count_product, int pMaxUses, int pVillagerXp, float priceMultiplier)
        {
            this.buying_item = buying_item.asItem();
            this.sell_for_item = sell_for_item.asItem();
            this.cost = pCost;
            this.count_product = count_product;
            this.maxUses = pMaxUses;
            this.villagerXp = pVillagerXp;
            this.priceMultiplier = priceMultiplier;
        }
        @Nullable
        @Override
        public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
            ItemStack buy = new ItemStack(this.buying_item, this.cost);
            ItemStack sell = new ItemStack(this.sell_for_item, this.count_product);
            return new MerchantOffer(buy, sell, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }
}

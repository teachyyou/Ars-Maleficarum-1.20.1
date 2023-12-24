package net.sfedu.ars_maleficarum.mixin;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Creeper;
import net.sfedu.ars_maleficarum.block.custom.InfusingAltarBlock;
import net.sfedu.ars_maleficarum.AI.AvoidBlockGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public class MixinWidget {
    @Inject(method="registerGoals", at = @At("HEAD"),cancellable = true)
    protected void registerGoals1(CallbackInfo ci) {
        PathfinderMob creeper = (PathfinderMob) (Object) this;
        creeper.goalSelector.addGoal(3, new AvoidBlockGoal<>(creeper, InfusingAltarBlock.class, 16.0F, 1.0D, 1.2D));

    }
}

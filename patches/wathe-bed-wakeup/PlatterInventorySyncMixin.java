package cat.rezelyn.watheextended.mixin.fix;

import dev.doctor4t.wathe.block.FoodPlatterBlock;
import dev.doctor4t.wathe.block_entity.BeveragePlateBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodPlatterBlock.class)
public abstract class PlatterInventorySyncMixin {
    @Inject(
            method = "onUse",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void watheextended$syncPlatterPickup(
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            BlockHitResult hit,
            CallbackInfoReturnable<ActionResult> cir) {
        if (world.isClient || !(player instanceof ServerPlayerEntity serverPlayer)) return;
        serverPlayer.playerScreenHandler.syncState();
        cir.setReturnValue(ActionResult.SUCCESS);
    }

    @Inject(method = "onUse", at = @At("RETURN"))
    private void watheextended$syncDeniedPlatterPickup(
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            BlockHitResult hit,
            CallbackInfoReturnable<ActionResult> cir) {
        if (world.isClient || !(player instanceof ServerPlayerEntity serverPlayer)) return;
        if (cir.getReturnValue() != ActionResult.PASS) return;
        if (!(world.getBlockEntity(pos) instanceof BeveragePlateBlockEntity)) return;
        serverPlayer.playerScreenHandler.syncState();
    }
}

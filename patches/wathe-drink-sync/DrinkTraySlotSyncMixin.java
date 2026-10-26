package cat.rezelyn.watheextended.mixin.fix;

import dev.doctor4t.wathe.block.FoodPlatterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodPlatterBlock.class)
public abstract class DrinkTraySlotSyncMixin {
    @Inject(
            method = "onUse",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V",
                    shift = At.Shift.AFTER),
            cancellable = true)
    private void watheextended$syncTakenPlatterItem(
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            BlockHitResult hit,
            CallbackInfoReturnable<ActionResult> cir) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            int selectedSlot = serverPlayer.getInventory().selectedSlot;
            serverPlayer.playerScreenHandler.sendContentUpdates();
            serverPlayer.networkHandler.sendPacket(
                    new ScreenHandlerSlotUpdateS2CPacket(
                            -2, 0, selectedSlot, serverPlayer.getMainHandStack().copy()));
        }
        cir.setReturnValue(ActionResult.SUCCESS);
    }
}

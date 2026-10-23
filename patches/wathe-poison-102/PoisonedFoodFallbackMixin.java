package cat.rezelyn.watheextended.mixin.fix;

import cat.rezelyn.watheextended.WatheExtended;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.index.WatheDataComponentTypes;
import java.util.UUID;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Fallback only when a poisoned food stack did not update the poison timer. */
@Mixin(PlayerEntity.class)
public abstract class PoisonedFoodFallbackMixin {
  @Unique private boolean watheextended$checkPoisonedFood;
  @Unique private int watheextended$previousPoisonTicks;

  @Inject(method = "eatFood", at = @At("HEAD"))
  private void watheextended$rememberPoisonBeforeEating(
      World world, ItemStack stack, FoodComponent food,
      CallbackInfoReturnable<ItemStack> cir) {
    watheextended$checkPoisonedFood = false;
    PlayerEntity self = (PlayerEntity) (Object) this;
    if (world.isClient || !(self instanceof ServerPlayerEntity)) return;
    if (stack.getOrDefault(WatheDataComponentTypes.POISONER, null) == null) return;
    watheextended$previousPoisonTicks = PlayerPoisonComponent.KEY.get(self).poisonTicks;
    watheextended$checkPoisonedFood = true;
  }

  @Inject(method = "eatFood", at = @At("TAIL"))
  private void watheextended$restorePoisonedFoodIfMissing(
      World world, ItemStack stack, FoodComponent food,
      CallbackInfoReturnable<ItemStack> cir) {
    if (!watheextended$checkPoisonedFood) return;
    watheextended$checkPoisonedFood = false;
    PlayerEntity self = (PlayerEntity) (Object) this;
    if (world.isClient || !(self instanceof ServerPlayerEntity)) return;
    PlayerPoisonComponent component = PlayerPoisonComponent.KEY.get(self);
    if (component.poisonTicks != watheextended$previousPoisonTicks) return;

    String storedPoisoner = stack.getOrDefault(WatheDataComponentTypes.POISONER, null);
    if (storedPoisoner == null) return;
    UUID poisoner;
    try {
      poisoner = UUID.fromString(storedPoisoner);
    } catch (IllegalArgumentException exception) {
      WatheExtended.LOGGER.warn("Poisoned-food fallback: invalid poisoner UUID {}", storedPoisoner);
      return;
    }

    int newTicks = watheextended$previousPoisonTicks == -1
        ? world.getRandom().nextBetween(
            PlayerPoisonComponent.clampTime.getLeft(),
            PlayerPoisonComponent.clampTime.getRight())
        : MathHelper.clamp(
            watheextended$previousPoisonTicks - world.getRandom().nextBetween(100, 300),
            0, PlayerPoisonComponent.clampTime.getRight());
    component.setPoisonTicks(newTicks, poisoner);
    WatheExtended.LOGGER.info("Poisoned-food fallback applied to {} (ticks={})",
        self.getName().getString(), newTicks);
  }
}

package cat.rezelyn.watheextended.mixin.fix;

import cat.rezelyn.watheextended.WatheExtended;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.index.WatheDataComponentTypes;
import java.util.UUID;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Poisoned-food safety net on completed item consumption rather than
 * PlayerEntity.eatFood (which other mixins can bypass). This preserves
 * item metadata before it is consumed and avoids double poisoning.
 */
@Mixin(value = ItemStack.class, priority = 900)
public abstract class PoisonedFoodUseMixin {
  @WrapMethod(method = "finishUsing")
  private ItemStack watheextended$onFoodConsumed(
      World world, LivingEntity user, Operation<ItemStack> original) {
    ItemStack stack = (ItemStack) (Object) this;
    String source = stack.getOrDefault(WatheDataComponentTypes.POISONER, null);
    if (source == null || world.isClient || !(user instanceof ServerPlayerEntity player)) {
      return original.call(world, user);
    }

    PlayerPoisonComponent component = PlayerPoisonComponent.KEY.get(player);
    int oldTicks = component.poisonTicks;
    ItemStack result = original.call(world, user);
    if (component.poisonTicks > 0 && component.poisonTicks != oldTicks) return result;

    if (GameWorldComponent.KEY.get(world).getGameStatus()
        != GameWorldComponent.GameStatus.ACTIVE) {
      WatheExtended.LOGGER.info("[Poison102] Poison-tagged food consumed outside ACTIVE round");
      return result;
    }

    UUID poisoner;
    try {
      poisoner = UUID.fromString(source);
    } catch (IllegalArgumentException ex) {
      WatheExtended.LOGGER.warn("[Poison102] Invalid poisoner UUID: {}", source);
      return result;
    }
    int ticks = oldTicks < 0
        ? world.getRandom().nextBetween(PlayerPoisonComponent.clampTime.getLeft(),
            PlayerPoisonComponent.clampTime.getRight())
        : MathHelper.clamp(oldTicks - world.getRandom().nextBetween(100, 300),
            1, PlayerPoisonComponent.clampTime.getRight());

    // Allow Noelle's and Kin's poison-handling rules to run normally.
    component.setPoisonTicks(ticks, poisoner);
    WatheExtended.LOGGER.info(
        "[Poison102] {} ate poisoned food; source={}, old={}, requested={}, actual={}",
        player.getName().getString(), poisoner, oldTicks, ticks, component.poisonTicks);
    return result;
  }
}

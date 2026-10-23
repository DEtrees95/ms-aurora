package cat.rezelyn.watheextended.mixin.fix;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import java.util.UUID;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/** EXPERIMENTAL: protect actual poison from other addons cancelling the timer. */
@Mixin(value = PlayerPoisonComponent.class, priority = 900)
public abstract class RealPoisonTimerGuardMixin {
  @Shadow @Final private PlayerEntity player;
  @Shadow public int poisonTicks;
  @Shadow private int initialPoisonTicks;
  @Shadow public UUID poisoner;
  @Shadow public abstract void sync();

  @Unique
  private boolean watheextended$isRealPoisoner(UUID source) {
    if (source == null || player.getWorld().isClient) return false;
    GameWorldComponent game = GameWorldComponent.KEY.get(player.getWorld());
    if (game.getGameStatus() != GameWorldComponent.GameStatus.ACTIVE) return false;
    Role victimRole = game.getRole(player);
    if (victimRole != null
        && victimRole.identifier().getNamespace().equals("kinswathe")
        && victimRole.identifier().getPath().equals("robot")) return false;
    PlayerEntity attacker = player.getWorld().getPlayerByUuid(source);
    if (attacker == null || !game.canUseKillerFeatures(attacker)) return false;
    Role attackerRole = game.getRole(attacker);
    if (attackerRole != null
        && attackerRole.identifier().getNamespace().equals("noellesroles")
        && attackerRole.identifier().getPath().equals("infected")) return false;
    return GameFunctions.isPlayerAliveAndSurvival(player);
  }

  @WrapMethod(method = "setPoisonTicks")
  private void watheextended$guardSetPoisonTicks(int ticks, UUID source,
      Operation<Void> original) {
    original.call(ticks, source);
    if (ticks <= 0 || !watheextended$isRealPoisoner(source)) return;
    if (poisonTicks <= 0) {
      this.poisoner = source;
      this.poisonTicks = ticks;
      if (this.initialPoisonTicks == 0) this.initialPoisonTicks = ticks;
      sync();
    }
  }

  @WrapMethod(method = "serverTick")
  private void watheextended$guardPoisonTick(Operation<Void> original) {
    int before = this.poisonTicks;
    int initialBefore = this.initialPoisonTicks;
    UUID originalSource = this.poisoner;
    original.call();
    if (before > 1 && this.poisonTicks <= 0
        && watheextended$isRealPoisoner(originalSource)) {
      this.poisoner = originalSource;
      this.poisonTicks = before - 1;
      this.initialPoisonTicks = initialBefore > 0 ? initialBefore : before;
      sync();
    }
  }
}

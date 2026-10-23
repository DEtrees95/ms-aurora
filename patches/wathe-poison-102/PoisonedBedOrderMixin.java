package cat.rezelyn.watheextended.mixin.fix;

import dev.doctor4t.wathe.block_entity.TrimmedBedBlockEntity;
import dev.doctor4t.wathe.util.PoisonUtils;
import java.util.UUID;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** WATHE 1.4 clears the scorpion poisoner's UUID before reading it. */
@Mixin(value = PoisonUtils.class, remap = false)
public abstract class PoisonedBedOrderMixin {
  @Redirect(
      method = "bedPoison",
      at = @At(value = "INVOKE",
          target = "Ldev/doctor4t/wathe/block_entity/TrimmedBedBlockEntity;setHasScorpion(ZLjava/util/UUID;)V"),
      remap = false)
  private static void watheextended$delayScorpionRemoval(
      TrimmedBedBlockEntity bed, boolean hasScorpion, UUID poisoner) {
    // Read the UUID first; clear after getPoisoner() is called.
  }

  @Redirect(
      method = "bedPoison",
      at = @At(value = "INVOKE",
          target = "Ldev/doctor4t/wathe/block_entity/TrimmedBedBlockEntity;getPoisoner()Ljava/util/UUID;"),
      remap = false)
  private static UUID watheextended$readPoisonerBeforeRemoval(TrimmedBedBlockEntity bed) {
    UUID poisoner = bed.getPoisoner();
    bed.setHasScorpion(false, null);
    return poisoner;
  }
}

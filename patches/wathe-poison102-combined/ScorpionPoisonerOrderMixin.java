package cat.rezelyn.watheextended.mixin.fix;

import dev.doctor4t.wathe.block_entity.TrimmedBedBlockEntity;
import dev.doctor4t.wathe.util.PoisonUtils;
import java.util.UUID;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Wathe 1.4 bedPoison calls setHasScorpion(false, null) before getPoisoner(),
 * erasing the UUID. Defer cleanup until the UUID has been retrieved.
 *
 * Keep the original poisoned-bed behavior and scorpion consumption intact.
 */
@Mixin(value = PoisonUtils.class, remap = false)
public abstract class ScorpionPoisonerOrderMixin {
  @Redirect(
      method = "bedPoison",
      at = @At(value = "INVOKE",
          target = "Ldev/doctor4t/wathe/block_entity/TrimmedBedBlockEntity;setHasScorpion(ZLjava/util/UUID;)V"),
      remap = false)
  private static void watheextended$deferScorpionRemoval(
      TrimmedBedBlockEntity bed, boolean hasScorpion, UUID poisoner) {
    // The base method was clearing poisoner before reading it.
    // Cleanup is performed immediately after getPoisoner() instead.
  }

  @Redirect(
      method = "bedPoison",
      at = @At(value = "INVOKE",
          target = "Ldev/doctor4t/wathe/block_entity/TrimmedBedBlockEntity;getPoisoner()Ljava/util/UUID;"),
      remap = false)
  private static UUID watheextended$readPoisonerThenRemove(TrimmedBedBlockEntity bed) {
    UUID savedPoisoner = bed.getPoisoner();
    bed.setHasScorpion(false, null);
    return savedPoisoner;
  }
}

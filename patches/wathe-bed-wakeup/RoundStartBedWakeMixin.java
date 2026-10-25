package cat.rezelyn.watheextended.mixin.fix;

import cat.rezelyn.watheextended.WatheExtended;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import java.util.List;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Experimental round-start bed fix for Wathe 1.4 / Fabric 1.21.1.
 *
 * The base game moves ready players from the preparation room to the train
 * while they may still be sleeping. Waking up later restores the original
 * lobby bed position, below the train play area; Wathe then eliminates the
 * player as having fallen off the train. Explicitly end sleep BEFORE the
 * teleport, role assignment and train reset.
 *
 * No changes to sleep during an active round.
 */
@Mixin(GameFunctions.class)
public abstract class RoundStartBedWakeMixin {
    @Inject(method = "baseInitialize", at = @At("HEAD"))
    private static void msAurora$wakeSleepingPlayers(
            ServerWorld world,
            GameWorldComponent component,
            List<ServerPlayerEntity> readyPlayers,
            CallbackInfo ci) {
        // Include every player: ready and spectators alike may still be sleeping.
        // The ready-player list was determined by position before this method.
        for (ServerPlayerEntity player : world.getPlayers()) {
            if (!player.isSleeping()) continue;
            WatheExtended.LOGGER.info("[BedWakeTest] Waking {} before round-start teleport",
                    player.getName().getString());
            player.wakeUp();
            if (player.isSleeping()) {
                WatheExtended.LOGGER.warn("[BedWakeTest] {} still sleeping after wakeUp()",
                        player.getName().getString());
            }
        }
    }
}

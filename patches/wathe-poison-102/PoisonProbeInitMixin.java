package cat.rezelyn.watheextended.mixin.fix;

import cat.rezelyn.watheextended.WatheExtended;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Explicit version marker and OP-only direct poison diagnostic for issue #102. */
@Mixin(WatheExtended.class)
public abstract class PoisonProbeInitMixin {
  @Inject(method = "onInitialize", at = @At("TAIL"))
  private void watheextended$poisonProbeInit(CallbackInfo ci) {
    WatheExtended.LOGGER.warn("[Poison102] V3 probe loaded: use watheextended:poisonProbe DEtrees95 1200");
    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
        dispatcher.register(
            CommandManager.literal("watheextended:poisonProbe")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("target", EntityArgumentType.player())
                    .then(CommandManager.argument("ticks", IntegerArgumentType.integer(1, 1400))
                        .executes(context -> {
                          ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
                          int ticks = IntegerArgumentType.getInteger(context, "ticks");
                          PlayerPoisonComponent component = PlayerPoisonComponent.KEY.get(target);
                          int before = component.poisonTicks;
                          component.setPoisonTicks(ticks, target.getUuid());
                          int after = component.poisonTicks;
                          WatheExtended.LOGGER.warn(
                              "[Poison102] V3 poisonProbe target={} requested={} before={} after={}",
                              target.getName().getString(), ticks, before, after);
                          context.getSource().sendFeedback(
                              () -> Text.literal("Poison102: before=" + before
                                  + ", requested=" + ticks + ", after=" + after), false);
                          return 1;
                        })))));
  }
}

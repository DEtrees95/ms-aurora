package cat.rezelyn.watheextended.mixin.fix;

import dev.doctor4t.wathe.block.entity.SeatEntity;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Avoid dismounting passengers below the floor when the seat entity is
 * positioned below its mounting block. Validates standing room and nearby
 * support before choosing an exit location.
 */
@Mixin(SeatEntity.class)
public abstract class SeatSafeDismountMixin extends Entity {
  private static final int[][] DISMOUNT_OFFSETS = {
      {0, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, 0},
      {1, 1}, {1, -1}, {-1, -1}, {-1, 1}
  };

  protected SeatSafeDismountMixin(EntityType<?> type, World world) {
    super(type, world);
  }

  @Override
  public Vec3d updatePassengerForDismount(LivingEntity passenger) {
    World world = getWorld();
    if (world.isClient) {
      return super.updatePassengerForDismount(passenger);
    }

    BlockPos seatPos = ((SeatEntity) (Object) this).getSeatPos();
    if (seatPos == null) {
      return super.updatePassengerForDismount(passenger);
    }

    for (int[] offset : DISMOUNT_OFFSETS) {
      BlockPos floor = seatPos.add(offset[0], 0, offset[1]);
      BlockPos lowerFloor = floor.down();
      boolean hasSupport =
          !world.getBlockState(floor).getCollisionShape(world, floor).isEmpty()
              || !world.getBlockState(lowerFloor).getCollisionShape(world, lowerFloor).isEmpty();
      if (!hasSupport) {
        continue;
      }

      Vec3d candidate = Vec3d.ofBottomCenter(floor.up());
      if (Dismounting.canPlaceEntityAt(world, candidate, passenger, EntityPose.STANDING)) {
        return candidate;
      }
    }

    return super.updatePassengerForDismount(passenger);
  }
}

package net.muffin.mobeffects.event;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

/**
 * Callback for right-clicking ("using") your hand (no item).
 * Is hooked in before the spectator check, so make sure to check for the player's game mode as well!
 *
 * <p>Upon return:
 * <ul><li>SUCCESS cancels further processing and, on the client, sends a packet to the server.
 * <li>PASS falls back to further processing.
 * <li>FAIL cancels further processing and does not send a packet to the server.</ul>
 */
public interface UseHandCallback {
    Event<UseHandCallback> EVENT = EventFactory.createArrayBacked(UseHandCallback.class,
            listeners -> (player, world, target) -> {
                for (UseHandCallback event : listeners) {
                    ActionResult result = event.interact(player, world, target);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            }
    );

    ActionResult interact(PlayerEntity player, World world, HitResult target);
}

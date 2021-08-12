package napi.gui.api;

import napi.gui.api.action.ActionContext;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Controller handles various inventory events
 * and can manipulate with Window
 */
public interface Controller {

    /**
     * Called before window opened
     * @param window Window that will be opened
     * @param player Player who opened window
     */
    default void onWindowOpen(Window window, Player player) { }

    /**
     * Called after window opened
     * @param window Window that opened
     * @param player Player who opened window
     */
    default void onWindowOpened(Window window, Player player) { }

    /**
     * Called after window will be closed
     * @param window Window that closed
     * @param player Player who closed window
     */
    default void onWindowClosed(Window window, Player player) { }

    /**
     * Called when player produces click on inventory window.
     * This method called before executing item action.
     * If context cancelled, then action, associated with
     * clicked item won't be executed.
     *
     * Cancelling action and cancelling event is different things.
     * @param ctx Click context
     */
    default void onClick(ActionContext ctx) { }

    /**
     * Called when item placed in empty slot of window's inventory
     * @param window Current window
     * @param slot Placed slot
     * @param stack Item that placed in empty slot
     */
    default void onItemPlaced(Window window, int slot, ItemStack stack) { }

    /**
     * Called when item taken from window's inventory
     * @param window Current window
     * @param slot Taken item slot
     * @param stack Item that taken from inventory
     */
    default void onItemTaken(Window window, int slot, ItemStack stack) { }
}

package napi.gui.api;

import napi.gui.api.action.ActionContext;
import org.bukkit.entity.Player;

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

}

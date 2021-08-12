package napi.gui.api;

import napi.gui.api.item.Item;
import napi.gui.api.item.ItemsList;
import napi.gui.api.item.Slot;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Represents a GUI window
 */
public interface Window extends InventoryHolder {

    /**
     * Get window template
     * @return Window template
     */
    Template template();

    /**
     * Get current items, placed in inventory
     * @return Inventory items collection
     */
    ItemsList items();

    /**
     * Get controller of this window
     * @return Window controller
     */
    Controller controller();

    /**
     * Get Window's inventory
     * @return Inventory instance
     */
    Inventory inventory();

    /**
     * Open this window for player
     * @param player Player for who menu opened
     */
    void open(Player player);

    /**
     * Close this window for player who opened it
     */
    void close(Player player);

    /**
     * Process inventory click
     * @param event Click event
     */
    void click(InventoryClickEvent event);

    /**
     * Redraw all items
     */
    void redraw();

    /**
     * Redraw item
     * @param item Item instance
     */
    void redraw(Item item);

    /**
     * Redraw item in specified slot, if it exists
     * @param slot Item's slot
     */
    void redraw(Slot slot);

}

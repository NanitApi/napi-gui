package napi.gui.api.item;

import napi.gui.api.action.Action;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public interface Item extends Cloneable {

    /**
     * Get item slot
     * @return Item slot
     */
    Slot slot();

    /**
     * Get ItemStack of this item
     * @return ItemStack instance
     */
    ItemStack stack();

    /**
     * Get associated click action, if it present
     * @return Click action
     */
    Optional<Action> action();

    /**
     * Is this item fixed and cannot be moved from inventory.
     * If item fixed, then InventoryClickEvent will be cancelled to prevent item moving.
     * By default, true
     * @return Is item fixed
     */
    boolean isFixed();

    /**
     * Set item to be fixed or not
     * @param fixed Fixed value
     */
    void fixed(boolean fixed);

    Item clone();

}

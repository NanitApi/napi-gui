package napi.gui.item;

import napi.gui.api.action.Action;
import napi.gui.api.item.Item;
import napi.gui.api.item.Slot;
import org.bukkit.inventory.ItemStack;

/**
 * Items factory
 */
public final class Items {

    private Items() { }

    /**
     * Create item without action
     * @param slot Item slot
     * @param stack ItemStack instance
     * @return Created item
     */
    public static Item create(Slot slot, ItemStack stack) {
        return builder()
                .slot(slot)
                .stack(stack)
                .build();
    }

    /**
     * Create item with associated click action
     * @param slot Item slot
     * @param stack ItemStack instance
     * @param action Click action for this item
     * @return Created item
     */
    public static Item create(Slot slot, ItemStack stack, Action action) {
        return builder()
                .slot(slot)
                .stack(stack)
                .action(action)
                .build();
    }

    /**
     * Get the builder for item
     * @return Item builder
     */
    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

}

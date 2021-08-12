package napi.gui.item;

import com.google.common.base.Preconditions;
import napi.gui.api.action.Action;
import napi.gui.api.item.Item;
import napi.gui.api.item.Slot;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder {

    private Slot slot;
    private ItemStack stack;
    private Action action;
    private boolean fixed = true;

    public ItemBuilder slot(Slot slot) {
        this.slot = slot;
        return this;
    }

    public ItemBuilder stack(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    public ItemBuilder action(Action action) {
        this.action = action;
        return this;
    }

    public ItemBuilder fixed(boolean fixed) {
        this.fixed = fixed;
        return this;
    }

    public Item build() {
        Preconditions.checkNotNull(slot, "Item slot cannot be null");
        Preconditions.checkNotNull(stack, "Item stack cannot be null");
        return new InventoryItem(slot, stack, action, fixed);
    }

}

package napi.gui.item;

import napi.gui.api.action.Action;
import napi.gui.api.item.Item;
import napi.gui.api.item.Slot;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class InventoryItem implements Item {

    private final Slot slot;
    private final Action action;
    private ItemStack stack;

    InventoryItem(Slot slot, ItemStack stack, Action action) {
        this.slot = slot;
        this.stack = stack;
        this.action = action;
    }

    @Override
    public Slot slot() {
        return slot;
    }

    @Override
    public ItemStack stack() {
        return stack;
    }

    @Override
    public Optional<Action> action() {
        return Optional.ofNullable(action);
    }

    @Override
    public Item clone() {
        try {
            InventoryItem item = (InventoryItem) super.clone();
            item.stack = stack.clone();
            return item;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

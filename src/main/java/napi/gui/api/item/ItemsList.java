package napi.gui.api.item;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Always mutable list of inventory items
 */
public class ItemsList implements Iterable<Item> {

    private final Map<Integer, Item> items;

    public ItemsList() {
        items = new HashMap<>();
    }

    public ItemsList(Iterable<Item> items) {
        this();
        merge(items);
    }

    public Item get(int index) {
        return items.get(index);
    }

    public void add(Item item) {
        item.slot().apply(index -> items.put(index, item));
    }

    public void remove(Item item) {
        remove(item.slot());
    }

    public void remove(Slot slot) {
        slot.apply(this::remove);
    }

    public void remove(int index) {
        items.remove(index);
    }

    public void merge(Iterable<Item> items) {
        items.forEach(item -> add(item.clone()));
    }

    @Override
    public Iterator<Item> iterator() {
        return items.values().iterator();
    }

    public static ItemsList empty() {
        return new ItemsList();
    }
}

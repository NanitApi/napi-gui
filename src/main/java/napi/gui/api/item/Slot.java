package napi.gui.api.item;

import java.util.function.Consumer;

@FunctionalInterface
public interface Slot {
    
    /**
     * Get computed slot indexes in callback
     * @param slots Slot index callback
     */
    void apply(Consumer<Integer> slots);

}

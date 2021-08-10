package napi.gui.slot;

import napi.gui.api.inventory.Slot;

import java.util.function.Consumer;

public class SlotIndex implements Slot {

    private final int slot;

    public SlotIndex(int slot) {
        this.slot = slot;
    }

    @Override
    public void apply(Consumer<Integer> slots) {
        slots.accept(slot);
    }
    
}

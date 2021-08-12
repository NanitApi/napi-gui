package napi.gui.slot;

import napi.gui.api.item.Slot;

import java.util.function.Consumer;

public class SlotRange implements Slot {

    private final int min;
    private final int max;

    public SlotRange(int min, int max) {
        this.min = Math.max(min, 0);
        this.max = max;
    }

    @Override
    public void apply(Consumer<Integer> slots) {
        for (int i = min; i <= max; i++) {
            slots.accept(i);
        }
    }

}

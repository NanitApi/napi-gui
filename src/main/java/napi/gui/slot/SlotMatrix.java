package napi.gui.slot;

import napi.gui.api.inventory.Slot;

import java.util.function.Consumer;

public class SlotMatrix implements Slot {

    private final Iterable<String> matrix;

    public SlotMatrix(Iterable<String> matrix) {
        this.matrix = matrix;
    }

    @Override
    public void apply(Consumer<Integer> slots) {

    }
}

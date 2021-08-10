package napi.gui.slot;

import napi.gui.api.inventory.Slot;

import java.util.Arrays;

/**
 * Slot factory
 */
public final class Slots {

    private Slots() { }

    public static Slot index(int index) {
        return new SlotIndex(index);
    }

    public static Slot range(int start, int end) {
        return new SlotRange(start, end);
    }

    public static Slot matrix(String... matrix) {
        return new SlotMatrix(Arrays.asList(matrix));
    }

    public static Slot matrix(Iterable<String> matrix) {
        return new SlotMatrix(matrix);
    }

}

package napi.gui.slot;

import napi.gui.api.item.Slot;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

public class SlotMatrix implements Slot {

    private final Collection<Integer> slots;

    public SlotMatrix(Iterable<String> matrix) {
        slots = new LinkedList<>();
        int rowNum = 0;

        for (String row : matrix) {
            int slot = rowNum * 9;

            for (char cell : row.toCharArray()) {
                if (cell != '-')
                    slots.add(slot);
                slot++;
            }

            rowNum++;
        }
    }

    @Override
    public void apply(Consumer<Integer> slots) {
        this.slots.forEach(slots);
    }
}

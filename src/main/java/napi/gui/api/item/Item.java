package napi.gui.api.item;

import napi.gui.api.action.Action;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public interface Item extends Cloneable {

    Slot slot();

    ItemStack stack();

    Optional<Action> action();

    Item clone();

}

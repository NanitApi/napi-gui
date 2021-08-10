package napi.gui.api.inventory;

import org.bukkit.inventory.ItemStack;

public interface Item {

    Slot slot();

    ItemStack stack();

}

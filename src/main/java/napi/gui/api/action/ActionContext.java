package napi.gui.api.action;

import napi.gui.api.Window;
import napi.gui.api.item.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Optional;

/**
 * Contains data related to inventory action
 */
public class ActionContext {

    private final ClickType clickType;
    private final InventoryClickEvent event;
    private final Item clickedItem;
    private final Player player;
    private final Window window;
    private boolean cancelled;

    public ActionContext(ClickType clickType, InventoryClickEvent event, Player player, Window window, Item clickedItem) {
        this.clickType = clickType;
        this.event = event;
        this.player = player;
        this.window = window;
        this.clickedItem = clickedItem;
    }

    public Player player() {
        return player;
    }

    public Window window() {
        return window;
    }

    public Optional<Item> clickedItem() {
        return Optional.ofNullable(clickedItem);
    }

    public ClickType clickType() {
        return clickType;
    }

    public InventoryClickEvent event() {
        return event;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

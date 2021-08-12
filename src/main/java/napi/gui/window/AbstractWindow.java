package napi.gui.window;

import napi.gui.api.Controller;
import napi.gui.api.Template;
import napi.gui.api.Window;
import napi.gui.api.action.ActionContext;
import napi.gui.api.item.Item;
import napi.gui.api.item.ItemsList;
import napi.gui.api.item.Slot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractWindow implements Window {

    private final Template template;
    private final Controller controller;
    private final Inventory inventory;
    private final ItemsList items;

    private Player player;

    public AbstractWindow(Template template) {
        this.template = template;
        this.controller = template.controller();
        inventory = Bukkit.createInventory(this, template.rows() * 9, template.title());
        items = new ItemsList(template.items());
    }

    @Override
    public Template template() {
        return template;
    }

    @Override
    public ItemsList items() {
        return items;
    }

    @Override
    public Controller controller() {
        return controller;
    }

    @Override
    public Inventory inventory() {
        return inventory;
    }

    @Override
    public void open(Player player) {
        this.player = player;
        controller.onWindowOpen(this, player);
        player.getOpenInventory();
        player.openInventory(inventory);
        controller.onWindowOpened(this, player);
    }

    @Override
    public void close() {
        if (player != null) {
            try {
                player.closeInventory();
            } catch (Throwable t) { /* Ignore */ }
            controller.onWindowClosed(this, player);
        }
    }

    @Override
    public void click(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();
        Item item = items().get(event.getSlot());
        ActionContext ctx = new ActionContext(event.getClick(), event, clicker, this, item);

        controller.onClick(ctx);

        if (item != null && !ctx.isCancelled())
            item.action().ifPresent(action -> action.execute(ctx));
    }

    @Override
    public void redraw() {
        items.forEach(this::redraw);
    }

    @Override
    public void redraw(Item item) {
        item.slot().apply(index -> redraw(index, item.stack()));
    }

    @Override
    public void redraw(Slot slot) {
        slot.apply(index -> {
            Item item = items.get(index);
            if (item != null)
                redraw(index, item.stack());
        });
    }

    private void redraw(int index, ItemStack stack) {
        inventory.setItem(index, stack);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}

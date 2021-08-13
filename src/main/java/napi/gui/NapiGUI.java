package napi.gui;

import com.google.common.base.Preconditions;
import napi.gui.api.Controller;
import napi.gui.api.item.ItemsList;
import napi.gui.api.Template;
import napi.gui.api.Window;
import napi.gui.window.BaseTemplate;
import napi.gui.window.BaseWindow;
import napi.gui.window.DefaultController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

public final class NapiGUI implements Listener {

    public NapiGUI(Plugin plugin) {
        plugin.getServer()
                .getPluginManager()
                .registerEvents(this, plugin);
    }

    /**
     * Create GUI template with empty items list
     * @param title Menu title
     * @param rows Inventory rows number
     * @return Created template
     */
    public Template createTemplate(String title, int rows) {
        return createTemplate(title, rows, new DefaultController(), ItemsList.empty());
    }

    /**
     * Create GUI template with empty items list
     * @param title Menu title
     * @param rows Inventory rows number
     * @param controller Controller for windows, created by this template
     * @return Created template
     */
    public Template createTemplate(String title, int rows, Controller controller) {
        return createTemplate(title, rows, controller, ItemsList.empty());
    }

    /**
     * Create GUI template with basic items list
     * @param title Menu title
     * @param rows Inventory rows number
     * @param controller Controller for windows, created by this template
     * @param items Basic items list for this template.
     *              This items will be placed when Window will be created by this template
     * @return Created template
     */
    public Template createTemplate(String title, int rows, Controller controller, ItemsList items) {
        Preconditions.checkArgument(rows > 0 && rows < 7,
                "Menu rows number must be between 1 and 6 inclusive");
        return new BaseTemplate(title, rows, controller, items);
    }

    /**
     * Create GUI window by template
     * @param template Template instance
     * @return Created window
     */
    public Window createWindow(Template template) {
        return new BaseWindow(template);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null
                && event.getClickedInventory().getHolder() instanceof Window) {
            Window window = (Window) event.getClickedInventory().getHolder();
            window.click(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player
                && event.getInventory().getHolder() instanceof Window) {
            Window window = (Window) event.getInventory().getHolder();
            window.controller().onWindowClosed(window, (Player) event.getPlayer());
        }
    }
}

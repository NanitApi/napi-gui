package napi.gui.example;

import napi.gui.api.Controller;
import napi.gui.api.Window;
import napi.gui.api.action.ActionContext;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestController implements Controller {

    @Override
    public void onWindowOpened(Window window, Player player) {
        System.out.println("Opened menu");
    }

    @Override
    public void onWindowClosed(Window window, Player player) {
        System.out.println("Closed menu");
    }

    @Override
    public void onClick(ActionContext ctx) {
        System.out.println("Click on " + ctx.window());
    }

    @Override
    public void onItemPlaced(Window window, int slot, ItemStack stack) {
        System.out.println("Placed item " + stack + " in " + slot);
    }

    @Override
    public void onItemTaken(Window window, int slot, ItemStack stack) {
        System.out.println("Taken item " + stack + " from " + slot);
    }
}

package napi.gui.example;

import napi.gui.NapiGUI;
import napi.gui.api.Template;
import napi.gui.api.Window;
import napi.gui.item.Items;
import napi.gui.slot.Slots;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private NapiGUI gui;
    private Template tmpl;

    @Override
    public void onEnable() {
        gui = new NapiGUI(this);

        tmpl = gui.createTemplate("Hello", 3, new TestController());

        tmpl.items().add(Items.builder()
                .slot(Slots.matrix(
                        "xxxxxxxxx",
                        "x-------x",
                        "xxxxxxxxx"
                ))
                .stack(new ItemStack(Material.CAKE))
                .build());

        tmpl.items().add(Items.builder()
                .slot(Slots.index(11))
                .stack(new ItemStack(Material.ARROW))
                .action(ctx -> System.out.println("Clicked on button"))
                .build());

        tmpl.items().add(Items.builder()
                .slot(Slots.index(13))
                .stack(new ItemStack(Material.STONE))
                .action(ctx -> System.out.println("Clicked on movable button"))
                .fixed(false)
                .build());

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) {
            Window window = gui.createWindow(tmpl);
            window.open(event.getPlayer());
        }
    }

}

package napi.gui.example;

import com.google.common.reflect.TypeToken;
import napi.configurate.yaml.Configuration;
import napi.configurate.yaml.source.ConfigSources;
import napi.gui.NapiGUI;
import napi.gui.api.Template;
import napi.gui.api.Window;
import napi.gui.item.Items;
import napi.gui.serialize.ItemSerializer;
import napi.gui.slot.Slots;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin implements Listener {

    private NapiGUI gui;
    private Template tmpl;

    @Override
    public void onEnable() {
        Configuration conf = Configuration.builder()
                .source(ConfigSources.resource("/config.yml", this)
                        .copyTo(getDataFolder().getPath()))
                .serializer(ItemStack.class, new ItemSerializer())
                .build();

        try {
            conf.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gui = new NapiGUI(this);

        tmpl = gui.createTemplate("Hello", 3, new TestController());

        ItemStack loadedItem = null;

        try {
            loadedItem = conf.getNode("item_cake").getValue(TypeToken.of(ItemStack.class));
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        tmpl.items().add(Items.builder()
                .slot(Slots.index(11))
                .stack(loadedItem)
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

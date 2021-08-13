package napi.gui.serialize;

import com.google.common.reflect.TypeToken;
import napi.configurate.yaml.util.ConfigUtil;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;

public class ItemSerializer implements TypeSerializer<ItemStack> {

    @Override
    public @Nullable ItemStack deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode node) throws ObjectMappingException {
        ItemParser parser = new ItemParser();
        Map<String, Object> map = ConfigUtil.mapValues(node);

        try {
            return parser.parse(map);
        } catch (Exception e) {
            throw new ObjectMappingException(e);
        }
    }

    @Override
    public void serialize(@NonNull TypeToken<?> type, @Nullable ItemStack obj, @NonNull ConfigurationNode node) throws ObjectMappingException {

    }
}

package napi.gui.item;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemParser {

    private MethodHandle getMatIdMethod;

    public ItemParser() {
        try {
            Method getMatId = Material.class.getMethod("getMaterial", int.class);
            getMatIdMethod = MethodHandles.lookup().unreflect(getMatId);
        } catch (Throwable t) {
            // Ignore
        }
    }

    public ItemStack parse(Map<String, Object> map) throws Exception {
        Material type = getMaterial(map.get("type"));
        byte data = (byte) map.getOrDefault("data", 0);
        int amount = (int) map.getOrDefault("amount", 1);
        short damage = (short) map.getOrDefault("damage", 0);

        ItemStack item;

        try {
            item = new ItemStack(type, amount, damage, data);
        } catch (Throwable t) {
            item = new ItemStack(type, amount, damage);
        }

        if (item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();

            if (map.containsKey("name"))
                meta.setDisplayName(map.get("name").toString());

            if (map.containsKey("lore"))
                meta.setLore((List<String>) map.get("lore"));

            if (map.containsKey("enchantments")) {
                Map<String, Object> enchMap = (Map<String, Object>) map.get("enchantments");

                for (Map.Entry<String, Object> entry : enchMap.entrySet()) {
                    Enchantment enchantment = Enchantment.getByName(entry.getKey().toUpperCase());
                    if (enchantment == null)
                        throw new IllegalArgumentException("Enchantment with name "+entry.getKey()+" not found");
                    int level = (int) entry.getValue();
                    meta.addEnchant(enchantment, level, true);
                }
            }

            if (map.containsKey("flags")) {
                List<String> flags = (List<String>) map.get("flags");
                for (String flag : flags) {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                }
            }

            if (map.containsKey("skull_owner") && meta instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) meta;
                String owner = map.get("skull_owner").toString();

                try {
                    skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
                } catch (Throwable t) {
                    skullMeta.setOwner(owner);
                }
            }

            if (map.containsKey("color")) {
                Color color = getColor(map.get("color").toString());
                if (meta instanceof LeatherArmorMeta){
                    ((LeatherArmorMeta)meta).setColor(color);
                } else if (meta instanceof PotionMeta){
                    ((PotionMeta)meta).setColor(color);
                }
            }

            if (map.containsKey("model")) {
                meta.setCustomModelData((int) map.get("model"));
            }

            if (map.containsKey("potion") && meta instanceof PotionMeta) {
                List<PotionEffect> effects = getPotionData((List<Object>) map.get("potion"));
                for(PotionEffect effect : effects) {
                    ((PotionMeta) meta).addCustomEffect(effect, true);
                }
            }

            if (map.containsKey("firework")) {
                // TODO
            }

            if (map.containsKey("book")) {
                // TODO
            }

            if (map.containsKey("banner")) {
                // TODO
            }

            meta.setUnbreakable((boolean) map.getOrDefault("unbreakable", false));

            item.setItemMeta(meta);
        }

        return item;
    }

    private Material getMaterial(Object id) {
        if (id instanceof Number) {
            try {
                return (Material) getMatIdMethod.invoke((int) id);
            } catch (Throwable t) {
                // Ignore
            }
        }
        return Material.getMaterial(id.toString().toUpperCase());
    }

    private Color getColor(String color) {
        String str = color;

        if(str.charAt(0) == '#') {
            str = str.substring(1);
            int rgb = Integer.parseInt(str, 16);
            return Color.fromRGB(rgb);
        }

        return DyeColor.valueOf(color).getColor();
    }

    private List<PotionEffect> getPotionData(List<Object> list) {
        List<PotionEffect> effects = new ArrayList<>();

        for (Object obj : list) {
            Map<String, Object> map = (Map<String, Object>) obj;

            PotionEffectType type = PotionEffectType.getByName(map.get("type").toString().toUpperCase());

            if (type == null)
                throw new IllegalArgumentException("Potion effect type not found");

            int amplifier = (int) map.get("amplifier");
            int duration = (int) map.get("duration");
            boolean ambient = (boolean) map.getOrDefault("ambient", true);
            boolean particles = (boolean) map.getOrDefault("particles", true);
            boolean icon = (boolean) map.getOrDefault("icon", true);

            effects.add(new PotionEffect(type, amplifier, duration, ambient, particles, icon));
        }

        return effects;
    }

}

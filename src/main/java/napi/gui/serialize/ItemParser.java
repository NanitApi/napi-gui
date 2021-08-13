package napi.gui.serialize;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import napi.gui.api.Colors;
import napi.gui.item.Skulls;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        byte data = new Integer((int)map.getOrDefault("data", 0)).byteValue();
        int amount = (int) map.getOrDefault("amount", 1);
        short damage = new Integer((int) map.getOrDefault("damage", 0)).shortValue();

        ItemStack item;

        try {
            item = new ItemStack(type, amount, damage, data);
        } catch (Throwable t) {
            item = new ItemStack(type, amount, damage);
        }

        if (item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();

            if (map.containsKey("name"))
                meta.setDisplayName(Colors.of(map.get("name").toString()));

            if (map.containsKey("lore"))
                meta.setLore(Colors.ofList((List<String>) map.get("lore")));

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

            if (map.containsKey("texture") && meta instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) meta;
                String url = map.get("texture").toString();
                Skulls.setTexture(skullMeta, url);
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

            if (map.containsKey("firework") && meta instanceof FireworkMeta) {
                FireworkMeta fireworkMeta = (FireworkMeta) meta;
                Map<String, Object> fmap = (Map<String, Object>) map.get("firework");
                List<FireworkEffect> effects = getFireworkEffects((List<Object>) map.get("potion"));
                int power = (int) fmap.getOrDefault("power", 1);

                fireworkMeta.addEffects(effects);
                fireworkMeta.setPower(power);
            }

            if (map.containsKey("book") && meta instanceof BookMeta) {
                BookMeta bookMeta = (BookMeta) meta;
                Map<String, Object> bookMap = (Map<String, Object>) map.get("book");
                String title = Colors.of(bookMap.get("title").toString());
                String author = Colors.of(bookMap.get("author").toString());
                List<String> pages = Colors.ofList((List<String>) bookMap.get("pages"));

                bookMeta.setTitle(title);
                bookMeta.setAuthor(author);
                bookMeta.setPages(pages);
            }

            if (map.containsKey("banner") && meta instanceof BannerMeta) {
                BannerMeta bannerMeta = (BannerMeta) meta;
                List<Pattern> patterns = getBannerData(map.get("banner").toString());
                bannerMeta.setPatterns(patterns);
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

    private List<Pattern> getBannerData(String json) {
        List<Pattern> patterns = new ArrayList<>();
        JsonObject root = new JsonParser().parse(json).getAsJsonObject();
        JsonArray patternsArray = root.get("BlockEntityTag").getAsJsonObject()
                .get("Patterns").getAsJsonArray();

        for (JsonElement element : patternsArray){
            JsonObject object = element.getAsJsonObject();
            String pattern = object.get("Pattern").getAsString();
            byte colorByte = object.get("Color").getAsByte();

            PatternType patternType = PatternType.getByIdentifier(pattern);
            DyeColor color = DyeColor.getByWoolData(colorByte);

            if (patternType == null)
                throw new IllegalArgumentException("Cannot serialize banner pattern from JSON");
            if (color == null)
                throw new IllegalArgumentException("Cannot serialize banner color from JSON");

            patterns.add(new Pattern(color, patternType));
        }

        return patterns;
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

    private List<FireworkEffect> getFireworkEffects(List<Object> list) {
        List<FireworkEffect> effects = new LinkedList<>();

        for (Object obj : list) {
            Map<String, Object> map = (Map<String, Object>) obj;
            effects.add(getFireworkEffect(map));
        }

        return effects;
    }

    private FireworkEffect getFireworkEffect(Map<String, Object> map) {
        FireworkEffect.Builder builder = FireworkEffect.builder();

        if(map.containsKey("colors")) {
            List<String> list = (List<String>) map.get("colors");
            List<Color> colors = list.stream()
                    .map(this::getColor)
                    .collect(Collectors.toList());
            builder.withColor(colors);
        }

        if(map.containsKey("fade_colors")) {
            List<String> list = (List<String>) map.get("fadeColors");
            List<Color> colors = list.stream()
                    .map(this::getColor)
                    .collect(Collectors.toList());
            builder.withFade(colors);
        }

        if(map.containsKey("trail")) {
            builder.trail(Boolean.parseBoolean(map.get("trail").toString()));
        }

        builder.with(FireworkEffect.Type.valueOf(map.get("type").toString().toUpperCase()));

        return builder.build();
    }
}

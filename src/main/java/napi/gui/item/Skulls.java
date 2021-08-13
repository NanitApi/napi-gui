package napi.gui.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class Skulls {

    private Skulls(){ }

	public static void setTexture(SkullMeta meta, String url) {
        GameProfile profile = new GameProfile(generateUUID(url), null);

        if (profile.getProperties() == null)
            throw new IllegalStateException("Profile doesn't contain a property map");

        PropertyMap propertyMap = profile.getProperties();
        byte[] encodedData = Base64Coder.encodeString(String.format("{textures:{SKIN:{url:\"%s\"}}}", url)).getBytes();

        propertyMap.put("textures", new Property("textures", new String(encodedData)));

        Field profileField;

        try{
            Method method = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            method.setAccessible(true);
            method.invoke(method, profile);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex){
            try {
                profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException | IllegalAccessException ex2){
                ex2.printStackTrace();
            }
        }
    }

    private static UUID generateUUID(String url){
        return UUID.nameUUIDFromBytes(("AbstractMenusHead:" + url).getBytes(StandardCharsets.UTF_8));
    }
}
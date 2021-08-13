package napi.gui.api;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util to easy color codes replacing
 */
public final class Colors {

    private static final Pattern PATTERN = Pattern.compile("<#([A-Fa-f0-9]){6}>");
    private static boolean supportHex;

    static{
        try{
            Class.forName("net.md_5.bungee.api.ChatColor")
                    .getDeclaredMethod("of", String.class);
            supportHex = true;
        } catch (Throwable t){
            supportHex = false;
        }
    }

    /**
     * Replace all color codes
     * @param line Required single string
     * @return String with replaced colors
     */
    public static String of(String line) {
        if (supportHex) {
            Matcher matcher = PATTERN.matcher(line);

            while (matcher.find()) {
                net.md_5.bungee.api.ChatColor hexColor = net.md_5.bungee.api.ChatColor.of(matcher.group()
                        .substring(1, matcher.group().length() - 1));
                String before = line.substring(0, matcher.start());
                String after = line.substring(matcher.end());
                line = before + hexColor + after;
                matcher = PATTERN.matcher(line);
            }
        }

        return ChatColor.translateAlternateColorCodes('&', line);
    }

    /**
     * Replace all color codes in list of strings
     * @param list Required strings list
     * @return Strings list with replaced colors
     */
    public static List<String> ofList(List<String> list){
        for(int i = 0; i < list.size(); i++){
            list.set(i, of(list.get(i)));
        }
        return list;
    }

    /**
     * Replace all color codes in array of strings
     * @param array Required strings array
     * @return Strings array with replaced colors
     */
    public static String[] ofArr(String[] array){
        for (int i = 0; i < array.length; i++){
            array[i] = of(array[i]);
        }
        return array;
    }
}

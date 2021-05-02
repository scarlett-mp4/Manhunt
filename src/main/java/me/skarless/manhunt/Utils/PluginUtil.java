package me.skarless.manhunt.Utils;

import me.skarless.manhunt.Manhunt;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class PluginUtil {

    public static String getString(String path) {
        return parseColors(Manhunt.getInstance().languageConfig.getConfig().getString(path));
    }

    public static List<String> getStringList(String path) {
        List<String> newList = new ArrayList<>();
        for (String s : Manhunt.getInstance().languageConfig.getConfig().getStringList(path)) {
            newList.add(parseColors(s));
        }
        return newList;
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static List<Item> getNearbyItems(Location location, int radius) {
        List<Item> itemList = new ArrayList<>();
        for (Item item : location.getWorld().getEntitiesByClass(Item.class)) {
            if (location.distanceSquared(item.getLocation()) <= radius) {
                itemList.add(item);
            }
        }
        return itemList;
    }

    public static String parseColors(String message) {
        final Pattern hexPattern = Pattern.compile("#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }
}

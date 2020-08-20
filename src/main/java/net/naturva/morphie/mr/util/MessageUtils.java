package net.naturva.morphie.mr.util;

import org.bukkit.ChatColor;

public class MessageUtils {

    public static String addColor(String message) {
        if (message == null) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}

package net.naturva.morphie.mr.util;

import net.naturva.morphie.mr.MorphRedeem;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.UUID;

public class Utils {
    private MorphRedeem plugin;

    public Utils(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public boolean getFileExists(UUID uuid) {
        File playerFile = new File(this.plugin.getDataFolder() + File.separator + "PlayerData", uuid + ".yml");
        if (!playerFile.exists()) {
            return false;
        }
        return true;
    }

    public boolean checkIfUUID(String s) {
        try {
            UUID uuid = UUID.fromString(s);
            return true;
        }
        catch(IllegalArgumentException e) {
            return false;
        }

    }
}

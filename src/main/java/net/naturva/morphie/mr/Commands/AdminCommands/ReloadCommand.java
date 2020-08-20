package net.naturva.morphie.mr.Commands.AdminCommands;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.MorphRedeem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ReloadCommand {
    private MorphRedeem plugin;

    public ReloadCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public void reloadCommand(Player player, String[] args) {
        if (player.hasPermission("morphredeem.admin") || player.hasPermission("morphredeem.reload")) {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MorphRedeem");
            if (this.plugin != null) {
                this.plugin.reloadConfig();
                this.plugin.getServer().getPluginManager().disablePlugin(plugin);
                this.plugin.getServer().getPluginManager().enablePlugin(plugin);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("ReloadMessage")));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

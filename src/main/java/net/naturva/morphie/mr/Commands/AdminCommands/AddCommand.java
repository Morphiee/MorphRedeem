package net.naturva.morphie.mr.Commands.AdminCommands;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.Utils;
import net.naturva.morphie.mr.util.dataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AddCommand {
    private MorphRedeem plugin;

    public AddCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }
    
    public void addCredits(Player player, String[] args) {
        if (player.hasPermission("morphredeem.admin") || player.hasPermission("morphredeem.addcredits")) {
            if (args.length != 3) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Add")));
            }
            try {
                Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Add")));
            }
            int amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Add")));
            }
            Player target = null;
            OfflinePlayer offTarget = null;
            UUID targetUUID = null;
            if (Bukkit.getPlayer(args[1]) != null) {
                target = Bukkit.getPlayer(args[1]);
                targetUUID = target.getUniqueId();
            } else if (new Utils(plugin).checkIfUUID(args[1])) {
                targetUUID = UUID.fromString(args[1]);
                target = Bukkit.getPlayer(targetUUID);
                if (new Utils(plugin).getFileExists(targetUUID)) {
                    new dataManager(plugin).updateData(targetUUID, +amount, "Credits", "add");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddSuccessMessage")));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddMessage").replace("%CREDITS%", "" + amount)));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            } else if (!new Utils(plugin).checkIfUUID(args[1]) && Bukkit.getPlayer(args[1]) == null) {
                offTarget = Bukkit.getServer().getOfflinePlayer(args[1]);
                targetUUID = offTarget.getUniqueId();
                if (new Utils(plugin).getFileExists(targetUUID)) {
                    new dataManager(plugin).updateData(targetUUID, +amount, "Credits", "add");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddSuccessMessage")));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            }
            new dataManager(plugin).updateData(targetUUID, +amount, "Credits", "add");
            if (player == target) {
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddMessage").replace("%CREDITS%", "" + amount)));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddSuccessMessage")));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddMessage").replace("%CREDITS%", "" + amount)));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

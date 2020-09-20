package net.naturva.morphie.mr.Commands.AdminCommands;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.Utils;
import net.naturva.morphie.mr.util.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AddCommand {
    private MorphRedeem plugin;

    public AddCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }
    
    public void addCredits(CommandSender sender, String[] args) {
        if (sender.hasPermission("morphredeem.admin") || sender.hasPermission("morphredeem.addcredits")) {
            if (args.length != 3) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Add")));
            }
            try {
                Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Add")));
            }
            int amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Add")));
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
                    new DataManager(plugin).updateData(targetUUID, +amount, "Credits", "add");
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddSuccessMessage")));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddMessage").replace("%CREDITS%", "" + amount)));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            } else if (!new Utils(plugin).checkIfUUID(args[1]) && Bukkit.getPlayer(args[1]) == null) {
                offTarget = Bukkit.getServer().getOfflinePlayer(args[1]);
                targetUUID = offTarget.getUniqueId();
                if (new Utils(plugin).getFileExists(targetUUID)) {
                    new DataManager(plugin).updateData(targetUUID, +amount, "Credits", "add");
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddSuccessMessage")));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            }
            new DataManager(plugin).updateData(targetUUID, +amount, "Credits", "add");
            if (sender == target) {
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddMessage").replace("%CREDITS%", "" + amount)));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddSuccessMessage")));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditAddMessage").replace("%CREDITS%", "" + amount)));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

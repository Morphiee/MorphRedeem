package net.naturva.morphie.mr.Commands.AdminCommands;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.Utils;
import net.naturva.morphie.mr.util.dataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.rmi.CORBA.Util;
import java.util.UUID;

public class SetCommand {
    private MorphRedeem plugin;

    public SetCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public void setCredits(Player player, String[] args) {
        if (player.hasPermission("morphredeem.setcredits")) {
            if (args.length != 3) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Set")));
            }
            try {
                Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Set")));
            }
            int amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Set")));
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
                    new dataManager(plugin).updateData(targetUUID, amount, "Credits", "set");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSetSuccessMessage")));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            } else if (!new Utils(plugin).checkIfUUID(args[1]) && Bukkit.getPlayer(args[1]) == null) {
                offTarget = (OfflinePlayer)Bukkit.getServer().getOfflinePlayer(args[1]);
                targetUUID = offTarget.getUniqueId();
                if (new Utils(plugin).getFileExists(targetUUID)) {
                    new dataManager(plugin).updateData(targetUUID, amount, "Credits", "set");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSetSuccessMessage")));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            }
            new dataManager(plugin).updateData(targetUUID, amount, "Credits", "set");
            if (player == target) {
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSetMessage").replace("%CREDITS%", "" + amount)));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSetSuccessMessage")));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSetMessage").replace("%CREDITS%", "" + amount)));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

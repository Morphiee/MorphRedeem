package net.naturva.morphie.mr.Commands.AdminCommands;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.Utils;
import net.naturva.morphie.mr.util.dataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ResetCommand {
    private MorphRedeem plugin;

    public ResetCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public void resetCredits(Player player, String[] args) {
        if (player.hasPermission("morphredeem.resetcredits")) {
            if (args.length != 2) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Reset")));
            }
            Player target = null;
            OfflinePlayer offTarget = null;
            UUID targetUUID = null;
            if (Bukkit.getPlayer(args[1]) != null) {
                target = Bukkit.getPlayer(args[1]);
                targetUUID = target.getUniqueId();
            } else if (new Utils(plugin).checkIfUUID(args[1]) == true) {
                targetUUID = UUID.fromString(args[1]);
                target = Bukkit.getPlayer(targetUUID);
                if (new Utils(plugin).getFileExists(targetUUID)) {
                    new dataManager(plugin).updateData(targetUUID, 0, "Credits", "set");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditResetSuccessMessage")));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            } else if (new Utils(plugin).checkIfUUID(args[1]) == false && Bukkit.getPlayer(args[1]) == null) {
                offTarget = (OfflinePlayer)Bukkit.getServer().getOfflinePlayer(args[1]);
                targetUUID = offTarget.getUniqueId();
                if (new Utils(plugin).getFileExists(targetUUID)) {
                    new dataManager(plugin).updateData(targetUUID, 0, "Credits", "set");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditResetSuccessMessage")));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            }
            new dataManager(plugin).updateData(targetUUID, 0, "Credits", "set");
            if (player == target) {
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditResetMessage")));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditResetSuccessMessage")));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditResetMessage")));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

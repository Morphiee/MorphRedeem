package net.naturva.morphie.mr.Commands.PlayerCommands;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.Utils;
import net.naturva.morphie.mr.util.dataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SendCommand {
    private MorphRedeem plugin;

    public SendCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public void sendCredits(Player player, String[] args) {
        if (player.hasPermission("morphredeem.sendcredits")) {
            if (args.length != 3) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Send")));
            }
            try {
                Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Send")));
            }
            int amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Send")));
            }
            int senderCreds = Integer.parseInt(new dataManager(plugin).getData(player.getUniqueId(), "Credits"));
            if (amount > senderCreds) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidCredits")));
            }
            Player target = null;
            OfflinePlayer offTarget = null;
            UUID targetUUID = null;
            if (Bukkit.getPlayer(args[1]) != null) {
                target = Bukkit.getPlayer(args[1]);
                targetUUID = target.getUniqueId();
            } else if (Bukkit.getPlayer(args[1]) == null) {
                offTarget = (OfflinePlayer)Bukkit.getServer().getOfflinePlayer(args[1]);
                targetUUID = offTarget.getUniqueId();
                if (new Utils(plugin).getFileExists(targetUUID)) {
                    new dataManager(plugin).updateData(targetUUID, +amount, "Credits", "add");
                    new dataManager(plugin).updateData(player.getUniqueId(), -amount, "Credits", "remove");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSendSuccessMessage").replace("%TARGET%", offTarget.getName()).replace("%CREDITS%", "" + amount)));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            }
            new dataManager(plugin).updateData(targetUUID, +amount, "Credits", "add");
            new dataManager(plugin).updateData(player.getUniqueId(), -amount, "Credits", "remove");
            if (player == target) {
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSendMessage").replace("%SENDER%", player.getName()).replace("%CREDITS%", "" + amount)));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSendSuccessMessage").replace("%TARGET%", target.getName()).replace("%CREDITS%", "" + amount)));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSendMessage").replace("%SENDER%", player.getName()).replace("%CREDITS%", "" + amount)));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

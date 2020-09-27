package net.naturva.morphie.mr.Commands.PlayerCommands;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.MessageUtils;
import net.naturva.morphie.mr.util.Utils;
import net.naturva.morphie.mr.util.DataManager;
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
            if (args.length == 3) {
                boolean intCheck = true;
                try {
                    Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    intCheck = false;
                }
                if (intCheck = true) {
                    int amount = Integer.parseInt(args[2]);
                    int senderCreds = Integer.parseInt(new DataManager(plugin).getData(player.getUniqueId(), "Credits"));
                    if (amount > 0) {
                        if (amount <= senderCreds) {
                            Player target = null;
                            OfflinePlayer offTarget = null;
                            UUID targetUUID = null;
                            if (Bukkit.getPlayer(args[1]) != null) {
                                target = Bukkit.getPlayer(args[1]);
                                targetUUID = target.getUniqueId();
                                new DataManager(plugin).updateData(targetUUID, +amount, "Credits", "add");
                                new DataManager(plugin).updateData(player.getUniqueId(), -amount, "Credits", "remove");
                                if (player == target) {
                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSendMessage").replace("%SENDER%", player.getName()).replace("%CREDITS%", "" + amount)));
                                } else {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSendSuccessMessage").replace("%TARGET%", target.getName()).replace("%CREDITS%", "" + amount)));
                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSendMessage").replace("%SENDER%", player.getName()).replace("%CREDITS%", "" + amount)));
                                }
                            } else if (Bukkit.getPlayer(args[1]) == null) {
                                offTarget = (OfflinePlayer) Bukkit.getServer().getOfflinePlayer(args[1]);
                                targetUUID = offTarget.getUniqueId();
                                if (new Utils(plugin).getFileExists(targetUUID)) {
                                    new DataManager(plugin).updateData(targetUUID, +amount, "Credits", "add");
                                    new DataManager(plugin).updateData(player.getUniqueId(), -amount, "Credits", "remove");
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSendSuccessMessage").replace("%TARGET%", offTarget.getName()).replace("%CREDITS%", "" + amount)));
                                } else {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                                }
                            }
                        } else {
                            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidCredits")));
                        }
                    } else {
                        player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Send")));
                    }
                } else {
                    player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Send")));
                }
            } else {
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Send")));
            }
        }else {
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

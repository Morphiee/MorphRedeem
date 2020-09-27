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

public class SetCommand {
    private MorphRedeem plugin;

    public SetCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public void setCredits(CommandSender sender, String[] args) {
        if (sender.hasPermission("morphredeem.setcredits")) {
            boolean intCheck = true;
            if (args.length == 3) {
                try {
                    Integer.parseInt(args[2]);
                }
                catch (NumberFormatException e) {
                    intCheck = false;
                }
                if (intCheck) {
                    int amount = Integer.parseInt(args[2]);
                    if (amount > 0) {
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
                                new DataManager(plugin).updateData(targetUUID, amount, "Credits", "set");
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSetSuccessMessage")));
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                            }
                        } else if (!new Utils(plugin).checkIfUUID(args[1]) && Bukkit.getPlayer(args[1]) == null) {
                            offTarget = (OfflinePlayer)Bukkit.getServer().getOfflinePlayer(args[1]);
                            targetUUID = offTarget.getUniqueId();
                            if (new Utils(plugin).getFileExists(targetUUID)) {
                                new DataManager(plugin).updateData(targetUUID, amount, "Credits", "set");
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSetSuccessMessage")));
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                            }
                        }
                        new DataManager(plugin).updateData(targetUUID, amount, "Credits", "set");
                        if (sender == target) {
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSetMessage").replace("%CREDITS%", "" + amount)));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSetSuccessMessage")));
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditSetMessage").replace("%CREDITS%", "" + amount)));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Set")));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Set")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Set")));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

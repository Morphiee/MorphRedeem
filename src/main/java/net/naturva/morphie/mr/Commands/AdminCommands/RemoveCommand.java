package net.naturva.morphie.mr.Commands.AdminCommands;

import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.MessageUtils;
import net.naturva.morphie.mr.util.Utils;
import net.naturva.morphie.mr.util.dataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RemoveCommand {
    private MorphRedeem plugin;

    public RemoveCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public void removeCredits(Player player, String[] args) {
        if (player.hasPermission("morphredeem.admin") || player.hasPermission("morphredeem.removecredits")) {
            if (args.length != 3) {
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Remove")));
            }
            int amount = Integer.parseInt(args[2]);
            try {
                Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e) {
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Remove")));
            }
            if (amount <= 0) {
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Remove")));
            }
            Player target = null;
            OfflinePlayer offTarget = null;
            UUID targetUUID = null;
            int credits = 0;
            if (Bukkit.getPlayer(args[1]) != null) {
                target = Bukkit.getPlayer(args[1]);
                targetUUID = target.getUniqueId();
                credits = Integer.parseInt(new dataManager(plugin).getData(targetUUID, "Credits"));
                if (amount > credits) {
                    player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Remove")));
                }
            } else if (new Utils(plugin).checkIfUUID(args[1])) {
                targetUUID = UUID.fromString(args[1]);
                target = Bukkit.getPlayer(targetUUID);
                credits = Integer.parseInt(new dataManager(plugin).getData(targetUUID, "Credits"));
                if (amount > credits) {
                    player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Remove")));
                }
                if (new Utils(plugin).getFileExists(targetUUID)) {
                    new dataManager(plugin).updateData(targetUUID, -amount, "Credits", "remove");
                    player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditRemoveSuccessMessage")));
                } else {
                    player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            } else if (new Utils(plugin).checkIfUUID(args[1]) == false && Bukkit.getPlayer(args[1]) == null) {
                offTarget = (OfflinePlayer)Bukkit.getServer().getOfflinePlayer(args[1]);
                targetUUID = offTarget.getUniqueId();
                credits = Integer.parseInt(new dataManager(plugin).getData(targetUUID, "Credits"));
                if (amount > credits) {
                    player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Remove")));
                }
                if (new Utils(plugin).getFileExists(targetUUID)) {
                    new dataManager(plugin).updateData(targetUUID, -amount, "Credits", "remove");
                    player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditRemoveSuccessMessage")));
                } else {
                    player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                }
            }
            if (amount > credits) {
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("CorrectUsage.Remove")));
            }
            new dataManager(plugin).updateData(targetUUID, -amount, "Credits", "remove");
            if (player == target) {
                target.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditRemoveMessage").replace("%CREDITS%", "" + amount)));
            } else {
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditRemoveSuccessMessage")));
                target.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Prefix") + this.plugin.getMessage("CreditRemoveMessage").replace("%CREDITS%", "" + amount)));
            }
        } else {
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

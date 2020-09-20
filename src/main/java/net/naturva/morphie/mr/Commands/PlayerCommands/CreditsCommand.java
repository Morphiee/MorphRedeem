package net.naturva.morphie.mr.Commands.PlayerCommands;

import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.MessageUtils;
import net.naturva.morphie.mr.util.Utils;
import net.naturva.morphie.mr.util.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CreditsCommand {
    private MorphRedeem plugin;

    public CreditsCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public void runCredits(Player player, String[] args) {
        UUID uuid = player.getUniqueId();
        if (args.length == 1) {
            if (player.hasPermission("morphredeem.credits")) {
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Prefix") + this.plugin.getMessage("PlayerCreditsMessage").replace("%CREDITS%", new DataManager(plugin).getData(uuid, "Credits"))));
            } else {
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
            }
        } else if (args.length == 2) {
            if (player.hasPermission("morphredeem.credits.others")) {
                Player target = null;
                OfflinePlayer offTarget = null;
                if (Bukkit.getPlayer(args[1]) != null) {
                    target = Bukkit.getPlayer(args[1]);
                    String targetCredits = new DataManager(plugin).getData(target.getUniqueId(), "Credits");
                    player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Prefix") + this.plugin.getMessage("OtherPlayerCreditMessage").replace("%PLAYER%", target.getName()).replace("%CREDITS%", targetCredits)));
                } else {
                    offTarget = (OfflinePlayer)Bukkit.getServer().getOfflinePlayer(args[1]);
                    if (new Utils(plugin).getFileExists(offTarget.getUniqueId())) {
                        String targetCredits = new DataManager(plugin).getData(offTarget.getUniqueId(), "Credits");
                        player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Prefix") + this.plugin.getMessage("OtherPlayerCreditMessage").replace("%PLAYER%", offTarget.getName()).replace("%CREDITS%", targetCredits)));
                    } else {
                        player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidPlayer")));
                    }
                }
            } else {
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
            }
        }
    }
}

package net.naturva.morphie.mr.Commands.PlayerCommands;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.MessageUtils;
import org.bukkit.entity.Player;

public class HelpCommand {
    private MorphRedeem plugin;

    public HelpCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public void runHelp(Player player) {
        if (player.hasPermission("morphredeem.help")) {
            player.sendMessage("");
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.Header")));
            player.sendMessage("");
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.Help")));
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.MR")));
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.MRSkill")));
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.Credits")));
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.CreditsOthers")));
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.Send")));
            player.sendMessage("");
            if (player.hasPermission("morphredeem.admin") || player.hasPermission("morphredeem.reload")) {
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.Add")));
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.Remove")));
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.Set")));
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.Reset")));
                player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.Reload")));
            }
            player.sendMessage("");
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("Commands.Footer")));
            player.sendMessage("");
        } else {
            player.sendMessage(MessageUtils.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

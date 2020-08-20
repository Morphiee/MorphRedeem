package net.naturva.morphie.mr.Commands.PlayerCommands;

import com.gmail.nossr50.api.exceptions.McMMOPlayerNotFoundException;
import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.menus.RedeemMenu;
import org.bukkit.entity.Player;

public class MenuCommand {

    private MorphRedeem plugin;

    public MenuCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public void openMenu(Player player) {
        if (!player.isSleeping()) {
            if (player.hasPermission("morphredeem.redeem")) {
                try {
                    new RedeemMenu(plugin).openGUIRedeem(player);
                } catch (McMMOPlayerNotFoundException e1) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("McMMOPlayerNotLoadedMessage")));
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
            }
        }
    }
}

package net.naturva.morphie.mr.Commands;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.Commands.AdminCommands.*;
import net.naturva.morphie.mr.Commands.PlayerCommands.*;
import net.naturva.morphie.mr.MorphRedeem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    private MorphRedeem plugin;

    public CommandHandler(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("morphredeem") || cmd.getName().equalsIgnoreCase("redeem") || cmd.getName().equalsIgnoreCase("mr")) {
                if (args.length == 0) {
                    new MenuCommand(plugin).openMenu(player);
                    return true;
                } else if (args[0].equalsIgnoreCase("help")) {
                    new HelpCommand(plugin).runHelp(player);
                    return true;
                } else if (args[0].equalsIgnoreCase("credits")) {
                    new CreditsCommand(plugin).runCredits(player, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("add")) {
                    new AddCommand(plugin).addCredits(player, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("remove")) {
                    new RemoveCommand(plugin).removeCredits(player, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("set")) {
                    new SetCommand(plugin).setCredits(player, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("send")) {
                    new SendCommand(plugin).sendCredits(player, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("reset")) {
                    new ResetCommand(plugin).resetCredits(player, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    new ReloadCommand(plugin).reloadCommand(player, args);
                    return true;
                } else if (args.length == 2) {
                    new SkillNameCommand(plugin).redeemCredits(player, args);
                    return true;
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidArgsMessage")));
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}

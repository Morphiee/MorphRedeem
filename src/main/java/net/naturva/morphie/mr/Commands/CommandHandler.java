package net.naturva.morphie.mr.Commands;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.Commands.AdminCommands.*;
import net.naturva.morphie.mr.Commands.PlayerCommands.*;
import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.MessageUtils;
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
        if (cmd.getName().equalsIgnoreCase("morphredeem") || cmd.getName().equalsIgnoreCase("redeem") || cmd.getName().equalsIgnoreCase("mr")) {
            if (args.length == 0) {
                // Player Only
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    new MenuCommand(plugin).openMenu(player);
                } else {
                    sender.sendMessage(MessageUtils.addColor(plugin.getMessage("ErrorPrefix") + " &cThis command can only be run by a player!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {
                // Player Only
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    new HelpCommand(plugin).runHelp(player);
                } else {
                    sender.sendMessage(MessageUtils.addColor(plugin.getMessage("ErrorPrefix") + " &cThis command can only be run by a player!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("credits")) {
                // Player Only
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    new CreditsCommand(plugin).runCredits(player, args);
                } else {
                    sender.sendMessage(MessageUtils.addColor(plugin.getMessage("ErrorPrefix") + " &cThis command can only be run by a player!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("add")) {
                // Sender (Including Console)
                new AddCommand(plugin).addCredits(sender, args);
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                // Sender (Including Console)
                new RemoveCommand(plugin).removeCredits(sender, args);
                return true;
            } else if (args[0].equalsIgnoreCase("set")) {
                // Sender (Including Console)
                new SetCommand(plugin).setCredits(sender, args);
                return true;
            } else if (args[0].equalsIgnoreCase("send")) {
                // Player Only
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    new SendCommand(plugin).sendCredits(player, args);
                } else {
                    sender.sendMessage(MessageUtils.addColor(plugin.getMessage("ErrorPrefix") + " &cThis command can only be run by a player!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("reset")) {
                // Sender (Including Console)
                new ResetCommand(plugin).resetCredits(sender, args);
                return true;
            } else if (args[0].equalsIgnoreCase("reload")) {
                // Sender (Including Console)
                new ReloadCommand(plugin).reloadCommand(sender, args);
                return true;
            } else if (args.length == 2) {
                // Player Only
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    new SkillNameCommand(plugin).redeemCredits(player, args);
                } else {
                    sender.sendMessage(MessageUtils.addColor(plugin.getMessage("ErrorPrefix") + " &cThis command can only be run by a player!"));
                }
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidArgsMessage")));
                return true;
            }
        }
        return false;
    }
}

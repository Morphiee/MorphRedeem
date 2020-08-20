package net.naturva.morphie.mr.Commands.PlayerCommands;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.exceptions.InvalidSkillException;
import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.util.McMMOMethods;
import net.naturva.morphie.mr.util.dataManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SkillNameCommand {
    private MorphRedeem plugin;

    public SkillNameCommand(MorphRedeem plugin) {
        this.plugin = plugin;
    }

    public void redeemCredits(Player player, String[] args) {
        if (player.hasPermission("morphredeem.skill")) {
            UUID uuid = player.getUniqueId();
            String skill = args[0];
            boolean intCheck = true;
            try {
                Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e1) {
                intCheck = false;
            }
            if (intCheck) {
                int amount2 = Integer.parseInt(args[1]);
                int credits = Integer.parseInt(new dataManager(plugin).getData(uuid, "Credits"));
                try {
                    if (new McMMOMethods().doesSkillExist(player, skill)) {
                        if (amount2 > 0 && amount2 <= credits) {
                            int cap = ExperienceAPI.getLevelCap(skill);
                            if (this.plugin.skillscfg.getSkillLevelTillUse("Skills." + skill + ".LevelTillUse") <= ExperienceAPI.getLevel(player, skill)) {
                                if (ExperienceAPI.getLevel(player, skill) + amount2 > cap) {
                                    String message = this.plugin.getMessage("SkillCapReached");
                                    if (message.contains("%SKILL%")) {
                                        message = message.replaceAll("%SKILL%", skill);
                                    }
                                    if (message.contains("%CAP%")) {
                                        message = message.replaceAll("%CAP%", "" + cap);
                                    }
                                    if (message.contains("%LEVEL%")) {
                                        message = message.replaceAll("%LEVEL%", "" + (ExperienceAPI.getLevel(player, skill) + amount2));
                                    }
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + message));
                                } else {
                                    new dataManager(plugin).updateData(uuid, +amount2, "Credits_Spent", "add");
                                    new dataManager(plugin).updateData(uuid, -amount2, "Credits", "remove");

                                    ExperienceAPI.addLevel(player, skill, amount2);
                                    String message = this.plugin.getMessage("CreditAssignmentSuccess");
                                    if (message.contains("%SKILL%")) {
                                        message = message.replaceAll("%SKILL%", skill);
                                    }
                                    if (message.contains("%CREDITS%")) {
                                        message = message.replaceAll("%CREDITS%", "" + amount2);
                                    }
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("Prefix") + message));
                                }
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("LevelTillUse").replace("%LEVEL%", "" + this.plugin.skillscfg.getSkillLevelTillUse("Skills." + skill + ".LevelTillUse"))));
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidCredits")));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidSkill")));
                    }
                } catch (InvalidSkillException e) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidSkill")));
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("InvalidNumber")));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
        }
    }
}

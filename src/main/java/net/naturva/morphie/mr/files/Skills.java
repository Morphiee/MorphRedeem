package net.naturva.morphie.mr.files;

import net.naturva.morphie.mr.MorphRedeem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Skills implements Listener {
    private MorphRedeem plugin = (MorphRedeem)MorphRedeem.getPlugin(MorphRedeem.class);
    private FileConfiguration skillsCFG;
    private File skillsFile;

    public void setup() {
        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }
        this.skillsFile = new File(this.plugin.getDataFolder(), "skills.yml");
        if (!this.skillsFile.exists()) {
            try {
                this.skillsFile.createNewFile();

                this.skillsCFG = YamlConfiguration.loadConfiguration(this.skillsFile);
                List<String> list = new ArrayList<>();
                list.add("Acrobatics");

                this.skillsCFG.addDefault("SkillsToDisable.List", list);
                this.skillsCFG.addDefault("SkillsToDisable.Enabled", false);
                this.skillsCFG.addDefault("Skills.Acrobatics.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Acrobatics.GUI.ItemName", "LEATHER_BOOTS");
                this.skillsCFG.addDefault("Skills.Alchemy.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Alchemy.GUI.ItemName", "POTION");
                this.skillsCFG.addDefault("Skills.Archery.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Archery.GUI.ItemName", "BOW");
                this.skillsCFG.addDefault("Skills.Axes.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Axes.GUI.ItemName", "DIAMOND_AXE");
                this.skillsCFG.addDefault("Skills.Excavation.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Excavation.GUI.ItemName", "DIAMOND_SHOVEL");
                this.skillsCFG.addDefault("Skills.Fishing.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Fishing.GUI.ItemName", "FISHING_ROD");
                this.skillsCFG.addDefault("Skills.Herbalism.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Herbalism.GUI.ItemName", "FARMLAND");
                this.skillsCFG.addDefault("Skills.Mining.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Mining.GUI.ItemName", "DIAMOND_PICKAXE");
                this.skillsCFG.addDefault("Skills.Repair.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Repair.GUI.ItemName", "ANVIL");
                this.skillsCFG.addDefault("Skills.Swords.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Swords.GUI.ItemName", "DIAMOND_SWORD");
                this.skillsCFG.addDefault("Skills.Taming.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Taming.GUI.ItemName", "BONE");
                this.skillsCFG.addDefault("Skills.Unarmed.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Unarmed.GUI.ItemName", "STICK");
                this.skillsCFG.addDefault("Skills.Woodcutting.LevelTillUse", 0);
                this.skillsCFG.addDefault("Skills.Woodcutting.GUI.ItemName", "OAK_WOOD");

                this.skillsCFG.options().header(" Welcome to the Skills.yml file! I decided since I will be having multiple options for each\n skill that it would make more sense to have a base file for all options under each of the skills.\n \n SkillsToDisable: - List of skills that wont be used in the plugin. \n LevelTillUse: - Level that the user has to be in the specific skill before they can apply credits to it.\n ItemName: - Name of the item that the skill will use in the redeem GUI.");
                this.skillsCFG.options().copyDefaults(true);
                saveskills();
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create the skills.yml file");
            }
        }
        this.skillsCFG = YamlConfiguration.loadConfiguration(this.skillsFile);
    }

    public void saveskills() {
        try {
            this.skillsCFG.save(this.skillsFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the skills.yml file");
        }
    }

    public String getSkillItemName(String string) {
        return skillsCFG.getString(string);
    }

    public Integer getSkillLevelTillUse(String string) {
        return skillsCFG.getInt(string);
    }

    public List<String> getSkillDisableList(String string) {
        return skillsCFG.getStringList(string);
    }

    public boolean getSkillDisableBoolean(String string) {
        return skillsCFG.getBoolean(string);
    }
}

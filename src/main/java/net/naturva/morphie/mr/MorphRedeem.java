package net.naturva.morphie.mr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import jdk.nashorn.internal.objects.annotations.Getter;
import net.naturva.morphie.mr.Commands.CommandHandler;
import net.naturva.morphie.mr.files.Skills;
import net.naturva.morphie.mr.util.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.naturva.morphie.mr.events.PlayerFileEvent;
import net.naturva.morphie.mr.events.chat.RedeemChatEvent;
import net.naturva.morphie.mr.events.menus.RedeemMenuEvent;
import net.naturva.morphie.mr.files.Messages;
import net.naturva.morphie.mr.util.MetricsLite;
import net.naturva.morphie.mr.util.MorphRedeemExpansion;
import net.naturva.morphie.mr.util.Database.MySQLConnection;

public class MorphRedeem extends JavaPlugin implements Listener {
	
	public static Logger log = Logger.getLogger("Minecraft");
	public Messages messagescfg;
	public Skills skillscfg;
	public HashMap<Player, String> addCredits = new HashMap<Player, String>();
	private String version = "1.3.0";
	
	private PlayerFileEvent pe;
	private RedeemMenuEvent me;
	private RedeemChatEvent ce;
	
	public void onEnable() {
		Objects.requireNonNull(getCommand("mr")).setExecutor(new CommandHandler(this));
		this.pe = new PlayerFileEvent(this);
		this.me = new RedeemMenuEvent(this);
		this.ce = new RedeemChatEvent(this);
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(this.pe, this);
		getServer().getPluginManager().registerEvents(this.me, this);
		getServer().getPluginManager().registerEvents(this.ce, this);
		
        new MetricsLite(this);

		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&8_______________________________________________________"));
        getServer().getConsoleSender().sendMessage(MessageUtils.addColor(" &9__  __ &b___ "));
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9|  \\/  &b| _ \\     &9Version&8: &b" + this.version));
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9| |\\/| &b|   /      &9Status&8: &aEnabled"));
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9|_|  |_&b|_|_\\"));
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&8_______________________________________________________"));
		getServer().getConsoleSender().sendMessage(" ");

		createConfig();
		loadConfigManager();

		getServer().getConsoleSender().sendMessage(" ");
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9Command Handler&8: &aEnabled"));

		if (this.getConfig().getString("StorageMethod").equals("MySQL")) {
			new MySQLConnection(this).mysqlSetup();
			new MySQLConnection(this).checkStructure();
			getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9Storage Type&8: &bMySQL"));
		} else {
			getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9Storage Type&8: &bYML"));
		}
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
        	new MorphRedeemExpansion(this).register();
        	getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9PlaceholderAPI&8: &bHooked"));
        } else {
        	getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9PlaceholderAPI&8: &cNot found"));
        }
		if(Bukkit.getPluginManager().getPlugin("mcMMO") != null){
			new MorphRedeemExpansion(this).register();
			getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9mcMMO&8: &bHooked"));
		} else {
			getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9mcMMO&8: &cNot found"));
		}
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&8_______________________________________________________"));
	}
	
	public void onDisable(){
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&8_______________________________________________________"));
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor(" &9__  __ &b___ "));
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9|  \\/  &b| _ \\     &9Version&8: &b" + this.version));
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9| |\\/| &b|   /      &9Status&8: &cDisabled"));
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9|_|  |_&b|_|_\\"));
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&8_______________________________________________________"));
	}
	
	private void createConfig() {
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			File file = new File(getDataFolder(), "config.yml");
			if (!file.exists()) {
				getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9Config&8: &aGenerating config."));
				getConfig().options().copyDefaults(true);
				saveDefaultConfig();
			} else {
				getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9Config&8: &bLoading config.yml"));
			}
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
    public void loadConfigManager() {
        this.messagescfg = new Messages();
        this.messagescfg.setup();
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9Config&8: &bLoading messages.yml"));
        this.skillscfg = new Skills();
        this.skillscfg.setup();
		getServer().getConsoleSender().sendMessage(MessageUtils.addColor("&9Config&8: &bLoading skills.yml"));
      }
  	
    public ItemStack createInventoryItem(String paramString1, int paramInt, String paramString2, ArrayList<String> paramArrayList, boolean paramBoolean) {
    	ItemStack localItemStack = new ItemStack(Material.matchMaterial(paramString1), paramInt);
    	ItemMeta localItemMeta = localItemStack.getItemMeta();
    	localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
    	localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
    	localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_DESTROYS });
    	if (paramBoolean) {
    		localItemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
    		localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
    	}
    	localItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', paramString2));
    	localItemMeta.setLore(paramArrayList);
    	localItemStack.setItemMeta(localItemMeta);
    	return localItemStack;
    }
    
    public ItemStack createInventoryGlassItem(String paramString1, int glassInt, int paramInt, String paramString2, ArrayList<String> paramArrayList, boolean paramBoolean) {
    	ItemStack localItemStack = new ItemStack(Material.matchMaterial(paramString1), paramInt, (short) glassInt);
    	ItemMeta localItemMeta = localItemStack.getItemMeta();
    	if (paramBoolean) {
    		localItemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
    		localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
    	}
    	localItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', paramString2));
    	localItemMeta.setLore(paramArrayList);
    	localItemStack.setItemMeta(localItemMeta);
    	return localItemStack;
    }

	public String getMessage(String string) {
		return this.messagescfg.getMessagesCgf().getString(string);
	}
	
	public List<String> getMessageList(String string) {
		return this.messagescfg.getMessagesCgf().getStringList(string);
	}

	@Getter
	public String getVersion() {
		return version;
	}
}

package net.naturva.morphie.mr.util;

import java.util.UUID;

import net.naturva.morphie.mr.MorphRedeem;
import net.naturva.morphie.mr.files.PlayerFileMethods;
import net.naturva.morphie.mr.util.Database.MySQLConnection;

public class DataManager {
	
	private MorphRedeem plugin;
	
	public DataManager(MorphRedeem plugin) {
		this.plugin = plugin;
	}

	public String getData(UUID uuid, String name) {
		if (this.plugin.getConfig().getString("StorageMethod").equals("MySQL")) {
			return new MySQLConnection(this.plugin).getData(uuid, name);
		} else {
			return new PlayerFileMethods(plugin).getStat(uuid, name);
		}
	}
	
	public void updateData(UUID uuid, int data, String name, String type) {
		if (this.plugin.getConfig().getString("StorageMethod").equals("MySQL")) {
			new MySQLConnection(this.plugin).updateData(uuid, data, name, type);
		} else if (type == "add" || type == "remove"){
			new PlayerFileMethods(plugin).updateCredits(uuid, name, data);
		} else if (type == "set") {
			new PlayerFileMethods(plugin).setData(uuid, name, data);
		}
	}
}

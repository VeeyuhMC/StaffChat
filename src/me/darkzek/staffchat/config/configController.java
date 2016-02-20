package me.darkzek.staffchat.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.darkzek.staffchat.Main;

public class configController {
	Main m;
	File data;
	static FileConfiguration dataConfig;
	File def;
	static FileConfiguration defConfig;
	public void onEnable() {
		m = Main.setInstance();
	}
	void getConfigs () {
		data = new File(m.getDataFolder(), "data.yml");
		if (!data.exists()) {
			createDataConfig();
		}
		def = new File(m.getDataFolder(), "config.yml");
		defConfig = m.getConfig();
		if (!def.exists()) {
			m.saveConfig();
		}
	}
	void createDataConfig () {
		dataConfig = YamlConfiguration.loadConfiguration(data);
		try {
			dataConfig.save(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

package to.epac.factorycraft.TerrainHousing;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import to.epac.factorycraft.TerrainHousing.Commands.Commands;
import to.epac.factorycraft.TerrainHousing.Events.BuildHandler;
import to.epac.factorycraft.TerrainHousing.Events.PlayerQuitHandler;
import to.epac.factorycraft.TerrainHousing.Events.SignClickHandler;

public class Main extends JavaPlugin {
	private static Plugin instance;
	
	public static File configFile;
	
	public void onEnable() {
		instance = this;
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new SignClickHandler(), this);
		pm.registerEvents(new BuildHandler(), this);
		pm.registerEvents(new PlayerQuitHandler(), this);
		
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Configuration not found. Generating the default one.");

            getConfig().options().copyDefaults(true);
            saveConfig();
        }
		
        getCommand("TerrainHousing").setExecutor(new Commands());
		
	}
	
	public void onDisable() {
		instance = null;
	}
	
	public static Plugin getInstance() {
		return instance;
	}
}

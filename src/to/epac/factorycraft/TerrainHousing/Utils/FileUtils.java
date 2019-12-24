package to.epac.factorycraft.TerrainHousing.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.TerrainHousing.Main;

public class FileUtils {
	private static Plugin plugin = Main.getInstance();
	
	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TerrainHousing.Prefix"));
	}
	
	/**
	 * Get TerrainHousing enabled/disabled
	 * @return
	 */
	public static boolean getEnabled() {
		return plugin.getConfig().getBoolean("TerrainHousing.Enabled");
	}
	public static void setEnabled(boolean enabled) {
		plugin.getConfig().set("TerrainHousing.Enabled", enabled);
		plugin.saveConfig();
	}
	
	
	/**
	 * Get height limit
	 * @return
	 */
	/*public static int getHeightLimit() {
		return plugin.getConfig().getInt("TerrainHousing.HeightLimit");
	}
	public static void setHeightLimit(int limit) {
		plugin.getConfig().set("TerrainHousing.HeightLimit", limit);
		plugin.saveConfig();
	}*/
	
	/**
	 * Get idle time before resets (in ticks)
	 * @return
	 */
	public static long getIdleTime() {
		return plugin.getConfig().getLong("TerrainHousing.IdleTime");
	}
	public static void setIdleTime(long tick) {
		plugin.getConfig().set("TerrainHousing.IdleTime", tick);
		plugin.saveConfig();
	}
	
	/**
	 * Get idle time before resets (in ticks)
	 * @return
	 */
	public static long getSignCd() {
		return plugin.getConfig().getLong("TerrainHousing.SignCd");
	}
	public static void setSignCd(long tick) {
		plugin.getConfig().set("TerrainHousing.SignCd", tick);
		plugin.saveConfig();
	}
	
	/**
	 * Get TerrainHousing override protections
	 * @return
	 */
	public static boolean getOverrideProtections() {
		return plugin.getConfig().getBoolean("TerrainHousing.OverrideProtections");
	}
	public static void setOverrideProtections(boolean override) {
		plugin.getConfig().set("TerrainHousing.OverrideProtections", override);
		plugin.saveConfig();
	}
	
	/**
	 * Get sign default text
	 * @return
	 */
	public static List<String> getSignAvailable() {
		return plugin.getConfig().getStringList("TerrainHousing.SignText.Available");
	}
	public static List<String> getSignOccupied() {
		return plugin.getConfig().getStringList("TerrainHousing.SignText.Occupied");
	}
	public static List<String> getSignResetting() {
		return plugin.getConfig().getStringList("TerrainHousing.SignText.Resetting");
	}
	
	/**
	 * Get all platform's name
	 * @return
	 */
	public static List<String> getTerrains() {
		List<String> locations = new ArrayList<>();
		
		ConfigurationSection locs = plugin.getConfig().getConfigurationSection("TerrainHousing.Location");
		locations.addAll(locs.getKeys(false));
		
		return locations;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Location getMinimum(String id) {
		return (Location) plugin.getConfig().get("TerrainHousing.Location." + id + ".Minimum");
	}
	public static void setMinimum(String id, Location loc) {
		plugin.getConfig().set("TerrainHousing.Location." + id + ".Minimum", loc);
		plugin.saveConfig();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Location getMaximum(String id) {
		return (Location) plugin.getConfig().get("TerrainHousing.Location." + id + ".Maximum");
	}
	public static void setMaximum(String id, Location loc) {
		plugin.getConfig().set("TerrainHousing.Location." + id + ".Maximum", loc);
		plugin.saveConfig();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Location getSign(String id) {
		return (Location) plugin.getConfig().get("TerrainHousing.Location." + id + ".Sign");
	}
	public static void setSign(String id, Location loc) {
		plugin.getConfig().set("TerrainHousing.Location." + id + ".Sign", loc);
		plugin.saveConfig();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Location getHead(String id) {
		return (Location) plugin.getConfig().get("TerrainHousing.Location." + id + ".Head");
	}
	public static void setHead(String id, Location loc) {
		plugin.getConfig().set("TerrainHousing.Location." + id + ".Head", loc);
		plugin.saveConfig();
	}
	
	
	/**
	 * Get player that occupied the platform
	 * @return UUID of occupied player, or null if not set
	 */
	public static String getOccupiedBy(String id) {
		return plugin.getConfig().getString("TerrainHousing.Location." + id + ".OccupiedBy", null);
	}
	public static void setOccupiedBy(String id, Player player) {
		plugin.getConfig().set("TerrainHousing.Location." + id + ".OccupiedBy", player.getUniqueId().toString());
		plugin.saveConfig();
	}
	public static void removeOccupied(String id) {
		plugin.getConfig().set("TerrainHousing.Location." + id + ".OccupiedBy", null);
		plugin.saveConfig();
	}
	
	
	
	
	
}

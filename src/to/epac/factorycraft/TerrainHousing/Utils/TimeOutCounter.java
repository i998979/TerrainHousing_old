package to.epac.factorycraft.TerrainHousing.Utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import to.epac.factorycraft.TerrainHousing.Main;

public class TimeOutCounter {

    private static Plugin plugin = Main.getInstance();
    
    public static HashMap<String, BukkitRunnable> runnables = new HashMap<>();

    public static void start(String id, Player player, long idle) {
        long expire = Utils.getTimeInt() + idle;

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                long now = Utils.getTimeInt();
                Sign sign = (Sign) FileUtils.getSign(id).getBlock().getState();
                Skull head = (Skull) FileUtils.getHead(id).getBlock().getState();
                
                long remain = expire - now;
                
                // TODO - Glitchy time display when <1min and 00:00
                String sec = String.format("%02d", remain % 60);
                String min = String.format("%02d", remain % 3600 / 60);
                String hr = String.format("%02d", remain % 86400 / 3600);
                
                for (int i = 0; i < 4; i++) {
                	String line = FileUtils.getSignOccupied().get(i)
                			.replaceAll("%player%", player.getName())
                			.replaceAll("%timer%", (hr.equals("0") ? hr + ":" : "") + min + ":" + sec);
                    sign.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
                    sign.update();
                }
                
                if (now > expire) {
                    // Save schematic
                    SchemUtils.save(player, id);
                    // Reset schematic
                    SchemUtils.reset(id);
                    // Update sign
                    for (int i = 0; i < 4; i++) {
                        sign.setLine(i, ChatColor.translateAlternateColorCodes('&', FileUtils.getSignResetting().get(i)));
                        sign.update();
                    }
                    stop(id);
                    
                    BukkitRunnable runnable0 = new BukkitRunnable() {
						@Override
						public void run() {
							// Remove occupation
                            FileUtils.removeOccupied(id);
                            // Update sign
                            for (int i = 0; i < 4; i++) {
                                sign.setLine(i, ChatColor.translateAlternateColorCodes('&', FileUtils.getSignAvailable().get(i)));
                                sign.update();
                            }
                            // Update skull
                            head.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(
                                        "8667ba71-b85a-4004-af54-457a9734eed7")));
                            head.update();
                            
                            sign.removeMetadata("TerrainHousing:OccupiedBy", plugin);
						}
                    };
                    runnable0.runTaskLater(plugin, 3 * 20);
                }
            }
        };
        runnables.put(id, runnable);
        runnable.runTaskTimer(plugin, 0, 20);
    }
    public static void stop(String id) {
    	BukkitRunnable runnable = runnables.get(id);
    	try {
    		runnable.cancel();
    		runnables.remove(id);
    	} catch (NullPointerException e) {
    		return;
    	}
    }
}
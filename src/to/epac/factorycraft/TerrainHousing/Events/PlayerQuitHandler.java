package to.epac.factorycraft.TerrainHousing.Events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import to.epac.factorycraft.TerrainHousing.Main;
import to.epac.factorycraft.TerrainHousing.Utils.FileUtils;
import to.epac.factorycraft.TerrainHousing.Utils.SchemUtils;
import to.epac.factorycraft.TerrainHousing.Utils.TimeOutCounter;

public class PlayerQuitHandler implements Listener {
	
	private static Plugin plugin = Main.getInstance();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        for (String id: FileUtils.getTerrains()) {

            Skull head = (Skull) FileUtils.getHead(id).getBlock().getState();
            Sign sign = (Sign) FileUtils.getSign(id).getBlock().getState();
            
            if (FileUtils.getOccupiedBy(id) != null) {
            	
                Player occupied = Bukkit.getPlayer(UUID.fromString(FileUtils.getOccupiedBy(id)));
                
                if (player.equals(occupied)) {
                    // Save schematic
                    SchemUtils.save(player, id);
                    // Reset schematic
                    SchemUtils.reset(id);
                    // Update sign
                    for (int i = 0; i < 4; i++) {
                        sign.setLine(i, ChatColor.translateAlternateColorCodes('&', FileUtils.getSignResetting().get(i)));
                        sign.update();
                    }
                    TimeOutCounter.stop(id);
                    
                    BukkitRunnable runnable = new BukkitRunnable() {
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
                    runnable.runTaskLater(plugin, 3 * 20);
                }
            }
        }
    }
}
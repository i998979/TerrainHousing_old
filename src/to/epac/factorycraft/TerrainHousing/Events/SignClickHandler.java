package to.epac.factorycraft.TerrainHousing.Events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import to.epac.factorycraft.TerrainHousing.Main;
import to.epac.factorycraft.TerrainHousing.Utils.FileUtils;
import to.epac.factorycraft.TerrainHousing.Utils.SchemUtils;
import to.epac.factorycraft.TerrainHousing.Utils.TimeOutCounter;

public class SignClickHandler implements Listener {
	
	Plugin plugin = Main.getInstance();
	
	@EventHandler
	public void onSignClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getState() instanceof Sign) {
            	
            	Player player = event.getPlayer();
                Sign sign = (Sign) event.getClickedBlock().getState();
                Location signloc = sign.getLocation();
            	
                for (String id: FileUtils.getTerrains()) {
                	
                	Location loc = FileUtils.getSign(id);
                	
                    if (signloc.equals(loc)) {
                    	if (sign.hasMetadata("TerrainHousing:OccupiedBy")) {
                    		Bukkit.broadcastMessage(ChatColor.DARK_RED + "Do not spam click the sign!");
                    		return;
                    	}
                    	
                    	Skull head = (Skull) FileUtils.getHead(id).getBlock().getState();
                    		
                    	// Platform is not occupied
                    	if (FileUtils.getOccupiedBy(id) == null) {
                    		// Add cooldown on sign click
                    		sign.setMetadata("TerrainHousing:OccupiedBy", new FixedMetadataValue(plugin, System.currentTimeMillis()));
                            // Load schematic
                            SchemUtils.paste(player, id);
                            // Set occupation
                            FileUtils.setOccupiedBy(id, player);
                            // Update sign
                            for (int i = 0; i < 4; i++) {
                            	String line = FileUtils.getSignOccupied().get(i).replaceAll("%player%", player.getName());
                                sign.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
                                sign.update();
                            }
                            // Update skull
                            head.setOwningPlayer(player);
                            head.update();
                            
                            BukkitRunnable runnable = new BukkitRunnable() {
								@Override
								public void run() {
		                            sign.removeMetadata("TerrainHousing:OccupiedBy", plugin);
								}
                            };
                            runnable.runTaskLater(plugin, 3 * 20);
                            
                            TimeOutCounter.start(id, player, FileUtils.getIdleTime());
                        }
                    	// If someone is occupied the Housing
                    	else {
                    		Player occupied = Bukkit.getPlayer(UUID.fromString(FileUtils.getOccupiedBy(id)));
                    		
                    		// If clicked player occupied the Housing
	                    	if (player.equals(occupied)) {
	                    		// Add cooldown on sign click
	                    		sign.setMetadata("TerrainHousing:OccupiedBy", new FixedMetadataValue(plugin, System.currentTimeMillis()));
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
	                        // If it is occupied by other player
	                        else {
	                        	try {
	                        		player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN +
	                        				"This Housing is occupied by " + ChatColor.YELLOW +
	                        				occupied.getName() + ChatColor.GREEN + ".");
	                        	} catch (Exception e) {
	                        		player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN +
	                        				"This Housing is occupied.");
	                        	}
	                        }
	                    }
                    }
                }
            }
		}
	}
}

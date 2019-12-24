package to.epac.factorycraft.TerrainHousing.Events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import to.epac.factorycraft.TerrainHousing.Utils.FileUtils;

public class BuildHandler implements Listener {

    @EventHandler
    public void onPlayerBuild(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location loc = block.getLocation();

        for (String id: FileUtils.getTerrains()) {

            Location min = FileUtils.getMinimum(id);
            Location max = FileUtils.getMaximum(id);

            if (loc.getX() >= Math.min(min.getX(), max.getX()) &&
                loc.getY() >= Math.min(min.getY(), max.getY()) &&
                loc.getZ() >= Math.min(min.getZ(), max.getZ())) {

                if (loc.getX() <= Math.max(min.getX(), max.getX()) &&
                    loc.getY() <= Math.max(min.getY(), max.getY()) &&
                    loc.getZ() <= Math.max(min.getZ(), max.getZ())) {

                    if (FileUtils.getOccupiedBy(id) == null) {
                        if (!player.hasPermission("TerrainHousing.Admin")) {
                            event.setCancelled(true);
                            player.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW +
                                "Click the sign to claim before you can modify blocks here.");
                        }
                    } else {
                        Player occupied = Bukkit.getPlayer(UUID.fromString(FileUtils.getOccupiedBy(id)));

                        if (!player.equals(occupied)) {
                            event.setCancelled(true);
                            player.sendMessage(FileUtils.getPrefix() + ChatColor.RED +
                                "You cannot modify blocks in this area because " + ChatColor.YELLOW +
                                occupied.getName() + ChatColor.RED + " is occupied.");
                        } else {
                            if (FileUtils.getOverrideProtections())
                                if (event.isCancelled())
                                    event.setCancelled(false);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location loc = block.getLocation();

        for (String id: FileUtils.getTerrains()) {

            Location min = FileUtils.getMinimum(id);
            Location max = FileUtils.getMaximum(id);

            if (loc.getX() >= Math.min(min.getX(), max.getX()) &&
                loc.getY() >= Math.min(min.getY(), max.getY()) &&
                loc.getZ() >= Math.min(min.getZ(), max.getZ())) {

                if (loc.getX() <= Math.max(min.getX(), max.getX()) &&
                    loc.getY() <= Math.max(min.getY(), max.getY()) &&
                    loc.getZ() <= Math.max(min.getZ(), max.getZ())) {

                    if (FileUtils.getOccupiedBy(id) == null) {
                        if (!player.hasPermission("TerrainHousing.Admin")) {
                            event.setCancelled(true);
                            player.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW +
                                "Click the sign to claim before you can modify blocks here.");
                        }
                    } else {
                        Player occupied = Bukkit.getPlayer(UUID.fromString(FileUtils.getOccupiedBy(id)));

                        if (!player.equals(occupied)) {
                            event.setCancelled(true);
                            player.sendMessage(FileUtils.getPrefix() + ChatColor.RED +
                                "You cannot modify blocks in this area because " + ChatColor.YELLOW +
                                occupied.getName() + ChatColor.RED + " is occupied.");
                        } else {
                            if (FileUtils.getOverrideProtections())
                                if (event.isCancelled())
                                    event.setCancelled(false);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Location loc = null;
        try {
            loc = block.getLocation();
        } catch (NullPointerException e) {
            return;
        }

        for (String id: FileUtils.getTerrains()) {

            Location min = FileUtils.getMinimum(id);
            Location max = FileUtils.getMaximum(id);

            if (loc.getX() >= Math.min(min.getX(), max.getX()) &&
                loc.getY() >= Math.min(min.getY(), max.getY()) &&
                loc.getZ() >= Math.min(min.getZ(), max.getZ())) {

                if (loc.getX() <= Math.max(min.getX(), max.getX()) &&
                    loc.getY() <= Math.max(min.getY(), max.getY()) &&
                    loc.getZ() <= Math.max(min.getZ(), max.getZ())) {

                    if (FileUtils.getOccupiedBy(id) == null) {
                        if (!player.hasPermission("TerrainHousing.Admin")) {
                            event.setCancelled(true);
                            player.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW +
                                "Click the sign to claim before you can modify blocks here.");
                        }
                    } else {
                        Player occupied = Bukkit.getPlayer(UUID.fromString(FileUtils.getOccupiedBy(id)));

                        if (!player.equals(occupied)) {
                            event.setCancelled(true);
                            player.sendMessage(FileUtils.getPrefix() + ChatColor.RED +
                                "You cannot modify blocks in this area because " + ChatColor.YELLOW +
                                occupied.getName() + ChatColor.RED + " is occupied.");
                        } else {
                            if (FileUtils.getOverrideProtections())
                                if (event.isCancelled())
                                    event.setCancelled(false);
                        }
                    }
                }
            }
        }
    }
}
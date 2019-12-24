package to.epac.factorycraft.TerrainHousing.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import to.epac.factorycraft.TerrainHousing.Utils.FileUtils;
import to.epac.factorycraft.TerrainHousing.Utils.SchemUtils;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            HelpPage(sender);
            return false;
        }
        
        if (!sender.hasPermission("TerrainHousing.Admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
            return false;
        }

        if (args[0].equalsIgnoreCase("enabled")) {
        	if (args.length == 1) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter true/false.");
        		return false;
        	}
        	if (!args[1].equalsIgnoreCase("true") && !args[1].equalsIgnoreCase("false")) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter true/false.");
        		return false;
        	}
        	boolean override = Boolean.parseBoolean(args[1]);
        	FileUtils.setOverrideProtections(override);
        	if (override)
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW + "TerrainHousing is now" +
            			ChatColor.GREEN + " enabled" + ChatColor.YELLOW + ".");
        	else
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW + "TerrainHousing is now" +
            			ChatColor.RED + " disabled" + ChatColor.YELLOW + ".");
        }
        /*else if (args[0].equalsIgnoreCase("height")) {
        	
        }*/
        else if (args[0].equalsIgnoreCase("idle")) {
        	if (args.length == 1) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter an integer (in tick).");
        		return false;
        	}
        	long idle;
        	try {
        		idle = Long.parseLong(args[1]);
        	} catch (NumberFormatException e) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter a valid integer (in tick).");
        		return false;
        	}
        	FileUtils.setIdleTime(idle);
        	sender.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW + "Player will auto unclaim the Housing if they "
        			+ "didn't modify blocks after " + ChatColor.GREEN + idle + ChatColor.YELLOW + " ticks.");
        }
        else if (args[0].equalsIgnoreCase("overrideprotection")) {
        	if (args.length == 1) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter true/false.");
        		return false;
        	}
        	if (!args[1].equalsIgnoreCase("true") && !args[1].equalsIgnoreCase("false")) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter true/false.");
        		return false;
        	}
        	boolean override = Boolean.parseBoolean(args[1]);
        	FileUtils.setOverrideProtections(override);
        	if (override)
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW + "Override Protection is now" +
            			ChatColor.GREEN + "enabled" + ChatColor.YELLOW + ".");
        	else
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.YELLOW + "Override Protection is now" +
            			ChatColor.RED + "disabled" + ChatColor.YELLOW + ".");
        }
        else if (args[0].equalsIgnoreCase("save")) {
        	if (args.length == 1) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter a Housing id.");
        		return false;
        	}
        	if (!FileUtils.getTerrains().contains(args[1])) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Housing id you've entered does not exist.");
        		return false;
        	}
        	if (args.length == 2) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter a player name.");
        		return false;
        	}
        	try {
        		Player target = Bukkit.getPlayer(args[2]);
        		SchemUtils.save(target, args[1]);
            	sender.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "Housing " + args[1] + " saved to " + target.getName() + ".");
        	} catch (Exception e) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Player name you have entered does not exist.");
        		return false;
        	}
        }
        else if (args[0].equalsIgnoreCase("load")) {
        	if (args.length == 1) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter a Housing id.");
        		return false;
        	}
        	if (!FileUtils.getTerrains().contains(args[1])) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Housing id you've entered does not exist.");
        		return false;
        	}
        	if (args.length == 2) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter a player name.");
        		return false;
        	}
        	try {
        		Player target = Bukkit.getPlayer(args[2]);
        		SchemUtils.paste(target, args[1]);
            	sender.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "Loaded " + target.getName() + " Housing into " + args[1] + ".");
        	} catch (Exception e) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Player name you have entered does not exist.");
        		return false;
        	}
        }
        else if (args[0].equalsIgnoreCase("reset")) {
        	if (args.length == 1) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter a Housing id.");
        		return false;
        	}
        	if (!FileUtils.getTerrains().contains(args[1])) {
        		sender.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Housing id you've entered does not exist.");
        		return false;
        	}
        	SchemUtils.reset(args[1]);
        	sender.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "Housing reset.");
        }
        else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
                return false;
            }

            Player player = (Player) sender;
            Location loc = player.getLocation();
            Location accr = loc;
            accr.setX(accr.getBlockX()); accr.setY(accr.getBlockY()); accr.setZ(accr.getBlockZ());
            accr.setPitch(0); accr.setYaw(0);
            
            if (args[0].equalsIgnoreCase("setMin")) {
            	if (args.length == 1) {
            		player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter a Housing id.");
            		return false;
            	}
            	if (!FileUtils.getTerrains().contains(args[1])) {
            		player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Housing id you've entered does not exist.");
            	}
            	
            	FileUtils.setMinimum(args[1], accr);
            	player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "Minimum location set.");
            }
            else if (args[0].equalsIgnoreCase("setMax")) {
            	if (args.length == 1) {
            		player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter a Housing id.");
            		return false;
            	}
            	if (!FileUtils.getTerrains().contains(args[1])) {
            		player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Housing id you've entered does not exist.");
            	}
            	
            	FileUtils.setMaximum(args[1], accr);
            	player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "Maximum location set.");
            }
            else if (args[0].equalsIgnoreCase("setSign")) {
            	if (args.length == 1) {
            		player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter a Housing id.");
            		return false;
            	}
            	if (!FileUtils.getTerrains().contains(args[1])) {
            		player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Housing id you've entered does not exist.");
            	}
            	
            	FileUtils.setSign(args[1], accr);
            	player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "Sign location set.");
            }
            else if (args[0].equalsIgnoreCase("setHead")) {
            	if (args.length == 1) {
            		player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Please enter a Housing id.");
            		return false;
            	}
            	if (!FileUtils.getTerrains().contains(args[1])) {
            		player.sendMessage(FileUtils.getPrefix() + ChatColor.RED + "Housing id you've entered does not exist.");
            	}
            	
            	FileUtils.setHead(args[1], accr);
            	player.sendMessage(FileUtils.getPrefix() + ChatColor.GREEN + "Head location set.");
            }
        }
        return false;
    }

    public void HelpPage(CommandSender sender) {
    	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7-------------------" + FileUtils.getPrefix() + "&7-------------------"));
    	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Main command: &e/TerrainHousing, /th, /trh"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c<>: Required &d[]: Optional"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th Enabled &c<true/false>&b: &3Enable/Disable TerrainHousing."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th Idle &c<ticks>&b: &3Set how long will players auto unclaim Housing if they don't modify blocks."));
        // sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th Height &c<Id> <integer>&b: &3Set max build height limit."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th overrideprotection  &c<true/false>&b: "
        		+ "&3Let players build even if region protection plugin is active. &3&l*Those message will still sent to players, "
        		+ "just they can build bypass it*&3."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th Idle  &c<Id> <integer>&b: &3Set max build time limit."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th Save  &c<Id> <Player>&b: &3Force save Housing into player's data."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th Load  &c<Id> <Player>&b: &3Force load Housing from player's data."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th Reset  &c<Id>&b: &3Reset Housing into default state."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th SetMin &c<Id>&b: &3Set current location as minimum part of the Housing, player can only build between Min&Max area."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th SetMax &c<Id>&b: &3Set current location as maximum part of the Housing, player can only build between Min&Max area."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th SetSign &c<Id>&b: &3Set current location as Sign Display."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/th SetHead &c<Id>&b: &3Set current location as Head Dispaly."));
    	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7-------------------" + FileUtils.getPrefix() + "&7-------------------"));
    }
}
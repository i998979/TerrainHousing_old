package to.epac.factorycraft.TerrainHousing.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;

import to.epac.factorycraft.TerrainHousing.Main;

public class SchemUtils {
    private static Plugin plugin = Main.getInstance();
    
    public static void reset(String id) {

        Location min = FileUtils.getMinimum(id);
        Location max = FileUtils.getMaximum(id);

        Clipboard clipboard;
        File file = new File(plugin.getDataFolder(), "schematics" + File.separator + "default.schem");

        ClipboardFormat format = ClipboardFormats.findByFile(file);

        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(min.getWorld()), -1)) {
            ClipboardHolder holder = new ClipboardHolder(clipboard);
            Operation operation = holder
                .createPaste(editSession)
                // It paste at Max's X Z value
                // and Min's Y value
                .to(BlockVector3.at(
                    max.getX(),
                    min.getY(),
                    max.getZ()))
                // If false, grass will be removed and replaced with air
                // If true, grass will stay and will not be replaced
                .ignoreAirBlocks(false)
                .build();
            Operations.complete(operation);
            // TODO
            editSession.close();
            // Save clipboard to undo/redo
            // WorldEdit.getInstance().getSessionManager().findByName(player.getName()).remember(editSession);
        } catch (WorldEditException e) {
            System.err.println("There was an error pasting schematic \"" + clipboard.toString() + "\"");
        }
    }
    public static void save(Player player, String id) {
        String uid = player.getUniqueId().toString();
        World world = new BukkitWorld(player.getWorld());

        Location min = FileUtils.getMinimum(id);
        Location max = FileUtils.getMaximum(id);

        BlockVector3 vMin = BlockVector3.at(min.getX(), min.getY(), min.getZ());
        BlockVector3 vMax = BlockVector3.at(max.getX(), max.getY(), max.getZ());

        CuboidRegion region = new CuboidRegion(world, vMin, vMax);
        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(region.getWorld(), -1);

        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());
        forwardExtentCopy.setCopyingEntities(true);
        
        try {
			Operations.complete(forwardExtentCopy);
		} catch (WorldEditException e) {
			e.printStackTrace();
		}

        File file = new File(plugin.getDataFolder(), "schematics" + File.separator + uid + ".schem");

        try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))) {
            writer.write(clipboard);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO
        editSession.close();
    }

    public static void paste(Player player, String id) {
        String uid = player.getUniqueId().toString();
        
        Location min = FileUtils.getMinimum(id);
        Location max = FileUtils.getMaximum(id);

        Clipboard clipboard;
        File file = new File(plugin.getDataFolder(), "schematics" + File.separator + uid + ".schem");

        ClipboardFormat format = ClipboardFormats.findByFile(file);

        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();

        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(min.getWorld()), -1)) {
            ClipboardHolder holder = new ClipboardHolder(clipboard);
            Operation operation = holder
                .createPaste(editSession)
                // It paste at Max's X Z value
                // and Min's Y value
                .to(BlockVector3.at(
                    max.getX(),
                    min.getY(),
                    max.getZ()))
                // If false, grass will be removed and replaced with air
                // If true, grass will stay and will not be replaced
                .ignoreAirBlocks(false)
                .build();
            Operations.complete(operation);
            // TODO
            editSession.close();
            // Save clipboard to undo/redo
            // WorldEdit.getInstance().getSessionManager().findByName(player.getName()).remember(editSession);
        } catch (WorldEditException e) {
            System.err.println("There was an error pasting schematic \"" + clipboard.toString() + "\"");
        }
    }
}
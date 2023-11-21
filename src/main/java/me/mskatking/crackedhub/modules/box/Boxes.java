package me.mskatking.crackedhub.modules.box;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.box.mechanic.Box;
import me.mskatking.crackedhub.util.ConfigHelper;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.Errors;
import me.mskatking.crackedhub.util.events.PluginShutdownEvent;
import me.mskatking.crackedhub.util.events.PluginStartupEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Boxes implements Listener {

    private boolean enabled = false;
    public MultiverseWorld boxOverworld, boxNether, boxEnd;
    private FileConfiguration config = new YamlConfiguration();
    private File f;
    public final ArrayList<Box> boxes = new ArrayList<>();

    @EventHandler
    public void startup(PluginStartupEvent e) {
        f = ConfigHelper.getFile("boxes.yml");
        config = ConfigHelper.getConfig("boxes.yml");

        enabled = e.getConfig().getBoolean("modules.boxes");

        boxOverworld = CrackedHub.core.getMVWorldManager().getMVWorld("box_overworld");
        boxNether = CrackedHub.core.getMVWorldManager().getMVWorld("box_nether");
        boxEnd = CrackedHub.core.getMVWorldManager().getMVWorld("box_end");
    }

    @EventHandler
    public void shutdown(PluginShutdownEvent e) {
        Console.info("Shutting down boxes module...");
        try {
            config.save(f);
        } catch (IOException ex) {
            Console.warn(Errors.BOXES_SAVE_WARN.toString());
        }
        Console.info("Boxes module shut down!");
    }
}

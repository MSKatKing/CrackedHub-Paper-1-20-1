package me.mskatking.crackedhub.modules.box;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.box.mechanic.Box;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.Errors;
import me.mskatking.crackedhub.util.Module;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CrackedHubBox implements Module {

    private boolean enabled;
    public final MultiverseWorld boxOverworld, boxNether, boxEnd;
    private final FileConfiguration config = new YamlConfiguration();
    private final File f;
    public final ArrayList<Box> boxes = new ArrayList<>();

    public CrackedHubBox() {
        File path = new File(String.valueOf(CrackedHub.getPlugin().getDataFolder()));
        if(!path.exists()) {
            boolean ignored = path.mkdirs();
        }
        this.f = new File(path, "boxes.yml");
        try {
            if(!f.exists()) {
                boolean ignored = f.createNewFile();
            }
            config.load(f);
        } catch (InvalidConfigurationException e) {
            Console.error("Box YAML is not valid!");
        } catch (Exception e) {
            Console.error(e.getMessage());
        }

        boxOverworld = CrackedHub.core.getMVWorldManager().getMVWorld("box_overworld");
        boxNether = CrackedHub.core.getMVWorldManager().getMVWorld("box_nether");
        boxEnd = CrackedHub.core.getMVWorldManager().getMVWorld("box_end");
    }

    @Override
    public void enable() {
        enabled = true;
    }

    @Override
    public void disable() {
        enabled = false;
    }

    @Override
    public void shutdown() {
        Console.info("Shutting down boxes module...");
        try {
            config.save(f);
        } catch (IOException e) {
            Console.warn(Errors.BOXES_SAVE_WARN.toString());
        }
        Console.info("Boxes module shut down!");
    }

    @Override
    public boolean save() {
        try {
            config.save(f);
        } catch (Exception e) {
            Console.error(Errors.BOXES_SAVE_ERROR.toString());
            return false;
        }
        return true;
    }

    @Override
    public boolean initializeFromConfig() {
        return true;
    }

    @Override
    public Component getPrefix() {
        return null;
    }
}

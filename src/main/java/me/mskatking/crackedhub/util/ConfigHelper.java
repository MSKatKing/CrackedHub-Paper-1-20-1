package me.mskatking.crackedhub.util;

import me.mskatking.crackedhub.CrackedHub;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigHelper {

    public static FileConfiguration getConfig(String file) {
        FileConfiguration out = new YamlConfiguration();
        try {
            out.load(getFile(file));
        } catch (IOException e) {
            Console.error("Unable to open file '" + file + "'. Does it exist?");
        } catch (InvalidConfigurationException e) {
            Console.error("Invalid YAML file '" + file + "'.");
        }
        return out;
    }

    public static File getFile(String fileName) {
        File path = new File(String.valueOf(CrackedHub.getPlugin().getDataFolder()));
        if(!path.exists()) {
            boolean ignored = path.mkdirs();
        }
        return new File(path, fileName);
    }

    public static void saveConfig(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            Console.error("Unable to save file '" + file + "'.");
        }
    }
}

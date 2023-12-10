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

    public static File getFile(String folder, String fileName) {
        File path = new File(String.valueOf(CrackedHub.getPlugin().getDataFolder()) + "/" + folder);
        if(!path.exists()) {
            boolean ignored = path.mkdirs();
        }
        File f = new File(path, fileName);
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return f;
    }

    public static FileConfiguration getConfig(String folder, String file) {
        FileConfiguration out = new YamlConfiguration();
        try {
            out.load(getFile(folder, file));
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

    public static void applyConfigDefaults(FileConfiguration config, File f, Configs type) {
        switch (type) {
            case MAIN -> {
                if(!config.isSet("modules.box")) config.set("modules.box", true);
                if(!config.isSet("modules.ranks")) config.set("modules.ranks", true);
                if(!config.isSet("modules.admin")) config.set("modules.admin", true);
                if(!config.isSet("modules.randomkit")) config.set("modules.randomkit", true);
                if(!config.isSet("modules.dupelifesteal")) config.set("modules.dupelifesteal", true);

                if(!config.isSet("server-type.alpha")) config.set("server-type.alpha", false);
                if(!config.isSet("server-type.normal")) config.set("server-type.normal", true);

                if(!config.isSet("randomkit.closed")) config.set("randomkit.closed", false);
                if(!config.isSet("randomkit.closedMessage")) config.set("randomkit.closedMessage", "-");

                if(!config.isSet("box.closed")) config.set("box.closed", false);
                if(!config.isSet("box.closedMessage")) config.set("box.closedMessage", "-");

                if(!config.isSet("skyblock.closed")) config.set("skyblock.closed", false);
                if(!config.isSet("skyblock.closedMessage")) config.set("skyblock.closedMessage", "-");

                if(!config.isSet("sql.address")) config.set("sql.address", "-");
                if(!config.isSet("sql.username")) config.set("sql.username", "-");
                if(!config.isSet("sql.password")) config.set("sql.password", "-");

                saveConfig(config, f);
                break;
            }
            case DISCORD_MAIN -> {
                if(!config.isSet("token")) config.set("token", "-");

                if(!config.isSet("channels.test")) config.set("channels.test", "-");
                if(!config.isSet("channels.listenForLinkCommand")) config.set("channels.listenForLinkCommand", "-");
                if(!config.isSet("channels.mcToDiscordChannel")) config.set("channels.mcToDiscordChannel", "-");
                if(!config.isSet("channels.guildID")) config.set("channels.guildID", "-");

                saveConfig(config, f);
                break;
            }
        }
    }

    public enum Configs {
        MAIN,
        DISCORD_MAIN;
    }
}

package me.mskatking.crackedhub;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.mskatking.crackedhub.modules.box.CrackedHubBox;
import me.mskatking.crackedhub.modules.randomkit.CrackedHubRandomKit;
import me.mskatking.crackedhub.modules.ranks.CrackedHubRanks;
import me.mskatking.crackedhub.util.Console;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public final class CrackedHub extends JavaPlugin {

    public static ArrayList<String> developers = new ArrayList<>();

    public static final MultiverseCore core = MultiverseCore.getPlugin(MultiverseCore.class);
    private static CrackedHub instance;

    public static CrackedHubBox boxModule;
    public static CrackedHubRanks ranksModule;
    public static CrackedHubRandomKit randomKitModule;

    public static FileConfiguration config = new YamlConfiguration();
    public static File f;

    @Override
    public void onEnable() {
        instance = this;

        boxModule = new CrackedHubBox();
        ranksModule = new CrackedHubRanks();
        randomKitModule = new CrackedHubRandomKit();

        File path = new File(String.valueOf(CrackedHub.getPlugin().getDataFolder()));
        if(!path.exists()) {
            boolean ignored = path.mkdirs();
        }
        f = new File(path, "config.yml");
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

        if (!config.contains("modules.box")) config.set("modules.box", true);
        if (!config.contains("modules.ranks")) config.set("modules.ranks", true);
        if (!config.contains("modules.admin")) config.set("modules.admin", true);
        if (!config.contains("modules.randomkit")) config.set("modules.randomkit", true);

        if(config.getBoolean("modules.box")) boxModule.enable();
        if(config.getBoolean("modules.ranks")) ranksModule.enable();
        if(config.getBoolean("modules.randomkit")) randomKitModule.enable();

        Console.info("CrackedHub enabled!");
    }

    @Override
    public void onDisable() {
        Console.info("Shutting down modules...");
        boxModule.shutdown();
        ranksModule.shutdown();
        randomKitModule.shutdown();

        try {
            config.save(f);
        } catch (Exception e) {
            Console.error("Error saving configs! Data has been lost!");
        }

        Console.info("CrackedHub disabled!");
    }

    public static CrackedHub getPlugin() {
        return instance;
    }
}

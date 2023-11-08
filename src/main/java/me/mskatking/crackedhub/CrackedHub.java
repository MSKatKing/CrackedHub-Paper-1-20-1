package me.mskatking.crackedhub;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.mskatking.crackedhub.commands.RebootMessage;
import me.mskatking.crackedhub.modules.box.CrackedHubBox;
import me.mskatking.crackedhub.modules.randomkit.CrackedHubRandomKit;
import me.mskatking.crackedhub.modules.ranks.CrackedHubRanks;
import me.mskatking.crackedhub.util.ConfigHelper;
import me.mskatking.crackedhub.util.Console;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
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
    public static Connection connection = null;

    @Override
    public void onEnable() {
        instance = this;

        boxModule = new CrackedHubBox();
        ranksModule = new CrackedHubRanks();
        randomKitModule = new CrackedHubRandomKit();

        f = ConfigHelper.getFile("config.yml");
        config = ConfigHelper.getConfig("config.yml");

        if (!config.contains("modules.box")) config.set("modules.box", true);
        if (!config.contains("modules.ranks")) config.set("modules.ranks", true);
        if (!config.contains("modules.admin")) config.set("modules.admin", true);
        if (!config.contains("modules.randomkit")) config.set("modules.randomkit", true);

        if(config.getBoolean("modules.box")) boxModule.enable();
        if(config.getBoolean("modules.ranks")) ranksModule.enable();
        if(config.getBoolean("modules.randomkit")) randomKitModule.enable();

        getServer().getCommandMap().register("crackedhub", new me.mskatking.crackedhub.commands.CrackedHub());
        getServer().getCommandMap().register("crackedhub", new RebootMessage());

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

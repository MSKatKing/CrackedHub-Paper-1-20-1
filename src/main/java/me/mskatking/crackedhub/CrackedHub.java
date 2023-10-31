package me.mskatking.crackedhub;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.mskatking.crackedhub.modules.box.CrackedHubBox;
import me.mskatking.crackedhub.modules.ranks.CrackedHubRanks;
import me.mskatking.crackedhub.modules.ranks.commands.SetRank;
import me.mskatking.crackedhub.events.Events;
import me.mskatking.crackedhub.events.Hello;
import me.mskatking.crackedhub.util.Console;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CrackedHub extends JavaPlugin {

    public static FileConfiguration config = new YamlConfiguration();
    public static File f;

    public static ArrayList<String> developers = new ArrayList<>();

    public static final MultiverseCore core = MultiverseCore.getPlugin(MultiverseCore.class);

    public static CrackedHubBox boxModule;
    public static CrackedHubRanks ranksModule;

    @Override
    public void onEnable() {
        boxModule = new CrackedHubBox();
        ranksModule = new CrackedHubRanks();

        getServer().getPluginManager().registerEvents(new Events(), this);

        getServer().getCommandMap().register("hello", new Hello("hello"));

        Console.info("CrackedHub enabled!");
    }

    @Override
    public void onDisable() {
        try {
            config.save(f);
        } catch (Exception e) {
            Console.error("Unable to save ranks!");
        }
        Console.info("CrackedHub disabled!");
    }
}

package me.mskatking.crackedhub.modules.ranks;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.ranks.commands.Ranks;
import me.mskatking.crackedhub.modules.ranks.commands.SetRank;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.Errors;
import me.mskatking.crackedhub.util.Module;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CrackedHubRanks implements Module {

    private boolean enabled;
    public ArrayList<Rank> ranks = new ArrayList<>();

    private final HashMap<String, Rank> players = new HashMap<>();

    public final FileConfiguration config = new YamlConfiguration();
    private final File f;
    private final Plugin p = CrackedHub.getPlugin(CrackedHub.class);

    public CrackedHubRanks() {
        File folder = new File(String.valueOf(CrackedHub.getPlugin(CrackedHub.class).getDataFolder()));
        if(!folder.exists()) folder.mkdirs();
        f = new File(folder, "ranks.yml");
        try {
            if(!f.exists()) f.createNewFile();
            config.load(f);
        } catch (InvalidConfigurationException e) {
            Console.error("rank.yml is invalid!");
        } catch (Exception e) {
            Console.error("Unable to load ranks!");
        }

        for(String s : config.getKeys(false)) {
            ranks.add(config.getSerializable(s, Rank.class));
        }

        p.getServer().getCommandMap().register("ranks", new Ranks());
        p.getServer().getCommandMap().register("getrank", new SetRank());
    }

    public void disable() {
        Console.info("Disabling rank module...");
        shutdown();
        enabled = false;
        Console.info("Rank module disabled!");
    }

    @Override
    public void shutdown() {
        Console.info("Shutting down rank module...");
        try {
            config.save(f);
        } catch (IOException e) {
            Console.warn(Errors.RANKS_SAVE_WARN.toString());
        }
        Console.info("Rank module shut down!");
    }

    @Override
    public boolean save() {
        try {
            config.save(f);
        } catch (Exception e) {
            Console.info(Errors.RANKS_SAVE_ERROR.toString());
            return false;
        }
        return true;
    }

    public void enable() {
        Console.info("Enabling rank module...");
        enabled = true;
        Console.info("Rank module enabled!");
    }
}

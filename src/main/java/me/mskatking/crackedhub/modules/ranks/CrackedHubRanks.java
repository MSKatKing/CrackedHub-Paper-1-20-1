package me.mskatking.crackedhub.modules.ranks;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.ranks.commands.Ranks;
import me.mskatking.crackedhub.modules.ranks.commands.SetRank;
import me.mskatking.crackedhub.util.Console;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;

public class CrackedHubRanks {

    public ArrayList<Rank> ranks = new ArrayList<>();
    private final FileConfiguration config = new YamlConfiguration();
    private final Plugin p = CrackedHub.getPlugin(CrackedHub.class);

    public CrackedHubRanks() {
        File f = new File(String.valueOf(CrackedHub.getPlugin(CrackedHub.class).getDataFolder()));
        if(!f.exists()) f.mkdirs();
        f = new File(f, "ranks.yml");
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

    public static void disable() {

    }

    public static void enable() {

    }
}

package me.mskatking.crackedhub.modules.ranks;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.ranks.commands.SetRank;
import me.mskatking.crackedhub.modules.ranks.events.RankEvents;
import me.mskatking.crackedhub.util.ConfigHelper;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.Errors;
import me.mskatking.crackedhub.util.events.PluginShutdownEvent;
import me.mskatking.crackedhub.util.events.PluginStartupEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class Ranks implements Listener {

    public FileConfiguration config;
    private File f;

    public Ranks() {
        CrackedHub.getPlugin().getServer().getPluginManager().registerEvents(this, CrackedHub.getPlugin());
    }

    @EventHandler
    public void startup(PluginStartupEvent e) {
        f = ConfigHelper.getFile("ranks.yml");
        config = ConfigHelper.getConfig("ranks.yml");

        if(e.getConfig().getBoolean("modules.ranks")) {
            Console.info("Enabling rank module...");
            e.getPlugin().getServer().getCommandMap().register("crackedhub", new me.mskatking.crackedhub.modules.ranks.commands.Ranks());
            e.getPlugin().getServer().getCommandMap().register("crackedhub", new SetRank());
            e.getPlugin().getServer().getPluginManager().registerEvents(new RankEvents(), e.getPlugin());
            Console.info("Rank module enabled!");
        }
    }

    @EventHandler
    public void shutdown(PluginShutdownEvent e) {
        Console.info("Shutting down rank module...");
        try {
            config.save(f);
        } catch (IOException ex) {
            Console.warn(Errors.RANKS_SAVE_WARN.toString());
        }
        Console.info("Rank module shut down!");
    }
}

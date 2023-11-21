package me.mskatking.crackedhub.modules.ranks;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.ranks.commands.Ranks;
import me.mskatking.crackedhub.modules.ranks.commands.SetRank;
import me.mskatking.crackedhub.modules.ranks.events.RankEvents;
import me.mskatking.crackedhub.util.ConfigHelper;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.Errors;
import me.mskatking.crackedhub.util.Module;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CrackedHubRanks implements Module {

    private boolean enabled;

    public FileConfiguration config;
    private final File f;
    private final Plugin p = CrackedHub.getPlugin(CrackedHub.class);

    public CrackedHubRanks() {
        f = ConfigHelper.getFile("ranks.yml");
        config = ConfigHelper.getConfig("ranks.yml");
    }

    public void disable() {
        Console.info("Disabling rank module...");
        shutdown();
        enabled = false;
        p.getServer().getCommandMap().getCommand("ranks").unregister(p.getServer().getCommandMap());
        p.getServer().getCommandMap().getCommand("setrank").unregister(p.getServer().getCommandMap());
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

    @Override
    public boolean initializeFromConfig() {
        return true;
    }

    @Override
    public Component getPrefix() {
        return Component.text("[", NamedTextColor.GOLD).append(Component.text("Ranks", NamedTextColor.YELLOW)).append(Component.text("] ", NamedTextColor.GOLD));
    }

    public void enable() {
        Console.info("Enabling rank module...");
        enabled = true;
        p.getServer().getCommandMap().register("crackedhub", new Ranks());
        p.getServer().getCommandMap().register("crackedhub", new SetRank());
        p.getServer().getPluginManager().registerEvents(new RankEvents(), p);
        Console.info("Rank module enabled!");
    }
}

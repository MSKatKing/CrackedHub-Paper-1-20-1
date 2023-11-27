package me.mskatking.crackedhub;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.mskatking.crackedhub.commands.Lobby;
import me.mskatking.crackedhub.commands.RebootMessage;
import me.mskatking.crackedhub.commands.Teleport;
import me.mskatking.crackedhub.modules.box.Boxes;
import me.mskatking.crackedhub.modules.dupelifesteal.DupeLifesteal;
import me.mskatking.crackedhub.modules.main.MainModule;
import me.mskatking.crackedhub.modules.randomkit.RandomKit;
import me.mskatking.crackedhub.modules.ranks.Ranks;
import me.mskatking.crackedhub.util.ConfigHelper;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.CrackedHubPlayer;
import me.mskatking.crackedhub.util.events.PluginShutdownEvent;
import me.mskatking.crackedhub.util.events.PluginStartupEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public final class CrackedHub extends JavaPlugin {

    public static ArrayList<CrackedHubPlayer> onlinePlayers = new ArrayList<>();

    public static final MultiverseCore core = MultiverseCore.getPlugin(MultiverseCore.class);
    private static CrackedHub instance;

    public static Boxes boxModule;
    public static Ranks ranksModule;
    public static RandomKit randomKitModule;
    public static DupeLifesteal dupeLifesteal;

    public static FileConfiguration config = new YamlConfiguration();
    public static File f;

    public static JDA bot;

    @Override
    public void onEnable() {
        Console.info("Initilizing Core features.");
        instance = this;

        boxModule = new Boxes();
        ranksModule = new Ranks();
        randomKitModule = new RandomKit();
        dupeLifesteal = new DupeLifesteal();

        getServer().getPluginManager().registerEvents(boxModule, this);
        getServer().getPluginManager().registerEvents(ranksModule, this);
        getServer().getPluginManager().registerEvents(randomKitModule, this);
        getServer().getPluginManager().registerEvents(dupeLifesteal, this);

        f = ConfigHelper.getFile("config.yml");
        config = ConfigHelper.getConfig("config.yml");

        bot = JDABuilder.createLight(config.getString("discord.token"), GatewayIntent.MESSAGE_CONTENT).setEventManager(new AnnotatedEventManager()).addEventListeners(new DiscordListener()).setActivity(Activity.playing("CrackedHub Server")).setStatus(OnlineStatus.ONLINE).build();

        if (!config.contains("modules.box")) config.set("modules.box", true);
        if (!config.contains("modules.ranks")) config.set("modules.ranks", true);
        if (!config.contains("modules.admin")) config.set("modules.admin", true);
        if (!config.contains("modules.randomkit")) config.set("modules.randomkit", true);
        if (!config.contains("modules.dupelifesteal")) config.set("modules.dupelifesteal", true);

        getServer().getCommandMap().register("crackedhub", new RebootMessage());
        getServer().getCommandMap().register("crackedhub", new Teleport());
        getServer().getCommandMap().register("crackedhub", new Lobby());

        getServer().getPluginManager().registerEvents(new MainEvents(), this);
        getServer().getPluginManager().registerEvents(new MainModule(), this);

        Bukkit.getPluginManager().callEvent(new PluginStartupEvent(instance, core, config));

        Console.info("CrackedHub enabled!");
        System.gc();
    }

    @Override
    public void onDisable() {
        Console.info("Shutting down modules...");
        Bukkit.getPluginManager().callEvent(new PluginShutdownEvent(instance, core));

        bot.shutdown();

        try {
            config.save(f);
        } catch (Exception e) {
            Console.error("Error saving configs! Data has been lost!");
        }

        Console.info("CrackedHub disabled!");
        System.gc();
    }

    public static CrackedHub getPlugin() {
        return instance;
    }
}

package me.mskatking.crackedhub;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.mskatking.crackedhub.commands.*;
import me.mskatking.crackedhub.modules.dupelifesteal.DupeLifesteal;
import me.mskatking.crackedhub.modules.main.MainModule;
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
import java.util.Arrays;
import java.util.Random;

public final class CrackedHub extends JavaPlugin {

    public static ArrayList<CrackedHubPlayer> onlinePlayers = new ArrayList<>();

    public static final MultiverseCore core = MultiverseCore.getPlugin(MultiverseCore.class);
    private static CrackedHub instance;

    public static Ranks ranksModule;
    public static DupeLifesteal dupeLifesteal;

    public static FileConfiguration config = new YamlConfiguration();
    public static File f;

    public static JDA bot;

    public static boolean alpha;

    @Override
    public void onEnable() {
        Console.info("Initilizing Core features.");
        instance = this;

        ranksModule = new Ranks();
        dupeLifesteal = new DupeLifesteal();

        f = ConfigHelper.getFile("configs/main", "config.yml");
        config = ConfigHelper.getConfig("configs/main", "config.yml");

        ConfigHelper.applyConfigDefaults(config, f, ConfigHelper.Configs.MAIN);

        alpha = config.getBoolean("server-type.alpha");

        if(alpha) {
            Console.warn("!! WARNING !!");
            Console.warn("You are starting in alpha mode! Please only do this if you know what you are doing!");
            Console.warn("If this server should not be in alpha mode, please shut down the server and change the config!!");
        }

        if(ConfigHelper.getConfig("configs/discord", "config.yml").getString("token").equals("-")) {
            Console.warn("Discord config not set up properly! Shutting down discord functionality.");
        } else {
            bot = JDABuilder.createLight(ConfigHelper.getConfig("configs/discord", "config.yml").getString("token"), GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES).setEventManager(new AnnotatedEventManager()).addEventListeners(new DiscordListener()).setActivity(Activity.playing("CrackedHub Server")).setStatus(OnlineStatus.ONLINE).build();
            getServer().getCommandMap().register("crackedhub", new Link());
            getServer().getCommandMap().register("crackedhub", new Unlink());
        }

        getServer().getCommandMap().register("crackedhub", new RebootMessage());
        getServer().getCommandMap().register("crackedhub", new Teleport());
        getServer().getCommandMap().register("crackedhub", new Lobby());
        getServer().getCommandMap().register("crackedhub", new BackupSQL());

        getServer().getPluginManager().registerEvents(new MainEvents(), this);
        getServer().getPluginManager().registerEvents(new MainModule(), this);
        getServer().getPluginManager().registerEvents(new DiscordListener(), this);

        Bukkit.getPluginManager().callEvent(new PluginStartupEvent(instance, core, config));

        Console.info("CrackedHub enabled!");

        if(bot != null) {
            ((DiscordListener) bot.getEventManager().getRegisteredListeners().get(0)).init();
        }

        ConfigHelper.saveConfig(config, f);

        System.gc();
    }

    @Override
    public void onDisable() {
        Console.info("Shutting down modules...");
        Bukkit.getPluginManager().callEvent(new PluginShutdownEvent(instance, core));

        if(bot != null) bot.shutdown();

        Console.info("CrackedHub disabled!");
        System.gc();
    }

    public static CrackedHub getPlugin() {
        return instance;
    }

    public static String randomString(int length) {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (rightLimit - leftLimit + 1));
            while(Arrays.asList(58, 59, 60, 61, 62, 63, 64, 91, 92, 93, 94, 95, 96).contains(randomLimitedInt)) {
                randomLimitedInt = leftLimit + (int)(random.nextFloat() * (rightLimit - leftLimit + 1));
            }
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }
}

package me.mskatking.crackedhub.modules.dupelifesteal;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.dupelifesteal.commands.DuperCraft;
import me.mskatking.crackedhub.modules.dupelifesteal.events.DupeLifestealListener;
import me.mskatking.crackedhub.util.ConfigHelper;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.events.PluginReloadEvent;
import me.mskatking.crackedhub.util.events.PluginShutdownEvent;
import me.mskatking.crackedhub.util.events.PluginStartupEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DupeLifesteal implements Listener {

    public static FileConfiguration config;
    public static File f;
    public static FileConfiguration configb;
    public static File fb;

    public static FileConfiguration configcrafting;
    public static File fcrafting;
    public static ArrayList<NamespacedKey> bannedKeys = new ArrayList<>();

    public DupeLifesteal() {
        CrackedHub.getPlugin().getServer().getPluginManager().registerEvents(this, CrackedHub.getPlugin());
    }

    @EventHandler
    public void startup(PluginStartupEvent e) {
        Console.info("Initializing Lifesteal...");
        e.registerCommand(new DuperCraft());
        e.registerEvent(new DupeLifestealListener());
        configb = ConfigHelper.getConfig("configs/dupelifesteal", "banneddupes.yml");
        fb = ConfigHelper.getFile("configs/dupelifesteal", "banneddupes.yml");
        config = ConfigHelper.getConfig("configs/dupelifesteal", "config.yml");
        f = ConfigHelper.getFile("configs/dupelifesteal", "config.yml");
        configcrafting = ConfigHelper.getConfig("configs/dupelifesteal", "recipes.yml");
        fcrafting = ConfigHelper.getFile("configs/dupelifesteal", "recipes.yml");

        //Make sure defaults are set
        if(!configb.isSet("banned")) configb.set("banned", List.of("crackedhub:null"));

        List<String> ban = configb.getStringList("banned");
        ban.forEach(i -> {
            try {
                bannedKeys.add(new NamespacedKey(i.split(":")[0], i.split(":")[1]));
            } catch (IndexOutOfBoundsException ex) {
                Console.warn("(" + ex.getClass().getName() + ") Invalid config 'banneddupes.yml'! Error processing line: '" + i + "'.");
            }
        });
    }

    @EventHandler
    public void reload(PluginReloadEvent e) {
        configb = ConfigHelper.getConfig("configs/dupelifesteal", "banneddupes.yml");
        config = ConfigHelper.getConfig("configs/dupelifesteal", "config.yml");
        bannedKeys = new ArrayList<>();
        List<String> ban = config.getStringList("banned");
        ban.forEach(i -> {
            try {
                bannedKeys.add(new NamespacedKey(i.split(":")[0], i.split(":")[1]));
            } catch (IndexOutOfBoundsException ex) {
                Console.warn("(" + ex.getClass().getName() + ") Invalid config 'banneddupes.yml'! Error processing line: '" + i + "'.");
            }
        });
    }

    @EventHandler
    public void shutdown(PluginShutdownEvent e) throws IOException {
        Console.info("Shutting down Lifesteal...");
        ConfigHelper.saveConfig(config, f);
    }

    public static boolean isNotDupable(ItemStack is) {
        NamespacedKey key = (Items.isCustomItem(is) == null) ? is.getType().getKey() : new NamespacedKey(CrackedHub.getPlugin(), is.getItemMeta().getPersistentDataContainer().get(Items.Keys.INTERNAL_ID.getKey(), PersistentDataType.STRING));
        return bannedKeys.contains(key);
    }
}

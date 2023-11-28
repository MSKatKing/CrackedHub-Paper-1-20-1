package me.mskatking.crackedhub.modules.dupelifesteal;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.dupelifesteal.commands.DuperCraft;
import me.mskatking.crackedhub.modules.dupelifesteal.events.DupeLifestealListener;
import me.mskatking.crackedhub.util.ConfigHelper;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.events.PluginReloadEvent;
import me.mskatking.crackedhub.util.events.PluginShutdownEvent;
import me.mskatking.crackedhub.util.events.PluginStartupEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DupeLifesteal implements Listener {

    public static FileConfiguration config;
    public static File f;
    public static ArrayList<NamespacedKey> bannedKeys = new ArrayList<>();

    @EventHandler
    public void startup(PluginStartupEvent e) {
        Console.info("Initializing Lifesteal...");
        e.registerCommand(new DuperCraft());
        e.registerEvent(new DupeLifestealListener());
        config = ConfigHelper.getConfig("banneddupes.yml");
        f = ConfigHelper.getFile("banneddupes.yml");
        List<String> ban = config.getStringList("banned");
        ban.forEach(i -> {
            try {
                bannedKeys.add(new NamespacedKey(i.split(":")[0], i.split(":")[1]));
            } catch (IndexOutOfBoundsException ex) {
                Console.warn("(" + ex.getClass().getName() + ") Invalid config 'banneddupes.yml'! Error processing line: '" + i + "'.");
            }
        });
        ShapedRecipe duperTrooperT1 = new ShapedRecipe(Items.DUPER_TROOPER.getValue());
        duperTrooperT1.shape("***", "*%*", "***");
        duperTrooperT1.setIngredient('%', new ItemStack(Material.DIRT, 1));
        duperTrooperT1.setIngredient('*', new ItemStack(Material.AIR));
        CrackedHub.getPlugin().getServer().addRecipe(duperTrooperT1);
    }

    @EventHandler
    public void reload(PluginReloadEvent e) {
        config = ConfigHelper.getConfig("banneddupes.yml");
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

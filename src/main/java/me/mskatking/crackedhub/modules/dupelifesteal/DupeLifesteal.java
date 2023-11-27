package me.mskatking.crackedhub.modules.dupelifesteal;

import me.mskatking.crackedhub.modules.dupelifesteal.commands.Dupe;
import me.mskatking.crackedhub.modules.dupelifesteal.events.DupeLifestealListener;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.events.PluginShutdownEvent;
import me.mskatking.crackedhub.util.events.PluginStartupEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class DupeLifesteal implements Listener {

    @EventHandler
    public void startup(PluginStartupEvent e) {
        Console.info("Initializing Lifesteal...");
        e.registerCommand(new Dupe());
        e.registerEvent(new DupeLifestealListener());
    }

    @EventHandler
    public void shutdown(PluginShutdownEvent e) {
        Console.info("Shutting down Lifesteal...");
    }

    public static boolean isNotDupable(ItemStack is) {
        Items i = Items.isCustomItem(is);
        return !i.equals(null);
    }
}

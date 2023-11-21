package me.mskatking.crackedhub.modules.dupelifesteal;

import me.mskatking.crackedhub.modules.dupelifesteal.commands.Dupe;
import me.mskatking.crackedhub.modules.dupelifesteal.events.DupeLifestealListener;
import me.mskatking.crackedhub.util.events.PluginShutdownEvent;
import me.mskatking.crackedhub.util.events.PluginStartupEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DupeLifesteal implements Listener {

    @EventHandler
    public void startup(PluginStartupEvent e) {
        e.registerCommand(new Dupe());
        e.registerEvent(new DupeLifestealListener());
    }

    @EventHandler
    public void shutdown(PluginShutdownEvent e) {

    }
}

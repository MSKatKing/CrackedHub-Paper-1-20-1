package me.mskatking.crackedhub.modules.randomkit;

import me.mskatking.crackedhub.modules.randomkit.events.MainListener;
import me.mskatking.crackedhub.modules.randomkit.mechanics.Kit;
import me.mskatking.crackedhub.modules.randomkit.util.KitNotFoundException;
import me.mskatking.crackedhub.util.ConfigHelper;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.Errors;
import me.mskatking.crackedhub.util.events.PluginShutdownEvent;
import me.mskatking.crackedhub.util.events.PluginStartupEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RandomKit implements Listener {

    public ArrayList<Kit> kits = new ArrayList<>();

    private FileConfiguration config;
    private File f;

    @EventHandler
    public void startup(PluginStartupEvent e) {
        f = ConfigHelper.getFile("randomkits.yml");
        config = ConfigHelper.getConfig("randomkits.yml");

        if(e.getConfig().getBoolean("modules.randomkit")) {
            Console.info("Enabling random kit module...");
            e.registerCommand(new me.mskatking.crackedhub.modules.randomkit.commands.RandomKit());
            e.registerEvent(new MainListener());
            initializeFromConfig();

            Console.info("Random kit module enabled!");
        }
    }

    public boolean isFirstJoin(Player p) {
        if(!config.isSet("players." + p.getUniqueId())) {
            config.set("players." + p.getUniqueId(), true);
            return true;
        }
        return false;
    }


    @EventHandler
    public void shutdown(PluginShutdownEvent e) {
        Console.info("Shutting down random kit module...");
        for(Kit k : kits) {
            config.set(k.getID(), k.serialize());
        }
        try {
            config.save(f);
        } catch (Exception ex) {
            Console.error(Errors.RKIT_SAVE_ERROR.toString());
        }
        Console.info("Random kit module shut down!");
    }

    public boolean initializeFromConfig() {
        for(String s : config.getKeys(false)) {
            if(!s.equals("players")) kits.add(new Kit(s, config.getMapList(s + ".items")));
        }
        return true;
    }

    public Component getPrefix() {
        return Component.text("[", NamedTextColor.DARK_AQUA).append(Component.text("Kits", NamedTextColor.AQUA)).append(Component.text("] ", NamedTextColor.DARK_AQUA));
    }

    public Kit getKit(String name) throws KitNotFoundException {
        List<Kit> out = kits.stream().filter((i) -> i.getID().equals(name)).toList();
        if(out.isEmpty()) throw new KitNotFoundException(name);
        return out.get(0);
    }
}

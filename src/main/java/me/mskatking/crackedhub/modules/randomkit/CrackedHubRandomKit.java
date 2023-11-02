package me.mskatking.crackedhub.modules.randomkit;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.randomkit.commands.RandomKit;
import me.mskatking.crackedhub.modules.randomkit.mechanics.Kit;
import me.mskatking.crackedhub.modules.randomkit.mechanics.KitPlayer;
import me.mskatking.crackedhub.modules.randomkit.util.KitNotFoundException;
import me.mskatking.crackedhub.util.Console;
import me.mskatking.crackedhub.util.Errors;
import me.mskatking.crackedhub.util.Module;
import me.mskatking.crackedhub.util.PlayerNotFoundException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CrackedHubRandomKit implements Module {

    public ArrayList<KitPlayer> players = new ArrayList<>();
    public ArrayList<Kit> kits = new ArrayList<>();

    private final FileConfiguration config = new YamlConfiguration();
    private final File f;
    private boolean enabled = false;

    public CrackedHubRandomKit() {
        File path = new File(String.valueOf(CrackedHub.getPlugin().getDataFolder()));
        if(!path.exists()) {
            boolean ignored = path.mkdirs();
        }
        this.f = new File(path, "randomkits.yml");
        try {
            if(!f.exists()) {
                boolean ignored = f.createNewFile();
            }
            config.load(f);
        } catch (InvalidConfigurationException e) {
            Console.error("Kit config is not valid!");
        } catch (Exception e) {
            Console.error(e.getMessage());
        }
    }

    @Override
    public void enable() {
        Console.info("Enabling random kit module...");
        CrackedHub.getPlugin().getServer().getCommandMap().register("randomkit", new RandomKit());
        enabled = true;
        initializeFromConfig();
        Timer update = new Timer();
        update.schedule(new TimerTask() {
            @Override
            public void run() {
                if(enabled) players.forEach(KitPlayer::tick);
            }
        }, 1000);
        Console.info("Random kit module enabled!");
    }

    @Override
    public void disable() {
        Console.info("Disabling random kit module...");
        shutdown();
        enabled = false;
        Console.info("Random kit module disabled!");
    }

    @Override
    public void shutdown() {
        Console.info("Shutting down random kit module...");
        for(Kit k : kits) {
            config.set(k.getID(), k.serialize());
        }
        save();
        Console.info("Random kit module shut down!");
    }

    @Override
    public boolean save() {
        try {
            config.save(f);
            return true;
        } catch (Exception e) {
            Console.error(Errors.RKIT_SAVE_ERROR.toString());
            return false;
        }
    }

    @Override
    public boolean initializeFromConfig() {
        for(String s : config.getKeys(false)) {
            kits.add(new Kit(s, config.getMapList(s + ".items")));
        }
        return true;
    }

    @Override
    public Component getPrefix() {
        return Component.text("[", NamedTextColor.DARK_AQUA).append(Component.text("Kits", NamedTextColor.AQUA)).append(Component.text("] ", NamedTextColor.DARK_AQUA));
    }

    public KitPlayer getPlayer(Player p) throws PlayerNotFoundException {
        List<KitPlayer> out = players.stream().filter((i) -> i.p.getUniqueId().equals(p.getUniqueId())).toList();
        if(out.isEmpty()) throw new PlayerNotFoundException(p);
        return out.get(0);
    }

    public Kit getKit(String name) throws KitNotFoundException {
        List<Kit> out = kits.stream().filter((i) -> i.getID().equals(name)).toList();
        if(out.isEmpty()) throw new KitNotFoundException(name);
        return out.get(0);
    }
}

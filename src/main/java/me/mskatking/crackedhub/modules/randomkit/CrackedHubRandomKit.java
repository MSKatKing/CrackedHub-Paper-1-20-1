package me.mskatking.crackedhub.modules.randomkit;

import com.destroystokyo.paper.Namespaced;
import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.randomkit.commands.RandomKit;
import me.mskatking.crackedhub.modules.randomkit.events.MainListener;
import me.mskatking.crackedhub.modules.randomkit.mechanics.Kit;
import me.mskatking.crackedhub.modules.randomkit.util.KitNotFoundException;
import me.mskatking.crackedhub.util.*;
import me.mskatking.crackedhub.util.Module;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class CrackedHubRandomKit implements Module {

    public ArrayList<Kit> kits = new ArrayList<>();

    private final FileConfiguration config;
    private final File f;
    private boolean enabled = false;
    private Timer update = new Timer();

    public CrackedHubRandomKit() {
        this.f = ConfigHelper.getFile("randomkits.yml");
        this.config = ConfigHelper.getConfig("randomkits.yml");

        ItemStack s = new ItemStack(Material.GRAY_STAINED_GLASS);
        s.setAmount(13);
        ItemMeta m = s.getItemMeta();
        m.displayName(Component.text("test", NamedTextColor.RED).decorate(TextDecoration.BOLD));
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        m.addEnchant(Enchantment.BINDING_CURSE, 1, false);
        m.addEnchant(Enchantment.DIG_SPEED, 15, true);
        m.lore(List.of(Component.text("hehe", NamedTextColor.DARK_AQUA).decorate(TextDecoration.UNDERLINED)));
        m.setUnbreakable(true);
        m.setDestroyableKeys(List.of(Material.GRASS_BLOCK.getKey(), Material.DIAMOND_AXE.getKey()));
        m.setPlaceableKeys(List.of(Material.GRASS_BLOCK.getKey(), Material.AIR.getKey()));
        s.setItemMeta(m);
        this.config.set("test", new Kit(s).serialize());
    }

    @Override
    public void enable() {
        Console.info("Enabling random kit module...");
        CrackedHub.getPlugin().getServer().getCommandMap().register("crackedhub", new RandomKit());
        CrackedHub.getPlugin().getServer().getPluginManager().registerEvents(new MainListener(), CrackedHub.getPlugin());
        enabled = true;
        initializeFromConfig();
        update.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //nothing ticking for now
            }
        }, 0, 1000);
        Console.info("Random kit module enabled!");
    }

    public boolean isFirstJoin(Player p) {
        if(!config.isSet("players." + p.getUniqueId())) {
            config.set("players." + p.getUniqueId(), true);
            return true;
        }
        return false;
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
        update.cancel();
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
            if(!s.equals("players")) kits.add(new Kit(s, config.getMapList(s + ".items")));
        }
        return true;
    }

    @Override
    public Component getPrefix() {
        return Component.text("[", NamedTextColor.DARK_AQUA).append(Component.text("Kits", NamedTextColor.AQUA)).append(Component.text("] ", NamedTextColor.DARK_AQUA));
    }

    public Kit getKit(String name) throws KitNotFoundException {
        List<Kit> out = kits.stream().filter((i) -> i.getID().equals(name)).toList();
        if(out.isEmpty()) throw new KitNotFoundException(name);
        return out.get(0);
    }
}

package me.mskatking.crackedhub.modules.randomkit.events;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.randomkit.gui.KitGUI;
import me.mskatking.crackedhub.modules.randomkit.mechanics.KitPlayer;
import me.mskatking.crackedhub.modules.randomkit.util.KitNotFoundException;
import me.mskatking.crackedhub.util.PlayerNotFoundException;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class MainListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.isLeftClick()) {
            if(Objects.equals(e.getClickedInventory(), KitGUI.getInventory())) {
                switch (e.getSlot()) {
                    case 1: {
                        Objects.requireNonNull(e.getClickedInventory()).close();
                        e.setCancelled(true);
                        break;
                    }
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8: {
                        e.setCancelled(true);
                        break;
                    }
                    default: {
                        try {
                            CrackedHub.randomKitModule.getPlayer((Player) e.getWhoClicked()).giveKit(CrackedHub.randomKitModule.getKit(((TextComponent) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(e.getClickedInventory()).getItem(e.getSlot())).getItemMeta().displayName())).content()), false);
                        } catch (KitNotFoundException | PlayerNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        CrackedHub.randomKitModule.players.add(new KitPlayer(e.getPlayer()));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        try {
            CrackedHub.randomKitModule.players.remove(CrackedHub.randomKitModule.getPlayer(e.getPlayer()));
        } catch (PlayerNotFoundException ignored) {}
    }
}

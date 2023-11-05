package me.mskatking.crackedhub.modules.randomkit.events;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.randomkit.gui.KitGUI;
import me.mskatking.crackedhub.modules.randomkit.mechanics.KitPlayer;
import me.mskatking.crackedhub.modules.randomkit.util.KitNotFoundException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Objects;

public class MainListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (CrackedHub.randomKitModule.players.contains(e.getPlayer())) {
            e.getPlayer().sendMessage(CrackedHub.randomKitModule.getPrefix().append(Component.text("Giving you a random kit!", NamedTextColor.GREEN)));
            KitPlayer.giveRandomKit(e.getPlayer());
        }
    }

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
                            KitPlayer.giveKit((Player) e.getWhoClicked(), CrackedHub.randomKitModule.getKit(((TextComponent) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(e.getClickedInventory()).getItem(e.getSlot())).getItemMeta().displayName())).content()));
                        } catch (KitNotFoundException ex) {
                            e.getWhoClicked().sendMessage(CrackedHub.randomKitModule.getPrefix().append(Component.text("That kit doesn't exist!", NamedTextColor.RED)));
                        }
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if(CrackedHub.core.getMVWorldManager().getMVWorld("lobby").getCBWorld().equals(e.getFrom())) {
            e.getPlayer().sendMessage("");
            e.getPlayer().sendMessage(Component.text("Welcome to Random Kits!", NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
            e.getPlayer().sendMessage(Component.text("Every time you respawn, you will be given a new kit!", NamedTextColor.GRAY));
            e.getPlayer().sendMessage("");
        }
        if(CrackedHub.core.getMVWorldManager().getMVWorld("random_kit").getCBWorld().equals(e.getFrom()) ||
                CrackedHub.core.getMVWorldManager().getMVWorld("random_kit_end").getCBWorld().equals(e.getFrom()) ||
                CrackedHub.core.getMVWorldManager().getMVWorld("random_kit_nether").getCBWorld().equals(e.getFrom())) {
            CrackedHub.randomKitModule.players.remove(e.getPlayer());
        }
        if(CrackedHub.core.getMVWorldManager().getMVWorld("random_kit").getCBWorld().equals(e.getPlayer().getWorld()) ||
                CrackedHub.core.getMVWorldManager().getMVWorld("random_kit_end").getCBWorld().equals(e.getPlayer().getWorld()) ||
                CrackedHub.core.getMVWorldManager().getMVWorld("random_kit_nether").getCBWorld().equals(e.getPlayer().getWorld())) {
            if(CrackedHub.randomKitModule.isFirstJoin(e.getPlayer()))
                KitPlayer.giveRandomKit(e.getPlayer());
            CrackedHub.randomKitModule.players.add(e.getPlayer());
        }
    }
}

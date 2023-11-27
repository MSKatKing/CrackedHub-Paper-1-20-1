package me.mskatking.crackedhub.modules.dupelifesteal.events;

import org.bukkit.event.player.PlayerInteractEvent;

public interface InventoryClickFunction<T> {
    void run(PlayerInteractEvent e);
}

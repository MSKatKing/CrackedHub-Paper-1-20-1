package me.mskatking.crackedhub;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MainEvents implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        CrackedHub.core.teleportPlayer(null, e.getPlayer(), CrackedHub.core.getMVWorldManager().getMVWorld("lobby").getSpawnLocation());
    }
}

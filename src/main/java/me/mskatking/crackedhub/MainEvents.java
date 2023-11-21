package me.mskatking.crackedhub;

import me.mskatking.crackedhub.util.CrackedHubPlayer;
import me.mskatking.crackedhub.util.SQLProcessor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MainEvents implements Listener {

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        e.setFormat(((TextComponent) CrackedHubPlayer.findPlayer(e.getPlayer()).rank.getMessage(e.getPlayer().getName(), Component.text(e.getMessage()))).content());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        CrackedHub.core.teleportPlayer(null, e.getPlayer(), CrackedHub.core.getMVWorldManager().getMVWorld("lobby").getSpawnLocation());

        if(!SQLProcessor.contains(e.getPlayer().getUniqueId().toString())) {
            CrackedHub.onlinePlayers.add(new CrackedHubPlayer(e.getPlayer()));
            SQLProcessor.execute("INSERT INTO PLAYER_DATA VALUES ('" + e.getPlayer().getUniqueId() + "', 'member', 0, '" + e.getPlayer().getName() + "', false, 0);");
        } else {
            CrackedHub.onlinePlayers.add(new CrackedHubPlayer(e.getPlayer(), null));
        }

        CrackedHub.onlinePlayers.forEach(System.out::println);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        CrackedHub.onlinePlayers.remove(CrackedHubPlayer.findPlayer(e.getPlayer()));
    }
}

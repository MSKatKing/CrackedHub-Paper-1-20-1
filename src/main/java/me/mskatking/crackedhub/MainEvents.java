package me.mskatking.crackedhub;

import me.mskatking.crackedhub.util.SQLProcessor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainEvents implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ResultSet rs = SQLProcessor.getResult("SELECT * FROM PLAYER_DATA WHERE UUID = '" + e.getPlayer().getUniqueId() + "'");
        try {
            CrackedHub.ranksModule.players.put(e.getPlayer(), rs.getString("RANK"));
        } catch (SQLException ignored) {}
        CrackedHub.core.teleportPlayer(null, e.getPlayer(), CrackedHub.core.getMVWorldManager().getMVWorld("lobby").getSpawnLocation());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(!e.getPlayer().hasPlayedBefore()) {
            SQLProcessor.execute("INSERT INTO PLAYER_DATA VALUES ('" + e.getPlayer().getUniqueId() + "', 'member', 0, '" + e.getPlayer().getName() + "');");
        }
    }
}

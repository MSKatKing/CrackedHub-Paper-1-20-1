package me.mskatking.crackedhub;

import me.mskatking.crackedhub.util.CrackedHubPlayer;
import me.mskatking.crackedhub.util.Messages;
import me.mskatking.crackedhub.util.SQLProcessor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MainEvents implements Listener {

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        e.setFormat(PlainTextComponentSerializer.plainText().serialize(e.getPlayer().displayName().append(Component.text(": ")).append(Component.text(e.getMessage()))));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        CrackedHub.core.teleportPlayer(null, e.getPlayer(), CrackedHub.core.getMVWorldManager().getMVWorld("lobby").getSpawnLocation());

        e.getPlayer().sendMessage(Messages.getLevelUpMessage(1, Component.text("Nothing haha", NamedTextColor.GREEN)));

        if(!SQLProcessor.contains(e.getPlayer().getUniqueId().toString())) {
            CrackedHub.onlinePlayers.add(new CrackedHubPlayer(e.getPlayer(), false));
            SQLProcessor.execute("INSERT INTO PLAYER_DATA VALUES ('" + e.getPlayer().getUniqueId() + "', 'member', 0, '" + e.getPlayer().getName() + "', false, 0);");
        } else {
            CrackedHub.onlinePlayers.add(new CrackedHubPlayer(e.getPlayer(), true));
        }

        if(!SQLProcessor.contains(e.getPlayer().getUniqueId().toString(), "DUPE_LIFESTEAL_DATA")) {
            SQLProcessor.execute("INSERT INTO DUPE_LIFESTEAL_DATA VALUES ('" + e.getPlayer().getUniqueId() + "', 20);");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        CrackedHub.onlinePlayers.remove(CrackedHubPlayer.findPlayer(e.getPlayer()));
    }
}

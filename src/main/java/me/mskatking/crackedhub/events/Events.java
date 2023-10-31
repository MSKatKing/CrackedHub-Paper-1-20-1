package me.mskatking.crackedhub.events;

import io.papermc.paper.event.player.AbstractChatEvent;
import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.util.Console;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Events implements Listener {

    @EventHandler
    public void onChat(AbstractChatEvent e) {
        Console.info(CrackedHub.developers.toString());
        if(CrackedHub.developers.contains(e.getPlayer().getUniqueId().toString())) {
            e.message();
        }
    }
}

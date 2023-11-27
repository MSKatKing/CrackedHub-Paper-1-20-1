package me.mskatking.crackedhub.util.events;

import me.mskatking.crackedhub.util.CrackedHubPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class FindPlayerEvent extends Event {

    private final Player p;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public FindPlayerEvent(Player p) {
        this.p = p;
    }

    public Player getPlayer() {return p;}

    public CrackedHubPlayer returnPlayer(CrackedHubPlayer p) {
        return p;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}

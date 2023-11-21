package me.mskatking.crackedhub.util.events;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.mskatking.crackedhub.CrackedHub;
import net.kyori.adventure.text.Component;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PluginShutdownEvent extends Event {

    private final CrackedHub c;
    private final MultiverseCore core;

    public PluginShutdownEvent(CrackedHub c, MultiverseCore core) {
        this.c = c;
        this.core = core;
    }

    public void sendMessage(Component c) {
        this.c.getServer().getConsoleSender().sendMessage(c);
    }

    public CrackedHub getPlugin() {
        return c;
    }

    public MultiverseCore getMVCore() {
        return core;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }
}

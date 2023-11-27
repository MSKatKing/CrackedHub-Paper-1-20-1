package me.mskatking.crackedhub.util.events;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.mskatking.crackedhub.CrackedHub;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class MinuteTimerEvent extends Event {
    private final CrackedHub c;
    private final MultiverseCore core;
    private final FileConfiguration config;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public MinuteTimerEvent(CrackedHub c, MultiverseCore core, FileConfiguration config) {
        this.c = c;
        this.core = core;
        this.config = config;
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

    public FileConfiguration getConfig() {
        return config;
    }

    public void registerCommand(Command c) {
        this.c.getServer().getCommandMap().register("crackedhub", c);
    }

    public void registerEvent(Listener l) {
        this.c.getServer().getPluginManager().registerEvents(l, c);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}

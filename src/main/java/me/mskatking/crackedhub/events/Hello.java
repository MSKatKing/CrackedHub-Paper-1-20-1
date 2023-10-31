package me.mskatking.crackedhub.events;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Hello extends Command {

    public Hello(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage("Hi!");
        return true;
    }
}

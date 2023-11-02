package me.mskatking.crackedhub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Update extends Command {
    public Update() {
        super("update", "marks the server to shutdown", "/update", List.of());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return true;
    }
}

package me.mskatking.crackedhub.commands;

import me.mskatking.crackedhub.util.SQLProcessor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BackupSQL extends Command {

    public BackupSQL() {
        super("backup-sql", "ONLY FOR CONSOLE", "/backup-sql [location]", List.of());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender instanceof Player p) {
            p.sendMessage(Component.text("Sorry, but only the console can execute this command!", NamedTextColor.RED));
        } else {
            if(args.length == 1) {
                SQLProcessor.saveBackup(args[0]);
            } else {
                sender.sendMessage(Component.text("Incorrect amount of arguments. If you don't know what this means, please don't use this command."));
            }
        }
        return true;
    }
}

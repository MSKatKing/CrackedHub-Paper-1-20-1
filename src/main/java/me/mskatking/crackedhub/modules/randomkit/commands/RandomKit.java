package me.mskatking.crackedhub.modules.randomkit.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RandomKit extends Command {
    protected RandomKit() {
        super("randomkit", "Gives the player a random kit.", "/randomkit OR /randomkit <player> (requires admin)", List.of("rk", "randkit", "rkit", "randomk"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            Player p = (Player) sender;
        } else {
            if(args.length != 1) {
                sender.sendMessage(Component.text("Error! I don't know what the arguments '" + String.join(", ", args) + "' mean!", NamedTextColor.RED));
            }
        }
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return super.tabComplete(sender, alias, args);
    }
}

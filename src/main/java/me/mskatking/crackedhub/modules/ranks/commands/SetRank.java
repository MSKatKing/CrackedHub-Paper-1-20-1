package me.mskatking.crackedhub.modules.ranks.commands;

import me.mskatking.crackedhub.util.SQLProcessor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetRank extends Command {

    List<String> ranks = Arrays.asList("member", "mod", "developer", "manager", "admin", "owner");

    public SetRank() {
        super("setrank", "set the given player's rank", "/setrank <player> <rank>", List.of("sr"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if(strings.length != 2) {
            commandSender.sendMessage(Component.text("I don't know what the arguments '" + String.join(", ", strings) + "' mean!", NamedTextColor.RED));
            return true;
        }
        if(!commandSender.hasPermission("crackedhub.admin.setrank") && !commandSender.isOp()) {
            commandSender.sendMessage(Component.text("You don't have permission to execute this command!", NamedTextColor.RED));
            return true;
        }
        OfflinePlayer p = Bukkit.getOfflinePlayer(strings[0]);

        if(ranks.contains(strings[1])) {
            SQLProcessor.execute("UPDATE PLAYER_DATA SET RANK = '" + strings[1] + "' WHERE UUID = '" + p.getUniqueId() + "';");
            commandSender.sendMessage(Component.text("Successfully gave " + p.getName() + " the rank " + strings[1], NamedTextColor.GREEN));
            SQLProcessor.printTable("PLAYER_DATA");
            return true;
        } else {
            commandSender.sendMessage(Component.text("The rank " + strings[1] + " does not exist!", NamedTextColor.RED));
        }

        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args) {
            ArrayList<String> players = new ArrayList<>();
            Bukkit.getServer().getOnlinePlayers().forEach((i) -> players.add(i.getName()));


            List<String> result = new ArrayList<>();
            if(args.length == 1) {
                for(String a : players) {
                    if(a.toLowerCase().startsWith(args[0].toLowerCase()))
                        result.add(a);
                }
                return result;
            } else if (args.length == 2) {
                for(String a : ranks) {
                    if(a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
        return List.of("");
    }
}

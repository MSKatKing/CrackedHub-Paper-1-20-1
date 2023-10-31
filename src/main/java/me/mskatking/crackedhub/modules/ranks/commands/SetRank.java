package me.mskatking.crackedhub.modules.ranks.commands;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.util.Console;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
            commandSender.sendMessage(ChatColor.RED + "I don't know what the arguments '" + String.join(", ", strings) + "' mean!");
            return true;
        }
        if(!commandSender.hasPermission("crackedhub.admin.setrank") && !commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to execute this command!");
            return true;
        }
        OfflinePlayer p = Bukkit.getOfflinePlayer(strings[0]);

        if(ranks.contains(strings[1])) {
            if(!CrackedHub.developers.contains(p.getUniqueId().toString())) CrackedHub.developers.add(p.getUniqueId().toString());
            CrackedHub.config.set(strings[1], CrackedHub.developers);
            try {
                CrackedHub.config.save(CrackedHub.f);
            } catch (Exception e) {
                Console.error("Unable to save ranks!");
                commandSender.sendMessage(ChatColor.RED + "Unable to give " + p.getName() + " the rank " + strings[1]);
                return true;
            }
            commandSender.sendMessage(ChatColor.GREEN + "Successfully gave " + p.getName() + " the rank " + strings[1]);
            return true;
        } else {
            commandSender.sendMessage(ChatColor.RED + "The rank " + strings[1] + " does not exist!");
        }

        return true;
    }

    @Override
    public @Nullable List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args) {
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
        return null;
    }
}

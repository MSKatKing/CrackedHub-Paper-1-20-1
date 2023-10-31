package me.mskatking.crackedhub.modules.ranks.commands;

import me.mskatking.crackedhub.modules.ranks.gui.RankGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Ranks extends Command {
    public Ranks() {
        super("ranks", "Opens the rank editing menu", "/ranks", List.of("r"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender.hasPermission("crackedhub.admin.ranks") || sender.isOp()) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                p.sendMessage(ChatColor.GREEN + "Opening rank menu...");
                p.openInventory(RankGUI.getInventory());
            } else {
                sender.sendMessage(ChatColor.RED + "Only in-game players can use this command! (Console access coming soon)");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do this command!");
        }
        return false;
    }
}

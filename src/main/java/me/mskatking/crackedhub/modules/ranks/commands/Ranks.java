package me.mskatking.crackedhub.modules.ranks.commands;

import me.mskatking.crackedhub.modules.ranks.gui.RankGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
            if(sender instanceof Player p) {
                p.sendMessage(Component.text("Opening rank menu...", NamedTextColor.GREEN));
                p.openInventory(RankGUI.getInventory());
            } else {
                sender.sendMessage(Component.text("Only in-game players can use this command! (Console access coming soon)", NamedTextColor.RED));
            }
        } else {
            sender.sendMessage(Component.text("You don't have permission to do this command!", NamedTextColor.RED));
        }
        return false;
    }
}

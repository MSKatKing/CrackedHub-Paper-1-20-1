package me.mskatking.crackedhub.commands;

import me.mskatking.crackedhub.CrackedHub;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Lobby extends Command {
    public Lobby() {
        super("lobby", "This command teleports the player back to the lobby!", "/lobby", List.of("l", "hub"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender instanceof Player p) {
            p.sendMessage(Component.text("Sending you to the lobby...", NamedTextColor.GRAY));
            CrackedHub.core.teleportPlayer(null, p, CrackedHub.core.getMVWorldManager().getMVWorld("lobby").getSpawnLocation());
        } else {
            sender.sendMessage(Component.text("Only players can do this command!", NamedTextColor.RED));
        }
        return true;
    }
}

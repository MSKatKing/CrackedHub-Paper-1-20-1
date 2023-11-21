package me.mskatking.crackedhub.commands;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.util.CustomErrors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Teleport extends Command {
    public Teleport() {
        super("teleport", "For use by the console only to send a player to a specific gamemode.", "", List.of());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(!(sender instanceof Player) && args.length == 2) {
            Player p = Bukkit.getPlayer(args[0]);
            assert p != null;
            p.sendMessage(Component.text("Connecting to " + args[1] + "...", NamedTextColor.GRAY));
            MultiverseWorld world;
            switch (args[1]) {
                case "skyblock" -> {
                    if(CrackedHub.config.getBoolean("skyblock.closed")) {
                        p.sendMessage(Component.text("Oops! SkyBlock is currently closed due to: " + CrackedHub.config.get("skyblock.closedMessage"), NamedTextColor.RED));
                        return true;
                    }
                    else world = CrackedHub.core.getMVWorldManager().getMVWorld("skyblock-hub");
                }
                case "randomkit" -> {
                    if(CrackedHub.config.getBoolean("randomkit.closed")) {
                        p.sendMessage(Component.text("Oops! Random Kit is currently closed due to: " + CrackedHub.config.get("randomkit.closedMessage"), NamedTextColor.RED));
                        return true;
                    }
                    else world = CrackedHub.core.getMVWorldManager().getMVWorld("random_kit");
                }
                case "box" -> {
                    if(CrackedHub.config.getBoolean("box.closed")) {
                        p.sendMessage(Component.text("Oops! Boxes is currently closed due to: " + CrackedHub.config.get("box.closedMessage"), NamedTextColor.RED));
                        return true;
                    }
                    else world = CrackedHub.core.getMVWorldManager().getMVWorld("boxes");
                }
                default -> {
                    p.sendMessage(Component.text("Oops! Couldn't find minigame '" + args[1] + "'! ", NamedTextColor.RED).append(CustomErrors.CONTACT_DEVELOPERS));
                    return true;
                }
            }
            p.sendMessage(Component.text("Sending to " + world.getName(), NamedTextColor.GRAY));
            CrackedHub.core.teleportPlayer(sender, p, world.getSpawnLocation());
        } else {
            sender.sendMessage(Component.text("You don't have permission to do this command!", NamedTextColor.RED));
        }
        return true;
    }
}

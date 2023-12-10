package me.mskatking.crackedhub.commands;

import me.mskatking.crackedhub.util.SQLProcessor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Unlink extends Command {
    public Unlink() {
        super("unlink", "Used for unlinking Minecraft and Discord accounts", "/unlink", List.of());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if(SQLProcessor.contains(p.getUniqueId().toString(), "UUID", SQLProcessor.Tables.DISCORD_LINKED.toString())) {
                SQLProcessor.execute("DELETE FROM DISCORD_LINKED WHERE UUID = '" + p.getUniqueId() + "';");
                p.sendMessage(Component.text("Successfully unlinked your accounts!", NamedTextColor.GREEN));
            } else {
                p.sendMessage(Component.text("Your account isn't linked!", NamedTextColor.RED));
            }
        } else {
            sender.sendMessage(Component.text("Only players can execute this command!"));
        }
        return true;
    }
}

package me.mskatking.crackedhub.modules.dupelifesteal.commands;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.util.WorldHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Dupe extends Command {
    public Dupe() {
        super("dupe", "Dupes the current item you are holding! Only works on dupe lifesteal!", "/dupe", List.of());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if(WorldHelper.isInWorlds(p, "dupelifesteal", "dupelifesteal_nether", "dupelifesteal_end")) {
                p.getInventory().addItem(p.getInventory().getItemInMainHand());
            } else {
                p.sendMessage(Component.text("You must be playing on dupe lifesteal to use this command!", NamedTextColor.RED));
            }
        } else {
            sender.sendMessage(Component.text("You must be a player to run this command!", NamedTextColor.RED));
        }
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
        return List.of();
    }
}

package me.mskatking.crackedhub.modules.dupelifesteal.commands;

import me.mskatking.crackedhub.util.CraftingHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DuperCraft extends Command {
    public DuperCraft() {
        super("dupercraft", "Dupes the current item you are holding! Only works on dupe lifesteal!", "/dupercraft", List.of());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender instanceof Player p) {
            p.sendMessage(Component.text("[Dupe Lifesteal] Opening duper crafting menu...", NamedTextColor.GREEN));
            p.openInventory(CraftingHelper.getDuperInventory());
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

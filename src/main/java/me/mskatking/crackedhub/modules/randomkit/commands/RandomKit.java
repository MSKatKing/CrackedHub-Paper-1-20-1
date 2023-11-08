package me.mskatking.crackedhub.modules.randomkit.commands;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.randomkit.gui.KitGUI;
import me.mskatking.crackedhub.modules.randomkit.mechanics.Kit;
import me.mskatking.crackedhub.modules.randomkit.util.KitNotFoundException;
import me.mskatking.crackedhub.util.PlayerNotFoundException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RandomKit extends Command {
    public RandomKit() {
        super("randomkit", "Gives the player a random kit.", "/randomkit OR /randomkit <player> <kitname> (requires console)", List.of("rk", "randkit", "rkit", "randomk"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender instanceof Player p && sender.hasPermission("crackedhub.admin.kits")) {
            p.openInventory(KitGUI.getInventory(p));
        }
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if(!(sender instanceof Player) || sender.isOp()) {
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
                for(String a : CrackedHub.randomKitModule.kits.stream().map(Kit::getID).toList()) {
                    if(a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
        }
        return List.of("");
    }
}

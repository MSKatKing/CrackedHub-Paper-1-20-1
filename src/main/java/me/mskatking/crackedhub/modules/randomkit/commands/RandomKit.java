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
        if(sender instanceof Player p) {
            if (args.length == 1 && args[0].equals("menu")) p.openInventory(KitGUI.getInventory());
            else {
                try {
                    CrackedHub.randomKitModule.getPlayer(p).giveRandomKit(false);
                } catch (PlayerNotFoundException e) {
                    sender.sendMessage(CrackedHub.randomKitModule.getPrefix().append(Component.text("PlayerNotFoundException@" + e.getCause() + " | " + e.getMessage(), NamedTextColor.RED)));
                }
            }
        } else { //TODO: add functionality to create, edit, delete kits thru this command
            if(args.length != 2) {
                sender.sendMessage(CrackedHub.randomKitModule.getPrefix().append(Component.text("Error! I don't know what the arguments '" + String.join(", ", args) + "' mean!", NamedTextColor.RED)));
            } else {
                try {
                    CrackedHub.randomKitModule.getPlayer(Bukkit.getPlayer(args[0])).giveKit(CrackedHub.randomKitModule.getKit(args[1]), true);
                    sender.sendMessage(CrackedHub.randomKitModule.getPrefix().append(Component.text("Successfully gave " + args[0] + " the kit " + args[1] + "!", NamedTextColor.GREEN)));
                } catch (PlayerNotFoundException e) {
                    sender.sendMessage(CrackedHub.randomKitModule.getPrefix().append(Component.text("PlayerNotFoundException@" + e.getCause() + " | " + e.getMessage(), NamedTextColor.RED)));
                } catch (KitNotFoundException e) {
                    sender.sendMessage(CrackedHub.randomKitModule.getPrefix().append(Component.text("KitNotFoundException@" + e.getCause() + " | " + e.getMessage(), NamedTextColor.RED)));
                }
            }
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

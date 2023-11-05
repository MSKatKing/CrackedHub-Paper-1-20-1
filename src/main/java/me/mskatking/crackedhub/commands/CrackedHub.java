package me.mskatking.crackedhub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CrackedHub extends Command {
    public CrackedHub() {
        super("crackedhub", "Central command for CrackedHub plugin.", "/crackedhub <reload|enable|disable>", List.of("ch"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender.hasPermission("crackedhub.admin.crackedhub")) {
            switch (args[0]) {
                case "enable", "disable": {
                    break;
                }
                case "save": {
                    me.mskatking.crackedhub.CrackedHub.randomKitModule.save();
                    me.mskatking.crackedhub.CrackedHub.boxModule.save();
                    me.mskatking.crackedhub.CrackedHub.ranksModule.save();
                }
            }
        }
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if(sender.isOp()) {
            String[] modules = {"random_kit", "box", "admin", "ranks"};


            List<String> result = new ArrayList<>();
            if(args.length == 1) {
                for(String a : List.of("enable", "disable", "save")) {
                    if(a.toLowerCase().startsWith(args[0].toLowerCase()))
                        result.add(a);
                }
            } else if (args.length == 2 && List.of("enable", "disable").contains(args[0].toLowerCase())) {
                for(String a : modules) {
                    if(a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
        }
        return List.of("");
    }
}

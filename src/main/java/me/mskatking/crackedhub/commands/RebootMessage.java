package me.mskatking.crackedhub.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RebootMessage extends Command {
    public RebootMessage() {
        super("rebootMessage", "console only", "", List.of());
    }

    Timer t = new Timer();
    int s = 120;

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(!(sender instanceof Player p)) {
            s = 120;
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitlePart(TitlePart.TITLE, Component.text("!! SERVER REBOOT !!", NamedTextColor.RED));
                p.sendTitlePart(TitlePart.SUBTITLE, Component.text("Rebooting in 2 Minutes", NamedTextColor.RED));
                p.sendMessage(Component.newline().append(Component.text("Server is rebooting for weekly refresh in 2 minutes!!", NamedTextColor.RED).appendNewline().append(Component.text("Please try to rejoin up to 2 minutes after the server shuts down.", NamedTextColor.RED)).appendNewline()));
            }
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendActionBar(Component.text("Rebooting in " + s / 60 + "m " + s % 60 + "s.", NamedTextColor.RED));
                    }
                    s--;
                    if(s <= 0) {
                        cancel();
                    }
                }
            }, 0, 1000);
        } else {
            p.sendMessage(Component.text("Only the console can execute this command!", NamedTextColor.RED));
        }
        return true;
    }
}

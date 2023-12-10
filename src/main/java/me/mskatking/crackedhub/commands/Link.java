package me.mskatking.crackedhub.commands;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.DiscordListener;
import me.mskatking.crackedhub.util.SQLProcessor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Link extends Command {
    public Link() {
        super("link", "Tst", "/link <id>", List.of());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if(args.length == 1) {
                if(DiscordListener.LinkRequest.containsRequest(args[0]).isPresent()) {
                    SQLProcessor.execute("INSERT INTO DISCORD_LINKED VALUES ('" + p.getUniqueId() + "', '" + DiscordListener.LinkRequest.containsRequest(args[0]).get().initiatorU.getId() + "');");
                    DiscordListener.requests.remove(DiscordListener.LinkRequest.containsRequest(args[0]).get());
                    p.sendMessage(Component.text("Success!", NamedTextColor.GREEN).decorate(TextDecoration.BOLD).append(Component.text(" Linked your Minecraft and Discord accounts!", NamedTextColor.GREEN).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)));
                } else {
                    p.sendMessage(Component.text("A link request with the name '" + args[0] + "' could not be found!", NamedTextColor.RED));
                }
            } else if(!SQLProcessor.contains(p.getUniqueId().toString(), "UUID", SQLProcessor.Tables.DISCORD_LINKED.toString())) {
                String id = CrackedHub.randomString(5);
                DiscordListener.requests.add(new DiscordListener.LinkRequest(p, id));
                sender.sendMessage(Component.text("Created request! Please enter '/link ", NamedTextColor.GREEN).append(Component.text(id, NamedTextColor.GREEN).decorate(TextDecoration.UNDERLINED).hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("Click to copy id!", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, id))).append(Component.text("' in the Discord server to link your account!", NamedTextColor.GREEN)));
            } else {
                p.sendMessage(Component.text("You have already linked your accounts! If you believe this is a mistake, please contact an admin.", NamedTextColor.RED));
            }
        } else {
            sender.sendMessage(Component.text("Only players can execute this command!"));
        }
        return true;
    }
}

package me.mskatking.crackedhub.modules.ranks.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class Rank {

    public Component prefix;
    public NamedTextColor chatColor;
    public int priority;

    public Rank(Component prefix, NamedTextColor chatColor, int priority) {
        this.prefix = prefix;
        this.chatColor = chatColor;
    }

    public Component getMessage(String name, Component message) {
        return prefix.append(Component.text(" " + name + ": ", chatColor).append(message.color(chatColor)));
    }

    public enum Ranks {
        MEMBER(new Rank(Component.text("Member", NamedTextColor.GRAY).decorate(TextDecoration.BOLD), NamedTextColor.GRAY, 0)),
        DEVELOPER(new Rank(Component.text("Developer", NamedTextColor.RED).decorate(TextDecoration.BOLD), NamedTextColor.WHITE, 999)),
        OWNER(new Rank(Component.text("Owner ", NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD), NamedTextColor.WHITE, 100000));

        private final Rank value;
        Ranks(Rank rank) {value = rank;}
        public Rank value() {
            return value;
        }
    }
}

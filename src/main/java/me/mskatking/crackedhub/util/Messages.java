package me.mskatking.crackedhub.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class Messages {

    public static Component getLevelUpMessage(int level, Component... rewards) {
        Component out = Component.text("").appendNewline();
        out = out.append(Component.text("ll", NamedTextColor.AQUA).decorate(TextDecoration.OBFUSCATED));
        out = out.append(Component.text(" Cracked", NamedTextColor.GRAY));
        out = out.append(Component.text("Hub ", NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
        out = out.append(Component.text("Level Up! ", NamedTextColor.GREEN));
        out = out.append(Component.text("ll", NamedTextColor.AQUA).decorate(TextDecoration.OBFUSCATED));
        out = out.appendNewline().appendNewline().appendSpace().appendSpace().appendSpace().appendSpace();
        out = out.append(Component.text("Level " + (level - 1) + " --> ", NamedTextColor.DARK_GRAY));
        out = out.append(Component.text("Level " + level, NamedTextColor.GREEN)).appendNewline();
        out = out.appendSpace().appendSpace().appendSpace().appendSpace().append(Component.text("Rewards: ", NamedTextColor.AQUA)).appendNewline();
        for(Component c : rewards) {
            out = out.appendSpace().appendSpace().appendSpace().appendSpace().appendSpace().appendSpace().appendSpace().appendSpace().append(Component.text("- ", NamedTextColor.GRAY).append(c)).appendNewline();
        }
        return out;
    }
}

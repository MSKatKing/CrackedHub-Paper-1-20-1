package me.mskatking.crackedhub.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class CustomErrors {

    public static final Component CHAT_SEND_FAILURE = Component.text("An error occurred sending the chat message! Please report this to the ", NamedTextColor.RED).append(Component.text("developer team", NamedTextColor.RED).decorate(TextDecoration.UNDERLINED).hoverEvent(HoverEvent.showText(Component.text("MSKatKing", NamedTextColor.GRAY)))).append(Component.text(" ASAP!", NamedTextColor.RED));
    public static final Component CONTACT_DEVELOPERS = Component.text("Please contact the ", NamedTextColor.RED).append(Component.text("development team", NamedTextColor.RED).decorate(TextDecoration.UNDERLINED).hoverEvent(HoverEvent.showText(Component.text("MSKatKing", NamedTextColor.GRAY)))).append(Component.text(" ASAP!"));
}

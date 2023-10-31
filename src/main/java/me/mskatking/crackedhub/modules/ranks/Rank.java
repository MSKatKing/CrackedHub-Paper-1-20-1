package me.mskatking.crackedhub.modules.ranks;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Rank implements ConfigurationSerializable {

    /** Instance Variables |
     * id: Holds the rank's unique ID given to it at creation.
     * prefix: Holds the prefix of the rank. IE: "developer".
     * colors: Holds the colors for the prefix of the rank.
     * nameColor: Holds the name color of the player who has the rank.
     * messageColor: Holds the name color of the message sent in chat.*/
    public UUID id;
    public String prefix;
    public String colors;
    public NamedTextColor nameColor;
    public NamedTextColor messageColor;

    public Rank(String prefix, String colors) {
        this.id = UUID.randomUUID();
        this.prefix = prefix;
        this.colors = colors;
        this.nameColor = NamedTextColor.WHITE;
        this.messageColor = NamedTextColor.WHITE;
    }

    public Rank(String prefix, String colors, NamedTextColor nameColor, NamedTextColor messageColor) {
        this.id = UUID.randomUUID();
        this.prefix = prefix;
        this.colors = colors;
        this.nameColor = nameColor;
        this.messageColor = messageColor;
    }

    public Rank(Map<String, String> in, String id) {
        this.id = UUID.randomUUID();
        this.prefix = in.get(id + ".prefix");
        this.colors = in.get(id + ".colors");
        this.nameColor = in.get(id + ".nameColor");
        this.messageColor = in.get(id + "messageColor");
    }

    /** Returns the modified chat message according to the rank's cosmetics.*/
    public String applyRank(Player p, String message) {
        return colors + prefix + " " + nameColor + p.getName() + messageColor + ": " + message;
    }
    /**
     * Returns the serialized rank ready to be saved to a file.
     */
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put(id + ".prefix", prefix);
        result.put(id + ".colors", colors);
        result.put(id + ".nameColor", nameColor);
        result.put(id + ".messageColor", messageColor);
        return result;
    }

}

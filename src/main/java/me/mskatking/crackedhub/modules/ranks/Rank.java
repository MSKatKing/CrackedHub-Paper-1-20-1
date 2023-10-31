package me.mskatking.crackedhub.modules.ranks;

import me.mskatking.crackedhub.util.GUIObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Rank implements ConfigurationSerializable, GUIObject {

    /** Instance Variables |
     * id: Holds the rank's unique ID given to it at creation.
     * prefix: Holds the prefix of the rank. IE: "developer".
     * colors: Holds the colors for the prefix of the rank.
     * nameColor: Holds the name color of the player who has the rank.
     * messageColor: Holds the name color of the message sent in chat.*/
    public UUID id;
    public String prefix;
    public NamedTextColor color;
    public NamedTextColor nameColor;
    public NamedTextColor messageColor;

    public Rank(String prefix, NamedTextColor color) {
        this.id = UUID.randomUUID();
        this.prefix = prefix;
        this.color = color;
        this.nameColor = NamedTextColor.GRAY;
        this.messageColor = NamedTextColor.GRAY;
    }

    public Rank(String prefix, NamedTextColor color, NamedTextColor nameColor, NamedTextColor messageColor) {
        this.id = UUID.randomUUID();
        this.prefix = prefix;
        this.color = color;
        this.nameColor = nameColor;
        this.messageColor = messageColor;
    }

    public Rank(Map<String, String> in, String id) {
        this.id = UUID.randomUUID();
        this.prefix = in.get(id + ".prefix");
        this.color = NamedTextColor.namedColor(Integer.parseInt(in.get(id + ".color")));
        this.nameColor = NamedTextColor.namedColor(Integer.parseInt(in.get(id + ".nameColor")));
        this.messageColor = NamedTextColor.namedColor(Integer.parseInt(in.get(id + ".messageColor")));
    }

    /** Returns the modified chat message according to the rank's cosmetics.*/
    public TextComponent applyRank(Player p, String message) {
        return Component.text(prefix + " ", color).append(Component.text(p.getName(), nameColor)).append(Component.text(": " + message, messageColor));
    }


    /**
     * Returns the serialized rank ready to be saved to a file.
     */
    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put(id + ".prefix", prefix);
        result.put(id + ".colors", color.value());
        result.put(id + ".nameColor", nameColor.value());
        result.put(id + ".messageColor", messageColor.value());
        return result;
    }

    @Override
    public ItemStack getRepresentingItem() {
        ItemStack item = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(prefix, color));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.lore(List.of(Component.text("", NamedTextColor.GRAY), Component.text("Click to edit this rank!", NamedTextColor.GRAY)));
        item.setItemMeta(meta);
        return item;
    }
}

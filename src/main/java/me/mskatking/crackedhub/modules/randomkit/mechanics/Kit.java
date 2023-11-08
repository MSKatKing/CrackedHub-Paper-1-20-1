package me.mskatking.crackedhub.modules.randomkit.mechanics;

import me.mskatking.crackedhub.util.GUIObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Kit implements ConfigurationSerializable, GUIObject {

    private final String id;
    public final ArrayList<ItemStack> items = new ArrayList<>();

    public Kit(ItemStack... items) {
        this.id = UUID.randomUUID().toString();
        this.items.addAll(List.of(items));
    }

    public Kit(Component name, ArrayList<ItemStack> items) {
        this.id = ((TextComponent) name).content();
        this.items.addAll(items);
    }

    public Kit(String id, List<Map<?, ?>> in) {
        this.id = id;
        for(Map<?, ?> i : in) {
            items.add(ItemStack.deserialize((Map<String, Object>) i));
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> serItem = new ArrayList<>();
        for(ItemStack i : items) {
            serItem.add(i.serialize());
        }
        res.put("items", serItem);
        return res;
    }

    public String getID() {
        return this.id;
    }

    @Override
    public ItemStack getRepresentingItem() {
        ItemStack out = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = out.getItemMeta();
        meta.displayName(Component.text(id, NamedTextColor.AQUA).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        items.forEach((i) -> lore.add(i.displayName().append(Component.text("x" + i.getAmount(), NamedTextColor.DARK_GRAY)).decoration(TextDecoration.ITALIC, false)));
        meta.lore(lore);
        out.setItemMeta(meta);
        return out;
    }
}

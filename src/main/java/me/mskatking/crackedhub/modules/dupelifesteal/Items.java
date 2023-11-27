package me.mskatking.crackedhub.modules.dupelifesteal;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.dupelifesteal.events.InventoryClickFunction;
import me.mskatking.crackedhub.util.InventoryHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public enum Items {
    DUPER_TROOPER(duperTrooperItem(), (e) -> {
        Inventory i = InventoryHelper.emptyInventory(3, Component.text("Dupe Menu T1", NamedTextColor.GOLD));
        i.setItem(12, new ItemStack(Material.AIR));
        ItemStack item = new ItemStack(Material.ANVIL);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Keys.UUID_REFERECNE.getKey(), PersistentDataType.STRING, e.getItem().getItemMeta().getPersistentDataContainer().get(Keys.STACKABLE_UUID.getKey(), PersistentDataType.STRING));
        meta.displayName(Component.text("Dupe", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        //meta.lore(List.of(Component.text("Place the item you want to dupe in the slot to the left,", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC),
        //        Component.text("then click me to dupe it!", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)));
        item.setItemMeta(meta);
        i.setItem(13, item);
        i.setItem(14, new ItemStack(Material.AIR));
        e.getPlayer().openInventory(i);
    });

    private ItemStack value;
    private InventoryClickFunction functionality;

    Items(ItemStack value, InventoryClickFunction functionality) {
        this.value = value;
        this.functionality = functionality;
    }

    public ItemStack getValue() {
        return this.value;
    }

    public void executeFunctionality(PlayerInteractEvent e) {
        functionality.run(e);
    }

    private static ItemStack duperTrooperItem() {
        ItemStack out = new ItemStack(Material.DIAMOND);
        out.lore(List.of(Component.text("Right click me to open the dupe menu!", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE),
                Component.text("You can only dupe 5 more times!", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)));
        ItemMeta meta = out.getItemMeta();
        meta.displayName(Component.text("Duper Trooper [T1]", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        meta.getPersistentDataContainer().set(Keys.USES_REMAINING.getKey(), PersistentDataType.INTEGER, 5);
        meta.getPersistentDataContainer().set(Keys.INTERNAL_ID.getKey(), PersistentDataType.STRING, "duper_trooper_tier1");
        meta.getPersistentDataContainer().set(Keys.STACKABLE_UUID.getKey(), PersistentDataType.STRING, UUID.randomUUID().toString());
        out.setItemMeta(meta);
        return out;
    }

    public static Items isCustomItem(ItemStack is) {
        assert is.getItemMeta().getPersistentDataContainer().has(Keys.INTERNAL_ID.getKey());
        List<Items> items = EnumSet.allOf(Items.class).stream().filter((i) -> {
            return i.getValue().getItemMeta().getPersistentDataContainer().get(Keys.INTERNAL_ID.getKey(), PersistentDataType.STRING).equals(is.getItemMeta().getPersistentDataContainer().get(Keys.INTERNAL_ID.getKey(), PersistentDataType.STRING));
        }).toList();
        if(items.size() > 0) {
            return items.get(0);
        } else {
            return null;
        }
    }

    public enum Keys {
        USES_REMAINING("uses-remaining"),
        INTERNAL_ID("internal-id"),
        STACKABLE_UUID("stackable_uuid"),
        UUID_REFERECNE("uuid_reference");

        private NamespacedKey key;
        Keys(String key) {
            this.key = new NamespacedKey(CrackedHub.getPlugin(), key);
        }

        public NamespacedKey getKey() {
            return key;
        }
    }
}

package me.mskatking.crackedhub.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InventoryHelper {

    public static Inventory emptyInventory(int rows, Component name) {
        Inventory result = Bukkit.createInventory(null, rows * 9, name);
        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.displayName(Component.text("").decoration(TextDecoration.ITALIC, false));
        empty.setItemMeta(emptyMeta);
        for(int i = 0; i < 27; i++) {
            result.setItem(i, empty);
        }
        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta meta = exit.getItemMeta();
        meta.displayName(Component.text("Close", NamedTextColor.RED).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        exit.setItemMeta(meta);
        result.setItem(0, exit);
        return result;
    }

    public static void fillMiddleWithObjects(List<GUIObject> objects, Inventory inv) {
        int size = inv.getSize();
        Console.info(String.valueOf(inv.getSize()));
        int index = 0;
        for(int i = 9; i <= size - 9; i++) {
            if(i % 9 == 0 || i % 9 == 8) {
                continue;
            }
            if(index < objects.size()) {
                inv.setItem(i, objects.get(index).getRepresentingItem());
            } else {
                inv.setItem(i, new ItemStack(Material.AIR));
            }
            index++;
        }
    }

    public static void fillMiddleWithItems(List<ItemStack> objects, Inventory inv) {
        int size = inv.getSize();
        int index = 0;
        for(int i = 9; i <= size - 9; i++) {
            if(i % 9 == 0 || i % 9 == 8) {
                continue;
            }
            if(index < objects.size()) {
                inv.setItem(i, objects.get(index));
            } else {
                inv.setItem(i, new ItemStack(Material.AIR));
            }
            index++;
        }
    }
}

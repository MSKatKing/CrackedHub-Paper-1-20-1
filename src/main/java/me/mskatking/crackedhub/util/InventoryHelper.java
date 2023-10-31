package me.mskatking.crackedhub.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InventoryHelper {

    public static Inventory emptyInventory(int rows) {
        Inventory result = Bukkit.createInventory(null, rows * 9);
        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.displayName(Component.text(""));
        empty.setItemMeta(emptyMeta);
        for(int i = 0; i < 27; i++) {
            result.setItem(i, empty);
        }
        return result;
    }

    public static Inventory fillMiddleWithObjects(List<GUIObject> objects, Inventory inv) {
        int size = inv.getSize();
        int index = 0;
        for(int i = 9; i <= size - 9; i++) {
            if(i + 1 % 9 == 0 || i + 1 % 9 == 8) {
                i++;
                continue;
            }
            if(index <= objects.size()) {
                inv.setItem(i, objects.get(i).getRepresentingItem());
            } else {
                inv.setItem(i, new ItemStack(Material.AIR));
            }
            index++;
        }
        return inv;
    }
}

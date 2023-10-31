package me.mskatking.crackedhub.modules.ranks.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RankGUI {

    public static Inventory getInventory() {
        Inventory gui = Bukkit.createInventory(null, 54);
        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.displayName(Component.text(""));
        empty.setItemMeta(emptyMeta);
        for(int i = 0; i < 27; i++) {
            gui.setItem(i, empty);
        }
        return gui;
    }
}

package me.mskatking.crackedhub.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftingHelper implements Listener {

    public static Inventory getDuperInventory() {
        Inventory out = InventoryHelper.emptyInventoryWithHoles(6, Component.text("Duper Crafting", NamedTextColor.GRAY), 19, 20, 21, 28, 29, 30, 34, 37, 38, 39);
        for(int i = 1; i < 9; i++) {
            ItemStack filler = new ItemStack(Material.CRAFTING_TABLE);
            ItemMeta meta = filler.getItemMeta();
            meta.displayName(Component.text(" "));
            filler.setItemMeta(meta);
            out.setItem(i, filler);
        }
        out.setItem(32, new ItemStack(Material.ANVIL));

        return out;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if(e.getView().title().equals(Component.text("Duper Crafting", NamedTextColor.GRAY))) {
            Inventory menu = e.getView().getTopInventory();
            Inventory playerInventory = e.getView().getBottomInventory();
            Player player = (Player) e.getView().getPlayer();
            ItemStack clickedItem = e.getClickedInventory().getItem(e.getSlot());


        }
    }
}

package me.mskatking.crackedhub.modules.ranks.gui;

import me.mskatking.crackedhub.modules.ranks.Rank;
import me.mskatking.crackedhub.util.InventoryHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class RankGUI {

    public static Inventory getInventory() {
        Inventory gui = InventoryHelper.emptyInventory(6, Component.text("Ranks Menu", NamedTextColor.DARK_AQUA));
        InventoryHelper.fillMiddleWithObjects(List.of(new Rank("test", NamedTextColor.RED)), gui);
        return gui;
    }
}

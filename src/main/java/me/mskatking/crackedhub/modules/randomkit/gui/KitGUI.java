package me.mskatking.crackedhub.modules.randomkit.gui;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.util.GUIObject;
import me.mskatking.crackedhub.util.InventoryHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class KitGUI {

    public static Inventory getInventory() {
        Inventory result = InventoryHelper.emptyInventory(6, Component.text("Kits Menu", NamedTextColor.DARK_AQUA));
        InventoryHelper.fillMiddleWithObjects(List.of(CrackedHub.randomKitModule.kits.toArray(GUIObject[]::new)), result);
        return result;
    }
}

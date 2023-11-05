package me.mskatking.crackedhub.modules.randomkit.mechanics;

import me.mskatking.crackedhub.CrackedHub;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.Random;

public class KitPlayer {

    public static void giveKit(Player p, Kit k) {
        if(p.getInventory().getContents().length >= p.getInventory().getSize() + k.items.size()) {
            p.sendMessage(Component.text("[Kits] Your inventory is full! You need space for " + k.items.size() + " items!", NamedTextColor.RED));
            return;
        }
        for(ItemStack i : k.items) {
            p.getInventory().addItem(i);
        }
    }

    public static void giveRandomKit(Player p) {
        Kit random = (CrackedHub.randomKitModule.kits.size() - 1) == 0 ? CrackedHub.randomKitModule.kits.get(0) : CrackedHub.randomKitModule.kits.get(new Random().nextInt(0, CrackedHub.randomKitModule.kits.size() - 1));
        if(p.getInventory().getContents().length >= p.getInventory().getSize() + random.items.size()) {
            p.sendMessage(Component.text("[Kits] Your inventory is full! You need space for " + random.items.size() + " items!", NamedTextColor.RED));
            return;
        }
        for(ItemStack i : random.items) {
            p.getInventory().addItem(i);
        }
    }
}

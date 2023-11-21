package me.mskatking.crackedhub.modules.randomkit.mechanics;

import me.mskatking.crackedhub.CrackedHub;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class KitPlayer {

    public static void giveKit(Player p, Kit k) {
        for(ItemStack i : k.items) {
            p.getInventory().addItem(i);
        }
    }

    public static void giveRandomKit(Player p) {
        Kit random = (CrackedHub.randomKitModule.kits.size() - 1) == 0 ? CrackedHub.randomKitModule.kits.get(0) : CrackedHub.randomKitModule.kits.get(new Random().nextInt(0, CrackedHub.randomKitModule.kits.size() - 1));
        for(ItemStack i : random.items) {
            p.getInventory().addItem(i);
        }
    }
}

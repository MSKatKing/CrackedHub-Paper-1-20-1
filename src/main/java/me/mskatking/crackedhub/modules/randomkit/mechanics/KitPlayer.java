package me.mskatking.crackedhub.modules.randomkit.mechanics;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.util.GUIObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class KitPlayer implements ConfigurationSerializable, GUIObject {

    public final Player p;
    private int randomCooldown = 0;
    private int chosenCooldown = 0;

    public KitPlayer(Player p) {
        this.p = p;
    }

    public KitPlayer(String id, Map<String, Object> in) {
        p = Bukkit.getPlayer(UUID.fromString(id));
        randomCooldown = (int) in.get("random");
        chosenCooldown = (int) in.get("chose");
    }

    public void tick() {
        if(randomCooldown > 0) randomCooldown--;
        if(chosenCooldown > 0) chosenCooldown--;
    }

    public boolean canChooseKit() {
        return chosenCooldown <= 0;
    }

    public boolean canGetRandomKit() {
        return randomCooldown <= 0;
    }

    public void giveKit(Kit k, boolean bypassTime) {
        if(p.getInventory().getContents().length >= p.getInventory().getSize() + k.items.size()) {
            p.sendMessage(Component.text("[Kits] Your inventory is full! You need space for " + k.items.size() + " items!", NamedTextColor.RED));
            return;
        }
        if(bypassTime || canChooseKit()) {
            for(ItemStack i : k.items) {
                p.getInventory().addItem(i);
            }
        } else {
            p.sendMessage(Component.text("[Kits] You have to wait " + chosenCooldown / 60 + "m " + chosenCooldown % 60 + "s before you can choose a kit!", NamedTextColor.RED));
        }
    }

    public void giveRandomKit(boolean bypassTime) {
        Kit random = CrackedHub.randomKitModule.kits.get(new Random().nextInt(0, CrackedHub.randomKitModule.kits.size() - 1));
        if(bypassTime || canGetRandomKit()) {
            if(p.getInventory().getContents().length >= p.getInventory().getSize() + random.items.size()) {
                p.sendMessage(Component.text("[Kits] Your inventory is full! You need space for " + random.items.size() + " items!", NamedTextColor.RED));
                return;
            }
            for(ItemStack i : random.items) {
                p.getInventory().addItem(i);
            }
        } else {
            p.sendMessage(Component.text("[Kits] You have to wait " + randomCooldown / 60 + "m " + randomCooldown % 60 + "s before you can receive a kit!", NamedTextColor.RED));
        }
    }

    @Override
    public ItemStack getRepresentingItem() {
        ItemStack out = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) out.getItemMeta();
        meta.setOwningPlayer(p);
        meta.displayName(Component.text(p.getName(), CrackedHub.ranksModule.getPlayerColor(p)));
        return null;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> out = new HashMap<>();
        out.put("random", randomCooldown);
        out.put("choose", chosenCooldown);
        return out;
    }
}

package me.mskatking.crackedhub.modules.randomkit.gui;

import com.destroystokyo.paper.profile.PlayerProfile;
import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.util.GUIObject;
import me.mskatking.crackedhub.util.InventoryHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class KitGUI {

    public static Inventory getInventory(Player p) {
        Inventory result = InventoryHelper.emptyInventory(6, Component.text("Kits Menu", NamedTextColor.DARK_AQUA));
        InventoryHelper.fillMiddleWithObjects(List.of(CrackedHub.randomKitModule.kits.toArray(GUIObject[]::new)), result);
        if(p.hasPermission("crackedhub.admin.addKit")) {
            ItemStack s = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta m = (SkullMeta) s.getItemMeta();
            PlayerProfile pl = Bukkit.createProfile(UUID.randomUUID());
            PlayerTextures t = pl.getTextures();
            try {
                t.setSkin(new URL("http://textures.minecraft.net/texture/2ab75c4ae0f6afa3dfe2ba182e10935f000ba3549c532299f9b502513e7f99ce"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            pl.setTextures(t);
            m.setPlayerProfile(pl);
            m.displayName(Component.text("Create Kit", NamedTextColor.GREEN));
            s.setItemMeta(m);
            result.setItem(8, s);
        }
        return result;
    }

    public static Inventory getKitAddInventory() {
        Inventory i = InventoryHelper.emptyInventory(6, Component.text("Create Kit", NamedTextColor.GREEN));
        InventoryHelper.fillMiddleWithObjects(List.of(), i);
        ItemStack name = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = name.getItemMeta();
        meta.displayName(Component.text("Kit Name", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD));
        meta.lore(List.of(Component.text("Click to change the kit name", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)));
        name.setItemMeta(meta);
        i.setItem(4, name);
        name = new ItemStack(Material.GREEN_CONCRETE);
        meta = name.getItemMeta();
        meta.displayName(Component.text("Finalize Kit", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        name.setItemMeta(meta);
        i.setItem(53, name);
        return i;
    }

    public static void finalizeKitCreation(Inventory i) {
        //TODO: this
    }
}

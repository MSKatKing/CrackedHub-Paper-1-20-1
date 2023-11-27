package me.mskatking.crackedhub.modules.dupelifesteal.events;

import me.mskatking.crackedhub.modules.dupelifesteal.DupeLifesteal;
import me.mskatking.crackedhub.modules.dupelifesteal.Items;
import me.mskatking.crackedhub.util.CrackedHubPlayer;
import me.mskatking.crackedhub.util.WorldHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class DupeLifestealListener implements org.bukkit.event.Listener {

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if(e.getView().title().equals(Component.text("Dupe Menu T1", NamedTextColor.GOLD))) {
            e.setCancelled(true);
            ItemStack duperTrooper = Arrays.stream(e.getWhoClicked().getInventory().getContents()).filter((i) -> {
                return i.getItemMeta().getPersistentDataContainer().get(Items.Keys.STACKABLE_UUID.getKey(), PersistentDataType.STRING).equals(e.getClickedInventory().getItem(13).getItemMeta().getPersistentDataContainer().get(Items.Keys.UUID_REFERECNE.getKey(), PersistentDataType.STRING));
            }).findFirst().get();
            if(e.getSlot() == 13) {
                ItemStack dupedItem = e.getInventory().getItem(12);
                if(!dupedItem.equals(Material.AIR)) {
                    if(DupeLifesteal.isNotDupable(dupedItem)) {
                        e.getWhoClicked().sendMessage(Component.text("You can't dupe that item!"));
                    } else {
                        e.getInventory().setItem(14, dupedItem);
                        ItemMeta m = duperTrooper.getItemMeta();
                        m.getPersistentDataContainer().set(Items.Keys.USES_REMAINING.getKey(), PersistentDataType.INTEGER, duperTrooper.getItemMeta().getPersistentDataContainer().get(Items.Keys.USES_REMAINING.getKey(), PersistentDataType.INTEGER) - 1);
                        duperTrooper.setItemMeta(m);
                        e.getWhoClicked().getInventory().setItem(0, duperTrooper);

                    }
                } else {
                    e.getWhoClicked().sendMessage(Component.text("You can't dupe air!", NamedTextColor.RED));
                }
            }
        }
    }

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        if(e.getAction().isRightClick()) {
            Items i = Items.isCustomItem(e.getItem());
            if(!i.equals(null)) {
                i.executeFunctionality(e);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        try {
            Entity kill = e.getPlayer().getKiller();
            Player player = e.getPlayer();

            if (WorldHelper.isInWorlds(player, "dupelifesteal", "dupelifesteal_end", "dupelifesteal_nether")) {
                if(kill instanceof Player killer) {
                    CrackedHubPlayer.findPlayer(killer).hearts++;
                    CrackedHubPlayer.findPlayer(player).hearts--;

                    killer.setHealth(CrackedHubPlayer.findPlayer(killer).hearts);
                    player.setHealth(CrackedHubPlayer.findPlayer(player).hearts);

                    killer.sendMessage(Component.text("You stole 1 ❤ from " + player.getName() + "!", NamedTextColor.RED));
                    player.sendMessage(Component.text(killer.getName() + " stole 1 ❤ from you!", NamedTextColor.RED));
                } else {
                    CrackedHubPlayer.findPlayer(player).hearts--;

                    player.setHealth(CrackedHubPlayer.findPlayer(player).hearts);

                    player.sendMessage(Component.text("Yoou died and lost 1 ❤!", NamedTextColor.RED));
                }
            }
        } catch (NullPointerException ignored) {}
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if(WorldHelper.isInWorlds(e.getPlayer(), "dupelifesteal", "dupelifesteal_end", "dupelifesteal_nether")) {
            e.getPlayer().setHealth(CrackedHubPlayer.findPlayer(e.getPlayer()).hearts);
        }
    }
}

package me.mskatking.crackedhub.modules.dupelifesteal.events;

import me.mskatking.crackedhub.modules.dupelifesteal.DupeLifesteal;
import me.mskatking.crackedhub.modules.dupelifesteal.Items;
import me.mskatking.crackedhub.util.CrackedHubPlayer;
import me.mskatking.crackedhub.util.CustomErrors;
import me.mskatking.crackedhub.util.WorldHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class DupeLifestealListener implements org.bukkit.event.Listener {

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if(e.getView().title().equals(Component.text("DuperCraft Menu T1", NamedTextColor.GOLD))) {
            Inventory menu = e.getView().getTopInventory();
            Inventory playerInventory = e.getView().getBottomInventory();
            Player player = (Player) e.getView().getPlayer();
            ItemStack clickedItem = e.getClickedInventory().getItem(e.getSlot());

            if(menu == null || playerInventory == null || player == null || clickedItem == null) return;

            if(!e.isShiftClick() && !e.getClickedInventory().getType().equals(InventoryType.PLAYER) && !(e.getSlot() == 13)) e.setCancelled(true);
            if(clickedItem.getItemMeta().getPersistentDataContainer().has(Items.Keys.INTERNAL_ID.getKey()) && clickedItem.getItemMeta().getPersistentDataContainer().get(Items.Keys.INTERNAL_ID.getKey(), PersistentDataType.STRING).contains("duper_trooper")) {
                e.setCancelled(true);
                return;
            }

            int duperItemSlot = -1;
            ItemStack duperItem = null;
            for(int i = 0; i < playerInventory.getSize(); i++) {
                if(playerInventory.getItem(i) == null) continue;
                if(playerInventory.getItem(i).getItemMeta().getPersistentDataContainer().has(Items.Keys.STACKABLE_UUID.getKey()) && playerInventory.getItem(i).getItemMeta().getPersistentDataContainer().get(Items.Keys.STACKABLE_UUID.getKey(), PersistentDataType.STRING).equals(menu.getItem(22).getItemMeta().getPersistentDataContainer().get(Items.Keys.UUID_REFERECNE.getKey(), PersistentDataType.STRING))) {
                    duperItemSlot = i;
                    duperItem = playerInventory.getItem(i);
                    break;
                }
            }
            if(duperItemSlot == -1 || duperItem == null) {
                player.sendMessage(Component.text("[Duper Trooper] An error occured trying to process a inventory click! ", NamedTextColor.RED).append(CustomErrors.CONTACT_DEVELOPERS));
            }
            if(e.getSlot() == 22) {
                ItemStack itemToDupe = menu.getItem(13);
                if(itemToDupe == null) {
                    player.sendMessage(Component.text("[Duper Trooper] Silly, you can't dupe air!", NamedTextColor.RED));
                    return;
                } else if(DupeLifesteal.isNotDupable(itemToDupe)) {
                    player.sendMessage(Component.text("[Duper Trooper] That item is too powerful to dupe!", NamedTextColor.RED));
                    return;
                } else {
                    player.sendMessage(Component.text("[Duper Trooper] Duping " + itemToDupe.getAmount() + "x of " + PlainTextComponentSerializer.plainText().serialize(itemToDupe.displayName()), NamedTextColor.GREEN));
                    playerInventory.addItem(itemToDupe);
                    ItemMeta duperMeta = duperItem.getItemMeta();
                    duperMeta.getPersistentDataContainer().set(Items.Keys.USES_REMAINING.getKey(), PersistentDataType.INTEGER, duperMeta.getPersistentDataContainer().get(Items.Keys.USES_REMAINING.getKey(), PersistentDataType.INTEGER) - 1);
                    duperMeta.lore(List.of(Component.text("Right click me to open the dupe menu!", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE),
                            Component.text("You can only dupe " + duperMeta.getPersistentDataContainer().get(Items.Keys.USES_REMAINING.getKey(), PersistentDataType.INTEGER) + " more time(s)!", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)));
                    duperItem.setItemMeta(duperMeta);
                    if(duperItem.getItemMeta().getPersistentDataContainer().get(Items.Keys.USES_REMAINING.getKey(), PersistentDataType.INTEGER) <= 0) {
                        player.sendMessage(Component.text("[Duper Trooper] Oh no! Your duper trooper shattered!", NamedTextColor.RED));
                        playerInventory.setItem(duperItemSlot, new ItemStack(Material.AIR));
                        playerInventory.addItem(itemToDupe);
                        player.closeInventory();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(e.getView().title().equals(Component.text("DuperCraft Menu T1", NamedTextColor.GOLD)) && e.getReason().equals(InventoryCloseEvent.Reason.PLAYER)) {
            if(e.getView().getTopInventory().getItem(13) == null) return;
            e.getView().getPlayer().getInventory().addItem(e.getView().getTopInventory().getItem(13));
        }
    }

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        if(e.getAction().isRightClick()) {
            if(e.getItem() == null) return;
            Items i = Items.isCustomItem(e.getItem());
            if(!(i == null)) {
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

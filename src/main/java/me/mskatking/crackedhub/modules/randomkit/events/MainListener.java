package me.mskatking.crackedhub.modules.randomkit.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.randomkit.gui.KitGUI;
import me.mskatking.crackedhub.modules.randomkit.mechanics.Kit;
import me.mskatking.crackedhub.modules.randomkit.mechanics.KitPlayer;
import me.mskatking.crackedhub.modules.randomkit.util.KitNotFoundException;
import me.mskatking.crackedhub.util.GUIObject;
import me.mskatking.crackedhub.util.InventoryHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainListener implements Listener {

    private final HashMap<Player, Inventory> kitCreator = new HashMap<>();
    private final HashMap<Player, ArrayList<ItemStack>> kitItems = new HashMap<>();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (e.getPlayer().getWorld().equals(CrackedHub.core.getMVWorldManager().getMVWorld("random_kit").getCBWorld())) {
            e.getPlayer().sendMessage(CrackedHub.randomKitModule.getPrefix().append(Component.text("Giving you a random kit!", NamedTextColor.GREEN)));
            KitPlayer.giveRandomKit(e.getPlayer());
        }
    }

    @EventHandler
    public void onPortal(EntityPortalEnterEvent e) {
        if(e.getEntity() instanceof Player p && CrackedHub.core.getMVWorldManager().getMVWorld("random_kit_end").getCBWorld().equals(e.getEntity().getWorld()) && e.getLocation().getBlock().getType().equals(Material.END_PORTAL)) {
            p.getInventory().clear();
            CrackedHub.core.teleportPlayer(null, p, CrackedHub.core.getMVWorldManager().getMVWorld("random_kit").getSpawnLocation());
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        if(kitCreator.containsKey(e.getPlayer()) && !e.isAsynchronous()) {
            e.setCancelled(true);
            ItemMeta name = Objects.requireNonNull(kitCreator.get(e.getPlayer()).getItem(4)).getItemMeta();
            name.displayName(Component.text(e.getMessage()).color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD));
            Objects.requireNonNull(kitCreator.get(e.getPlayer()).getItem(4)).setItemMeta(name);
            InventoryHelper.fillMiddleWithItems(kitItems.get(e.getPlayer()), kitCreator.get(e.getPlayer()));
            e.getPlayer().openInventory(kitCreator.get(e.getPlayer()));
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
            if(e.getWhoClicked().getOpenInventory().title().equals(Component.text("Kits Menu", NamedTextColor.DARK_AQUA))) {
                if(!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.PLAYER)) {
                    switch (e.getSlot()) {
                        case 0: {
                            Objects.requireNonNull(e.getClickedInventory()).close();
                            kitItems.remove((Player) e.getWhoClicked());
                            kitCreator.remove((Player) e.getWhoClicked());
                            e.setCancelled(true);
                            break;
                        }
                        case 8: {
                            if (e.getWhoClicked().hasPermission("crackedhub.admin.addKit")) {
                                e.setCancelled(true);
                                e.getClickedInventory().close();
                                e.getWhoClicked().openInventory(KitGUI.getKitAddInventory());
                                kitCreator.put((Player) e.getWhoClicked(), KitGUI.getKitAddInventory());
                                kitItems.put((Player) e.getWhoClicked(), new ArrayList<>());
                                break;
                            }
                        }
                        case 1, 2, 3, 4, 5, 6, 7, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53: {
                            e.setCancelled(true);
                            break;
                        }
                        default: {
                            if(e.isLeftClick()) {
                                try {
                                    KitPlayer.giveKit((Player) e.getWhoClicked(), CrackedHub.randomKitModule.getKit(((TextComponent) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(e.getClickedInventory()).getItem(e.getSlot())).getItemMeta().displayName())).content()));
                                } catch (KitNotFoundException ex) {
                                    e.getWhoClicked().sendMessage(CrackedHub.randomKitModule.getPrefix().append(Component.text("That kit doesn't exist!", NamedTextColor.RED)));
                                }
                            } else {
                                try {
                                    CrackedHub.randomKitModule.kits.remove(CrackedHub.randomKitModule.getKit(((TextComponent) (Objects.requireNonNull((Objects.requireNonNull(e.getClickedInventory()).getItem(e.getSlot())).getItemMeta().displayName()))).content()));
                                } catch (KitNotFoundException ex) {
                                    e.getWhoClicked().sendMessage(CrackedHub.randomKitModule.getPrefix().append(Component.text("That lot doesn't exist!", NamedTextColor.RED)));
                                }
                                e.getClickedInventory().close();
                                InventoryHelper.fillMiddleWithObjects(List.of(CrackedHub.randomKitModule.kits.toArray(GUIObject[]::new)), kitCreator.get((Player) e.getWhoClicked()));
                                e.getWhoClicked().openInventory(kitCreator.get((Player) e.getWhoClicked()));
                            }
                            e.setCancelled(true);
                        }
                    }
                }
            } else if (e.getWhoClicked().getOpenInventory().title().equals(Component.text("Create Kit", NamedTextColor.GREEN))) {
                if(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.PLAYER)) {
                    e.setCancelled(true);
                    kitItems.get((Player) e.getWhoClicked()).add(e.getClickedInventory().getItem(e.getSlot()));
                    InventoryHelper.fillMiddleWithItems(kitItems.get((Player) e.getWhoClicked()), kitCreator.get((Player) e.getWhoClicked()));
                    e.getInventory().close();
                    e.getWhoClicked().openInventory(kitCreator.get((Player) e.getWhoClicked()));
                } else {
                    switch (e.getSlot()) {
                        case 0: {
                            Objects.requireNonNull(e.getClickedInventory()).close();
                            kitCreator.remove((Player) e.getWhoClicked());
                            kitItems.remove((Player) e.getWhoClicked());
                            e.setCancelled(true);
                            break;
                        }
                        case 4: {
                            kitCreator.put((Player) e.getWhoClicked(), e.getClickedInventory());
                            e.getClickedInventory().close();
                            e.getWhoClicked().sendMessage(Component.text("Type the name of the kit in chat.", NamedTextColor.GOLD));
                            break;
                        }
                        case 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52: {
                            e.setCancelled(true);
                            break;
                        }
                        case 53: {
                            e.setCancelled(true);
                            CrackedHub.randomKitModule.kits.add(new Kit(Objects.requireNonNull(e.getClickedInventory().getItem(4)).getItemMeta().displayName(), kitItems.get((Player) e.getWhoClicked())));
                            e.getClickedInventory().close();
                            e.getWhoClicked().openInventory(KitGUI.getInventory((Player) e.getWhoClicked()));
                            break;
                        }
                        default: {
                            if (!Objects.requireNonNull(e.getClickedInventory().getItem(e.getSlot())).getType().equals(Material.AIR)) {
                                kitItems.get((Player) e.getWhoClicked()).remove(e.getClickedInventory().getItem(e.getSlot()));
                            }
                            e.setCancelled(true);
                        }
                    }
                }
            }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (kitCreator.containsKey((Player) e.getPlayer()) && !e.getReason().equals(InventoryCloseEvent.Reason.PLUGIN)) {
            kitCreator.remove((Player) e.getPlayer());
            kitItems.remove((Player) e.getPlayer());
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if(CrackedHub.core.getMVWorldManager().getMVWorld("lobby").getCBWorld().equals(e.getFrom())) {
            e.getPlayer().sendMessage("");
            e.getPlayer().sendMessage(Component.text("Welcome to Random Kits!", NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
            e.getPlayer().sendMessage(Component.text("Every time you respawn, you will be given a new kit!", NamedTextColor.GRAY));
            e.getPlayer().sendMessage("");
        }
        if(CrackedHub.core.getMVWorldManager().getMVWorld("random_kit").getCBWorld().equals(e.getFrom()) ||
                CrackedHub.core.getMVWorldManager().getMVWorld("random_kit_end").getCBWorld().equals(e.getFrom()) ||
                CrackedHub.core.getMVWorldManager().getMVWorld("random_kit_nether").getCBWorld().equals(e.getFrom())) {
        }
        if(CrackedHub.core.getMVWorldManager().getMVWorld("random_kit").getCBWorld().equals(e.getPlayer().getWorld()) ||
                CrackedHub.core.getMVWorldManager().getMVWorld("random_kit_end").getCBWorld().equals(e.getPlayer().getWorld()) ||
                CrackedHub.core.getMVWorldManager().getMVWorld("random_kit_nether").getCBWorld().equals(e.getPlayer().getWorld())) {
            if(CrackedHub.randomKitModule.isFirstJoin(e.getPlayer()))
                KitPlayer.giveRandomKit(e.getPlayer());
        }
    }
}

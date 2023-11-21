package me.mskatking.crackedhub.modules.dupelifesteal.events;

import me.mskatking.crackedhub.util.CrackedHubPlayer;
import me.mskatking.crackedhub.util.WorldHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DupeLifestealListener implements org.bukkit.event.Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        try {
            Player killer = e.getEntity().getKiller();
            Player player = e.getEntity();

            if (WorldHelper.isInWorlds(killer, "dupelifesteal", "dupelifesteal_end", "dupelifesteal_nether") && WorldHelper.isInWorlds(player, "dupelifesteal", "dupelifesteal_end", "dupelifesteal_nether")) {
                CrackedHubPlayer.findPlayer(killer).hearts++;
                CrackedHubPlayer.findPlayer(player).hearts--;

                killer.setHealth(CrackedHubPlayer.findPlayer(killer).hearts);
                player.setHealth(CrackedHubPlayer.findPlayer(player).hearts);

                killer.sendMessage(Component.text("You stole 1 ❤ from " + player.getName() + "!", NamedTextColor.RED));
                player.sendMessage(Component.text(killer.getName() + " stole 1 ❤ from you!", NamedTextColor.RED));
            }
        } catch (NullPointerException ignored) {}
    }
}

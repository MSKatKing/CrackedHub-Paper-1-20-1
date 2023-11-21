package me.mskatking.crackedhub.modules.dupelifesteal.events;

import me.mskatking.crackedhub.util.CrackedHubPlayer;
import me.mskatking.crackedhub.util.WorldHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player player = e.getEntity();

        if(WorldHelper.isInWorlds(killer, "dupelifesteal", "dupelifesteal_end", "dupelifesteal_nether") && WorldHelper.isInWorlds(player, "dupelifesteal", "dupelifesteal_end", "dupelifesteal_nether")) {
            CrackedHubPlayer.findPlayer(killer).hearts++;
            CrackedHubPlayer.findPlayer(player).hearts--;

            killer.setHealth(CrackedHubPlayer.findPlayer(killer).hearts);
            player.setHealth(CrackedHubPlayer.findPlayer(player).hearts);
        }
    }
}

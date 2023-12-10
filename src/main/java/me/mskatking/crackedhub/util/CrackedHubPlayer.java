package me.mskatking.crackedhub.util;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.ranks.util.Rank;
import me.mskatking.crackedhub.util.events.SecondTimerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;

public class CrackedHubPlayer implements Listener {

    //DuperCraft Lifesteal associated stuff
    public int dupeCooldown = 0;
    public int hearts;

    public long playtime;
    public boolean staff;
    public Player player;
    public Rank rank = Rank.Ranks.MEMBER.value();

    private final Timer cooldownTimer = new Timer();

    public CrackedHubPlayer(Player p, boolean playedBefore) {
        if(playedBefore) {
            Console.info("Fetching player data for " + p.getName() + ".");
            ResultSet rs = SQLProcessor.getResult("SELECT RANK, PLAYTIME, USER, STAFF FROM PLAYER_DATA WHERE UUID = '" + p.getUniqueId() + "';");
            ResultSet rs1 = SQLProcessor.getResult("SELECT * FROM DUPE_LIFESTEAL_DATA WHERE UUID = '" + p.getUniqueId() + "';");
            try {
                rs.next();
                rs1.next();
                playtime = rs.getLong("PLAYTIME");
                staff = rs.getBoolean("STAFF");
                hearts = rs1.getInt("HEALTH");
                switch (rs.getString("RANK")) {
                    case "member" -> rank = Rank.Ranks.MEMBER.value();
                    case "developer" -> rank = Rank.Ranks.DEVELOPER.value();
                    case "owner" -> rank = Rank.Ranks.OWNER.value();
                    default -> rank = Rank.Ranks.MEMBER.value();
                }
            } catch (Exception ignored1) {}
        } else {
            Console.info("Say hello! " + p.getName() + " joined for the first time!");
            playtime = 0L;
            staff = false;
            hearts = 20;
            rank = Rank.Ranks.MEMBER.value();
        }

        player = p;

        cooldownTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(dupeCooldown > 0) dupeCooldown--;
            }
        }, 0, 1000);
    }

    public static CrackedHubPlayer findPlayer(Player p) {
        Console.error("Could not find player " + p.getUniqueId() + " (" + String.join(", ", CrackedHub.onlinePlayers.stream().map(a -> a.player.getUniqueId().toString()).toList()) + ")");
        for(CrackedHubPlayer cp : CrackedHub.onlinePlayers) {
            if(cp.player.getUniqueId().equals(p.getUniqueId())) return cp;
        }
        return null;
    }

    public String toString() {
        return player + "\n" + staff + "\n" + playtime;
    }

    @EventHandler
    public void second(SecondTimerEvent e) {

    }
}

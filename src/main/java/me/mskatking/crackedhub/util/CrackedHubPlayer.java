package me.mskatking.crackedhub.util;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.ranks.util.Rank;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;

public class CrackedHubPlayer {

    //Dupe Lifesteal associated stuff
    public int dupeCooldown = 0;
    public int hearts;

    public long playtime;
    public boolean staff;
    public Player player;
    public Rank rank;

    private final Timer cooldownTimer = new Timer();

    public CrackedHubPlayer(Player p) {
        player = p;
        playtime = 0L;
        staff = false;
        hearts = 20;

        cooldownTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(dupeCooldown > 0) dupeCooldown--;
            }
        }, 0, 1000);
    }

    public CrackedHubPlayer(Player p, @Nullable Boolean ignored) {
        ResultSet rs = SQLProcessor.getResult("SELECT RANK, PLAYTIME, USER, STAFF FROM PLAYER_DATA WHERE UUID = '" + p.getUniqueId() + "';");
        ResultSet rs1 = SQLProcessor.getResult("SELECT * FROM DUPE_LIFESTEAL_DATA WHERE UUID = '" + p.getUniqueId() + "';");
        try {
            rs.next();
            rs1.next();
            player = p;
            playtime = rs.getLong("PLAYTIME");
            staff = rs.getBoolean("STAFF");
            hearts = rs1.getInt("HEALTH");
            switch (rs.getString("RANK")) {
                case "member" -> rank = Rank.Ranks.MEMBER.value();
                case "developer" -> rank = Rank.Ranks.DEVELOPER.value();
            }
        } catch (Exception ignored1) {}

        cooldownTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(dupeCooldown > 0) dupeCooldown--;
            }
        }, 0, 1000);
    }

    public static CrackedHubPlayer findPlayer(Player p) {
        for(CrackedHubPlayer cp : CrackedHub.onlinePlayers) {
            if(cp.player.equals(p)) return cp;
        }
        return null;
    }

    public String toString() {
        return player + "\n" + staff + "\n" + playtime;
    }
}

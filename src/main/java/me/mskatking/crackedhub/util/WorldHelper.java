package me.mskatking.crackedhub.util;

import me.mskatking.crackedhub.CrackedHub;
import org.bukkit.entity.Player;

public class WorldHelper {

    public static boolean isInWorlds(Player p, String... worlds) {
        for(String s : worlds) {
            if(p.getWorld().equals(CrackedHub.core.getMVWorldManager().getMVWorld(s).getCBWorld())) return true;
        }
        return false;
    }
}

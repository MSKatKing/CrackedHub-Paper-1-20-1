package me.mskatking.crackedhub.util;

import org.bukkit.entity.Player;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException(Player p) {
        super("Error! Player " + p.getName() + "(" + p.getUniqueId()+") could not be found!");
    }
}

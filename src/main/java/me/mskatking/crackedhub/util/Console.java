package me.mskatking.crackedhub.util;

import me.mskatking.crackedhub.CrackedHub;

public class Console {
    public static void info(String message) {
        CrackedHub.getPlugin(CrackedHub.class).getLogger().info(message);
    }

    public static void warn(String message) {
        CrackedHub.getPlugin(CrackedHub.class).getLogger().warning(message);
    }

    public static void error(String message) {
        CrackedHub.getPlugin(CrackedHub.class).getLogger().severe(message);
    }
}

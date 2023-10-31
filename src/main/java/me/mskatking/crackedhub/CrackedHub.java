package me.mskatking.crackedhub;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.mskatking.crackedhub.modules.box.CrackedHubBox;
import me.mskatking.crackedhub.modules.ranks.CrackedHubRanks;
import me.mskatking.crackedhub.util.Console;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class CrackedHub extends JavaPlugin {

    public static ArrayList<String> developers = new ArrayList<>();

    public static final MultiverseCore core = MultiverseCore.getPlugin(MultiverseCore.class);
    private static CrackedHub instance;

    public static CrackedHubBox boxModule;
    public static CrackedHubRanks ranksModule;

    @Override
    public void onEnable() {
        instance = this;

        boxModule = new CrackedHubBox();
        ranksModule = new CrackedHubRanks();

        boxModule.enable();
        ranksModule.enable();

        Console.info("CrackedHub enabled!");
    }

    @Override
    public void onDisable() {
        Console.info("Shutting down modules...");
        boxModule.shutdown();
        ranksModule.shutdown();

        Console.info("CrackedHub disabled!");
    }

    public static CrackedHub getPlugin() {
        return instance;
    }
}

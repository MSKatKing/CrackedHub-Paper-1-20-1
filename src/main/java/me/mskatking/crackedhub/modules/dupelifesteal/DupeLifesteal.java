package me.mskatking.crackedhub.modules.dupelifesteal;

import me.mskatking.crackedhub.CrackedHub;
import me.mskatking.crackedhub.modules.dupelifesteal.commands.Dupe;
import me.mskatking.crackedhub.modules.dupelifesteal.events.Listener;
import me.mskatking.crackedhub.util.Module;
import net.kyori.adventure.text.Component;

public class DupeLifesteal implements Module {

    public DupeLifesteal() {

    }

    @Override
    public void enable() {
        CrackedHub.getPlugin().getServer().getCommandMap().register("dupelifesteal", new Dupe());
        CrackedHub.getPlugin().getServer().getPluginManager().registerEvents(new Listener(), CrackedHub.getPlugin());
    }

    @Override
    public void disable() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean initializeFromConfig() {
        return false;
    }

    @Override
    public Component getPrefix() {
        return null;
    }
}

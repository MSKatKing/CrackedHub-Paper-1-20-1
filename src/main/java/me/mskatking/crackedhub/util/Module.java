package me.mskatking.crackedhub.util;

import net.kyori.adventure.text.Component;

public interface Module {

    void enable();
    void disable();

    void shutdown();

    boolean save();

    boolean initializeFromConfig();

    Component getPrefix();
}

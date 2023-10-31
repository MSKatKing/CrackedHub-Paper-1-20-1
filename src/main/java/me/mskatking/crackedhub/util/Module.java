package me.mskatking.crackedhub.util;

public interface Module {
    void enable();
    void disable();

    void shutdown();

    boolean save();
}

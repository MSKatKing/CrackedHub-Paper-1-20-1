package me.mskatking.crackedhub.modules.randomkit.util;

public class KitNotFoundException extends Exception {
    public KitNotFoundException(String kitName) {
        super("Error! Kit '" + kitName + "' could not be found!");
    }
}

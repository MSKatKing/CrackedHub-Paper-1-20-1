package me.mskatking.crackedhub.util;

public enum Errors {
    BOXES_SAVE_ERROR("Error saving boxes configs! Data has been lost!"),
    BOXES_SAVE_WARN("Error saving boxes configs! Some changes may be lost!"),
    RANKS_SAVE_ERROR("Error saving ranks configs! Data has been lost!"),
    RANKS_SAVE_WARN("Error saving ranks configs! Some changes may be lost!");

    private final String value;
    Errors(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}

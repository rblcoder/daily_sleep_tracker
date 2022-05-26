package com.company.sleep.config;

public enum Constants {
    GET_UP_TIME_CANNOT_BE_LESS_THAN_SLEEP_TIME("Sleep time cannot be after Get up time"),
    SLEEP_TIME_CANNOT_BE_EMPTY("Sleep time cannot be empty"),
    EMPTY_MESSAGE("");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

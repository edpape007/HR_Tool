package com.hrtool.model;

public class Goal {
    private final String description;
    private final String calification;


    public Goal(String description, String calification) {
        this.description = description;
        this.calification = calification;
    }

    public String getDescription() {
        return description;
    }

    public String getCalification() {
        return calification;
    }
}

package com.chinmay.models;

public enum ComponentStatus {

    RUN("[run]"),
    OFF("[off] Disabled"),
    WAIT("[wait]"),
    OFF_ERROR("[off error]"),
    OFF_ERROR_DISABLED("[off error] Disabled"),
    MANUAL("[manual]");

    private String name;

    ComponentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ComponentStatus getStatus(String name) {
        switch (name) {
            case "[run]":
                return RUN;
            case "[off] Disabled":
                return OFF;
            case "[wait]":
                return WAIT;
            case "[off error]":
                return OFF_ERROR;
            case "[off error] Disabled":
                return OFF_ERROR_DISABLED;
            case "[manual]":
                return MANUAL;
            default:
                throw new IllegalArgumentException("Name "+name+" is invalid value for component status");
        }
    }
}

package com.chinmay.models;

public class Config {
    private String previousFilePath;
    private String currentFilePath;
    private String type;

    public Config() {
    }

    public String getPreviousFilePath() {
        return previousFilePath;
    }

    public void setPreviousFilePath(String previousFilePath) {
        this.previousFilePath = previousFilePath;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.chinmay.controller;

import com.chinmay.models.Config;
import com.chinmay.service.AbstractService;
import com.chinmay.service.ServiceFactory;
import com.chinmay.util.Constants;

import java.io.FileNotFoundException;

public class StatusController {

    private final Config config;

    public StatusController(String[] args) {
        this.config = createConfig(args);
    }

    public Config createConfig(String[] args) {
        if(args == null || args.length != 6) {
            throw new IllegalArgumentException("Usage is java -jar excel-compare-1.0-SNAPSHOT.jar --previous [filepath] --current [filepath] --type [type]");
        } else {
            Config config = new Config();
            setConfig(config, args[0], args[1]);
            setConfig(config, args[2], args[3]);
            setConfig(config, args[4], args[5]);
            return config;
        }
    }

    public void setConfig(Config config, String flag, String value) {
        switch (flag) {
            case "--previous":
                config.setPreviousFilePath(value);
                break;
            case "--current":
                config.setCurrentFilePath(value);
                break;
            case "--type":
                config.setType(value);
                break;
            default:
                throw new IllegalArgumentException("Supported arguments are --previous, --current and --type");
        }
    }

    public void process() {
        try {
            AbstractService service = ServiceFactory.getService(config.getType());
            service.compare(config.getPreviousFilePath(), config.getCurrentFilePath());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
